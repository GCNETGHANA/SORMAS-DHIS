/*
 * Copyright (c) 2020, Mathew Official
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.mirabilia.org.hzi.sormas;

import com.google.gson.Gson;
import com.mirabilia.org.hzi.sormas.DhisDataValue.NamedParameterStatement;
import com.mirabilia.org.hzi.sormas.DhisDataValue.DhimsDataValue;
import com.mirabilia.org.hzi.Util.dhis.DHIS2resolver;
import com.mirabilia.org.hzi.sormas.DhisDataValue.AgeRange;
import com.mirabilia.org.hzi.sormas.DhisDataValue.CaseReportByClassification;
import com.mirabilia.org.hzi.sormas.DhisDataValue.CaseReportByOutcome;
import com.mirabilia.org.hzi.sormas.DhisDataValue.Gender;
import com.mirabilia.org.hzi.sormas.DhisDataValue.CategoryOptionCombo;
import com.mirabilia.org.hzi.sormas.DhisDataValue.ServeResponse;
import com.mirabilia.org.hzi.sormas.doa.DbConnector;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Mathew Official
 */
@WebServlet(name = "greport", urlPatterns = {"/greport"})
public class greport extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> payloadRequest = getBody(request);
        int year = Integer.parseInt(payloadRequest.get("year"));
        int month = Integer.parseInt(payloadRequest.get("month"));

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DbConnector.getPgConnection();
            ResultSet rx;

            List<String> queries = new ArrayList<String>();
            queries.add(report_case_outcome());
            queries.add(report_case_classification());
            queries.add(report_case_hospitalised());
            queries.add(report_case_tested());

            String query
                    = "WITH q AS (\n"
                    + String.join("\nUNION\n", queries) + "\n"
                    + ")\n"
                    + "SELECT * FROM q WHERE dataElement IS NOT NULL AND categoryOptionCombo IS NOT NULL";
            System.out.println(query);
            // System.out.println("\n\n\n\n\n\n\n\nquery for report: \n" + query);

            NamedParameterStatement ps = new NamedParameterStatement(conn, query);
            ps.setInt("year", year);
            ps.setInt("month", month);

            rx = ps.executeQuery();

            List<DhimsDataValue> dhimsList = new ArrayList<DhimsDataValue>();
            while (rx.next()) {
                DhimsDataValue dh = new DhimsDataValue(rx.getString("dataElement"), rx.getString("categoryOptionCombo"), rx.getString("period"), rx.getString("orgUnit"), rx.getString("value"));
                dhimsList.add(dh);
            }

            System.out.println("all orgUnits for report: " + dhimsList.size());

            List<DhimsDataValue> dhimsList2 = new ArrayList<DhimsDataValue>();
            for (DhimsDataValue d : dhimsList) {
                if (d.orgUnit.length() > 0) {
                    dhimsList2.add(d);
                }
            }
            dhimsList = dhimsList2;
            dhimsList2 = null;

            System.out.println("valid orgUnits for report: " + dhimsList.size());

            ServeResponse resp = DHIS2resolver.PostMethod("/api/dataValueSets", dhimsList, "dataValues");
            String resString = this.gson.toJson(resp);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(resString);
            out.flush();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(greport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException se) {
            System.err.println(se);
        } catch (Exception ef) {
            System.err.println(ef);
        }
    }

    static String report_case_outcome() {

        String outcomes_when_clause = "";
        for (CaseReportByOutcome n : CaseReportByOutcome.list()) {
            outcomes_when_clause += "\nWHEN '" + n.code + "' THEN '" + n.dataElement() + "'";
        }

        String age_gender_when_clause = "";
        for (AgeRange age : AgeRange.list()) {
            for (Gender gender : Gender.list()) {
                age_gender_when_clause
                        += "\nWHEN p.sex = '" + gender.code + "'"
                        + " AND get_person_age(p.id, COALESCE(c.outcomedate, c.changedate)) BETWEEN " + age.min + " AND " + age.max
                        + " THEN "
                        + "'" + CategoryOptionCombo.code(age.name + ", " + gender.name) + "'";
            }
        }

        return "SELECT\n"
                + " COALESCE(f.externalid, '') orgUnit,\n"
                + "	TO_CHAR(COALESCE(c.outcomedate, c.changedate), 'YYYYMM') \"period\",\n"
                + " CASE"
                + age_gender_when_clause + "\n"
                + " END categoryOptionCombo,\n"
                + " CASE c.outcome"
                + outcomes_when_clause + "\n"
                + " END dataElement,\n"
                + "	COUNT(c.*) \"value\"\n"
                + "FROM cases c\n"
                + "LEFT JOIN person p ON c.person_id = p.id\n"
                + "LEFT JOIN facility f ON c.healthfacility_id = f.id\n"
                + "WHERE\n"
                + " c.deleted <> true\n"
                + "	AND date_part('year', COALESCE(c.outcomedate, c.changedate)) = :year\n"
                + "	AND date_part('month', COALESCE(c.outcomedate, c.changedate)) = :month\n"
                + "	AND c.disease = 'CORONAVIRUS'\n"
                + "GROUP BY\n"
                + "	f.externalid,\n"
                + "	to_char(COALESCE(c.outcomedate, c.changedate), 'YYYYMM'),\n"
                + " CASE"
                + age_gender_when_clause + "\n"
                + " END,\n"
                + "	c.outcome";
    }

    static String report_case_classification() {

        String classifications_when_clause = "";
        for (CaseReportByClassification n : CaseReportByClassification.list()) {
            classifications_when_clause += "\nWHEN '" + n.code + "' THEN '" + n.dataElement() + "'";
        }

        String age_gender_when_clause = "";
        for (AgeRange age : AgeRange.list()) {
            for (Gender gender : Gender.list()) {
                age_gender_when_clause
                        += "\nWHEN p.sex = '" + gender.code + "'"
                        + " AND get_person_age(p.id, COALESCE(c.classificationdate, c.reportdate)) BETWEEN " + age.min + " AND " + age.max
                        + " THEN "
                        + "'" + CategoryOptionCombo.code(age.name + ", " + gender.name) + "'";
            }
        }

        return "SELECT\n"
                + " COALESCE(f.externalid, '') orgUnit,\n"
                + " TO_CHAR(COALESCE(c.classificationdate, c.reportdate), 'YYYYMM') \"period\",\n"
                + " CASE"
                + age_gender_when_clause + "\n"
                + " END categoryOptionCombo,\n"
                + " CASE c.caseclassification"
                + classifications_when_clause + "\n"
                + " END dataElement,\n"
                + " COUNT(c.*) \"value\"\n"
                + "FROM cases c\n"
                + "LEFT JOIN person p ON c.person_id = p.id\n"
                + "LEFT JOIN facility f ON c.healthfacility_id = f.id\n"
                + "WHERE\n"
                + " c.deleted <> true\n"
                + "	AND date_part('year', COALESCE(c.classificationdate, c.reportdate)) = :year\n"
                + "	AND date_part('month', COALESCE(c.classificationdate, c.reportdate)) = :month\n"
                + "	AND c.disease = 'CORONAVIRUS'\n"
                + "GROUP BY\n"
                + "	f.externalid,\n"
                + "	to_char(COALESCE(c.classificationdate, c.reportdate), 'YYYYMM'),\n"
                + " CASE"
                + age_gender_when_clause + "\n"
                + " END,\n"
                + "	c.caseclassification";
    }

    static String report_case_hospitalised() {

        String classifications_when_clause = "";
        for (CaseReportByClassification n : CaseReportByClassification.list()) {
            classifications_when_clause += "\nWHEN '" + n.code + "' THEN '" + n.dataElement() + "'";
        }

        String age_gender_when_clause = "";
        for (AgeRange age : AgeRange.list()) {
            for (Gender gender : Gender.list()) {
                age_gender_when_clause
                        += "\nWHEN p.sex = '" + gender.code + "'"
                        + " AND get_person_age(p.id, COALESCE(c.classificationdate, c.reportdate)) BETWEEN " + age.min + " AND " + age.max
                        + " THEN "
                        + "'" + CategoryOptionCombo.code(age.name + ", " + gender.name) + "'";
            }
        }

        return "SELECT\n"
                + " COALESCE(f.externalid, '') orgUnit,\n"
                + " TO_CHAR(COALESCE(c.classificationdate, c.reportdate), 'YYYYMM') \"period\",\n"
                + " CASE"
                + age_gender_when_clause + "\n"
                + " END categoryOptionCombo,\n"
                + " CASE c.caseclassification \n"
                + "WHEN 'CONFIRMED' THEN 'K9nmTxAOmZx' \n"
                + " END dataElement,\n"
                + " COUNT(c.*) \"value\"\n"
                + "FROM cases c\n"
                + "LEFT JOIN person p ON c.person_id = p.id\n"
                + "LEFT JOIN facility f ON c.healthfacility_id = f.id\n"
                + "LEFT JOIN hospitalization h ON c.hospitalization_id  = h.id\n"
                + "WHERE\n"
                + " h.admissiondate IS NOT NULL \n"
                + "	AND date_part('year', COALESCE(h.admissiondate)) = :year\n"
                + "	AND date_part('month', COALESCE(h.admissiondate)) = :month\n"
                + "	AND c.disease = 'CORONAVIRUS'\n"
                + "GROUP BY\n"
                + "	f.externalid,\n"
                + "	to_char(COALESCE(c.classificationdate, c.reportdate), 'YYYYMM'),\n"
                + " CASE"
                + age_gender_when_clause + "\n"
                + " END,\n"
                + "	c.caseclassification";
    }

    static String report_case_tested() {

        String classifications_when_clause = "";
        for (CaseReportByClassification n : CaseReportByClassification.list()) {
            classifications_when_clause += "\nWHEN '" + n.code + "' THEN '" + n.dataElement() + "'";
        }

        String age_gender_when_clause = "";
        for (AgeRange age : AgeRange.list()) {
            for (Gender gender : Gender.list()) {
                age_gender_when_clause
                        += "\nWHEN p.sex = '" + gender.code + "'"
                        + " AND get_person_age(p.id, COALESCE(c.classificationdate, c.reportdate)) BETWEEN " + age.min + " AND " + age.max
                        + " THEN "
                        + "'" + CategoryOptionCombo.code(age.name + ", " + gender.name) + "'";
            }
        }

        return "SELECT\n"
                + "\n"
                + "COALESCE(f.externalid, '') orgUnit,\n"
                + "\n"
                + "TO_CHAR(COALESCE(c.classificationdate, c.reportdate), 'YYYYMM') \"period\",\n"
                + "\n"
                + "CASE\n"
                + age_gender_when_clause
                + "\n"
                + "END categoryOptionCombo,\n"
                + "\n"
                + "CASE h.testresult\n"
                + "\n"
                + "WHEN NULL THEN NULL\n"
                + "else 'ws1Zf3jodFM'\n"
                + "\n"
                + "END dataElement,\n"
                + "\n"
                + "COUNT(distinct(c.id)) \"value\"\n"
                + "\n"
                + "FROM cases c\n"
                + "\n"
                + "LEFT JOIN person p ON c.person_id = p.id\n"
                + "\n"
                + "LEFT JOIN facility f ON c.healthfacility_id = f.id\n"
                + "\n"
                + "LEFT JOIN samples s ON c.id = s.associatedcase_id\n"
                + "\n"
                + "LEFT JOIN pathogentest h ON s.id = h.sample_id\n"
                + "\n"
                + "WHERE\n"
                + "\n"
                + "h.testresult IS NOT NULL \n"
                + "\n"
                + "AND date_part('year', COALESCE(h.creationdate)) = :year\n"
                + "\n"
                + "AND date_part('month', COALESCE(h.creationdate)) = :month\n"
                + "\n"
                + "AND c.disease = 'CORONAVIRUS'\n"
                + "\n"
                + "GROUP BY\n"
                + "\n"
                + "f.externalid,\n"
                + "\n"
                + "to_char(COALESCE(c.classificationdate, c.reportdate), 'YYYYMM'),\n"
                + "h.testresult,\n"
                + "\n"
                + "CASE\n"
                + age_gender_when_clause
                + "\n"
                + "END,\n"
                + "\n"
                + "c.caseclassification";
    }

    public static Map<String, String> getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        String[] bodyArray = body.split("&");
        Map<String, String> sMap = new HashMap<String, String>();
        for (int v = 0; v < bodyArray.length; v++) {
            String[] kv = bodyArray[v].split("=");
            sMap.put(kv[0], kv[1]);
        }
        return sMap;
    }

}
