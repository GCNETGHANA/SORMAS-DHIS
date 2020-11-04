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

import com.mirabilia.org.hzi.sormas.DhisDataValue.NamedParameterStatement;
import com.mirabilia.org.hzi.sormas.DhisDataValue.DhimsDataValue;
import com.mirabilia.org.hzi.Util.dhis.DHIS2resolver;
import com.mirabilia.org.hzi.sormas.DhisDataValue.AgeRange;
import com.mirabilia.org.hzi.sormas.DhisDataValue.Gender;
import com.mirabilia.org.hzi.sormas.DhisDataValue.categoryOptionCombo;
import com.mirabilia.org.hzi.sormas.DhisDataValue.dataElement;
import com.mirabilia.org.hzi.sormas.DhisDataValue.orgUnit;
import com.mirabilia.org.hzi.sormas.doa.DbConnector;
import static com.mirabilia.org.hzi.sormas.getterSetters.Localizer_Deleter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> payloadRequest = getBody(request);
        int year = Integer.parseInt(payloadRequest.get("year"));
        int month = Integer.parseInt(payloadRequest.get("month"));

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DbConnector.getPgConnection();
            String str = "";
            ResultSet rx;

            List<AgeRange> ages = AgeRange.list();

            List<Gender> genders = Gender.list();

            StringBuilder stmt = new StringBuilder();
            for (AgeRange age : ages) {
                for (Gender gender : genders) {
                    if (stmt.length() > 0) {
                        stmt.append("\nUNION");
                    }
                    stmt.append("\n" + report_deaths(age.name + ", " + gender.name, gender.code, age.min, age.max));
                }
            }

            NamedParameterStatement ps = new NamedParameterStatement(conn, stmt.toString());
            ps.setInt("year", year);
            ps.setInt("month", month);

            rx = ps.executeQuery();

            List<DhimsDataValue> dhimsList = new ArrayList<DhimsDataValue>();
            while (rx.next()) {
                DhimsDataValue dh = new DhimsDataValue(rx.getString("dataElement"), rx.getString("categoryOptionCombo"), rx.getString("period"), rx.getString("orgUnit"), rx.getString("value"));
                String _u = dh.orgUnit;
                dh.orgUnit = orgUnit.externalid(dh.orgUnit);
                System.out.println("hahahah: " + _u + ": " + dh.orgUnit);
                dhimsList.add(dh);
            }
            
            List<DhimsDataValue> dhimsList2 = new ArrayList<DhimsDataValue>();
            for (DhimsDataValue d : dhimsList) {
                if (d.orgUnit.length() > 0)
                    dhimsList2.add(d);
            }
            dhimsList = dhimsList2.subList(0, 1);
            dhimsList2 = null;

            System.out.println("log2: " + dhimsList.size());
            
            // DHIS2resolver.PostMethod("/api/dataValueSets", dhimsList, "dataValues");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(greport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException se) {
            System.err.println(se);
        } catch(Exception ef){
            System.err.println(ef);
        }

    }

    static String report_deaths(String column, String gender, int ageMin, int ageMax) {
        return "SELECT\n"
            + "	c.healthfacility_id::VARCHAR orgUnit,\n"
            + "	'" + dataElement.code("Number of new deaths") + "' dataElement,\n"
            + "	'" + categoryOptionCombo.code(column) + "' categoryOptionCombo,\n"
            + "	TO_CHAR(c.reportdate, 'YYYYMM') \"period\",\n"
            + "	COUNT(c.*) \"value\"\n"
            + "FROM cases c\n"
            + "LEFT JOIN person p ON c.person_id = p.id\n"
            + "WHERE\n"
            + "	c.disease = 'CORONAVIRUS'\n"
            + "	AND date_part('year', c.outcomedate) = :year\n"
            + "	AND date_part('month', c.outcomedate) = :month\n"
            + "	AND c.outcome = 'DECEASED'\n"
            + "	AND p.sex = '" + gender + "'\n"
            + "	AND p.approximateage BETWEEN " + ageMin + " AND " + ageMax + "\n"
            + "GROUP BY\n"
            + "	c.healthfacility_id,\n"
            + "	to_char(c.reportdate, 'YYYYMM')";
    }

    static void report_b() {
        String stmt = "SELECT\n"
                + "	c.healthfacility_id::VARCHAR orgUnit,\n"
                + "	'Number of cases tested' dataElement,\n"
                + "	'default' categoryOptionCombo,\n"
                + "	to_char(c.reportdate, 'YYYYMM') \"period\",\n"
                + "	count(c.*) \"value\"\n"
                + "from cases c\n"
                + "Where\n"
                + "	c.disease = 'CORONAVIRUS'\n"
                + "	AND date_part('year', c.reportdate) = 2020\n"
                + "	AND date_part('month', c.reportdate) = 8\n"
                + "	AND c.outcome = 'DECEASED'\n"
                + "GROUP BY\n"
                + "	c.healthfacility_id,\n"
                + "	to_char(c.reportdate, 'YYYYMM')\n"
                + "LIMIT 1";
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
