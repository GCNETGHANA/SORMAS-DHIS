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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>now in localizer");
        try {
            String tkbck = "";

            PreparedStatement ps;
            ResultSet rx;

            PreparedStatement ps1;
            ResultSet rx1;

            PreparedStatement ps2;
            ResultSet rx2;

            PreparedStatement ps3;
            ResultSet rx3;

            Class.forName("org.postgresql.Driver");
            Connection conn = DbConnector.getPgConnection();
            String str = "";

            //loop through sormas local and get infrastructure data by level into mysql local db for futher use by the adapter.
            try {
                if (request.getParameter("del_all") != null && "true".equals(request.getParameter("del_all"))) {

                    Localizer_Deleter();

                    if (1 == 1) {

                        System.out.println("yes!!!");
                    }

                }

                if (request.getParameter("rg") != null && "true".equals(request.getParameter("rg"))) {
                    //System.out.println("region? !"+request.getParameter("rg"));
                    ps = conn.prepareStatement("SELECT changedate, uuid, externalid, name, id, creationdate FROM region;");
                    rx = ps.executeQuery();
                    while (rx.next()) {
                        String cf = "";
                        if (rx.getString(3) == null) {
                            cf = "0";
                        } else {
                            cf = rx.getString(3);
                        }
                        getterSetters.Localizer(rx.getString(1), rx.getString(2), cf, "2", rx.getString(4), rx.getString(5), "", rx.getString(6));

                    }
                    tkbck = "finised pulling state data... successful";
                    System.out.println(tkbck);
                }
                if (request.getParameter("ds") != null && "true".equals(request.getParameter("ds"))) {

                    ps1 = conn.prepareStatement("SELECT changedate, uuid, externalid, name, id, region_id, creationdate FROM district;");
                    rx1 = ps1.executeQuery();
                    while (rx1.next()) {
                        String cv = "";
                        if (rx1.getString(3) == null) {
                            cv = "0";
                        } else {
                            cv = rx1.getString(3);
                        }
                        getterSetters.Localizer(rx1.getString(1), rx1.getString(2), cv, "3", rx1.getString(4), rx1.getString(5), rx1.getString(6), rx1.getString(7));
                    }
                    tkbck = "finised pulling LGA / District data... successful";
                    System.out.println(tkbck);
                }

                if (request.getParameter("co") != null && "true".equals(request.getParameter("co"))) {

                    System.out.println("ward" + request.getParameter("co"));
                    ps2 = conn.prepareStatement("SELECT changedate, uuid, externalid, name, id, district_id, creationdate FROM community;");
                    rx2 = ps2.executeQuery();
                    while (rx2.next()) {
                        String cz = "";
                        if (rx2.getString(3) == null) {
                            cz = "0";
                        } else {
                            cz = rx2.getString(3);
                        }
                        // getterSetters.Localizer(ch, cm, cc, cx, co);
                        getterSetters.Localizer(rx2.getString(1), rx2.getString(2), cz, "4", rx2.getString(4), rx2.getString(5), rx2.getString(6), rx2.getString(7));
                    }
                    tkbck = "finised pulling Ward/Community data... NOW PULLING HF.. This will take some minutes";
                    System.out.println(tkbck);
                }

                if (request.getParameter("fa") != null && "true".equals(request.getParameter("fa"))) {
                    System.out.println("health facility !" + request.getParameter("fa"));
                    ps3 = conn.prepareStatement("SELECT changedate, uuid, externalid, name, id, community_id, creationdate FROM facility;");
                    rx3 = ps3.executeQuery();
                    while (rx3.next()) {
                        String cq = "";
                        if (rx3.getString(3) == null) {
                            cq = "0";
                        } else {
                            cq = rx3.getString(3);
                        }
                        getterSetters.Localizer(rx3.getString(1), rx3.getString(2), cq, "5", rx3.getString(4), rx3.getString(5), rx3.getString(6), rx3.getString(7));
                    }
                    tkbck = "finised pulling facility data... successful";
                    System.out.println(tkbck);
                }
                //  out.write("<script> var dd = document.getElementById('d_text').innerHTML = 'processing ward/community... done.';</script>");
                //  System.out.println(lister);
                conn.close();

                if (tkbck.isEmpty()) {
                    response.setContentType("text/plain;charset=UTF-8");
                    response.setStatus(200);
                    ServletOutputStream sout = response.getOutputStream();

                    sout.print(tkbck);
                } else {
                    response.setContentType("text/plain;charset=UTF-8");
                    response.setStatus(200);
                    ServletOutputStream sout = response.getOutputStream();

                    sout.print("something is wrong! in localizer");

                }

            } catch (SQLException ex) {
                out.print("SQLException: " + ex.getMessage());
                out.print("SQLState: " + ex.getSQLState());
                out.print("VendorError: " + ex.getErrorCode());
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(localizerz.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String year, month = "";
        Map<String, String> payloadRequest = getBody(request);
//        System.out.println(payloadRequest);
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DbConnector.getPgConnection();
            String str = "";
//            PreparedStatement ps;
            ResultSet rx;
//             ps = conn.prepareStatement("SELECT * from cases Where date_part('year', reportdate) = ? AND date_part('month', reportdate) = ?");

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
            ps.setInt("year", Integer.parseInt(payloadRequest.get("year")));
            ps.setInt("month", Integer.parseInt(payloadRequest.get("month")));

            rx = ps.executeQuery();
            //System.out.println(rx.toString());
            List<DhimsDataValue> dhimsList = new ArrayList<DhimsDataValue>();
            while (rx.next()) {
                DhimsDataValue dh = new DhimsDataValue(rx.getString("dataElement"), rx.getString("categoryOptionCombo"), rx.getString("period"), rx.getString("orgUnit"), rx.getString("value"));
                String _u = dh.orgUnit;
                dh.orgUnit = orgUnit.externalid(dh.orgUnit);
                System.out.println("hahahah: " + _u + ": " + dh.orgUnit);
                dhimsList.add(dh);
            }
            
            //System.out.println(dhimsList);
            
            List<DhimsDataValue> dhimsList2 = new ArrayList<DhimsDataValue>();
            for (DhimsDataValue d : dhimsList) {
                if (d.orgUnit.length() > 0)
                    dhimsList2.add(d);
            }
            dhimsList = dhimsList2.subList(0, 1);
            dhimsList2 = null;
            
            DHIS2resolver.PostMethod("/api/dataValueSets", dhimsList, "dataValues");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(greport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException se) {
            System.err.println(se);
        } catch(Exception ef){
            System.err.println(ef);
        }

    }

    static String report_deaths(String column, String gender, int ageMin, int ageMax) {
        System.out.println(categoryOptionCombo.code(column));
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
                + "	AND date_part('year', c.reportdate) = :year\n"
                + "	AND date_part('month', c.reportdate) = :month\n"
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
