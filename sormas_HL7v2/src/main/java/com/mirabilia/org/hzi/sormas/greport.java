
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
        int district = 0, region  = 0, subDistrict = 0;
        String facility = "";
        Map<String, String> payloadRequest = getBody(request);
        int year = Integer.parseInt(payloadRequest.get("year"));
        int month = Integer.parseInt(payloadRequest.get("month"));
        System.out.println("----The year selected is: " + year + " and Month: " + month);
        if(Integer.parseInt(payloadRequest.get("region")) == 0){
      
        }else if(Integer.parseInt(payloadRequest.get("district")) == 0){
            region = Integer.parseInt(payloadRequest.get("region"));
         
        }else if(Integer.parseInt(payloadRequest.get("subDistrict")) == 0){
            region = Integer.parseInt(payloadRequest.get("region"));
            district  = Integer.parseInt(payloadRequest.get("district"));
         
        }else if(String.valueOf(payloadRequest.get("facility")) == "0"){
            region = Integer.parseInt(payloadRequest.get("region"));
             district  = Integer.parseInt(payloadRequest.get("district"));
             subDistrict = Integer.parseInt(payloadRequest.get("subDistrict"));
        } else{
         region = Integer.parseInt(payloadRequest.get("region"));
         district  = Integer.parseInt(payloadRequest.get("district"));
         facility = payloadRequest.get("facility");
         subDistrict = Integer.parseInt(payloadRequest.get("subDistrict"));
        }
        
        
        System.out.println(region+district+facility);

        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DbConnector.getPgConnection();
            ResultSet rx;

            List<String> queries = new ArrayList<String>();
            String where = getWhereClauses(region, district, facility, subDistrict);
          
            queries.add(report_case_outcome(where));
            queries.add(report_case_classification(where));
            queries.add(report_case_hospitalised(where));
            queries.add(report_case_tested(where));
            queries.add(lab_results(where));
            queries.add(cases_in_icu(where));
            queries.add(cases_by_treatment(where));
             queries.add(cases_by_origin(where));

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
    
    
    public static void AutoPost(int year, int month){
         int district = 0, region  = 0, subDistrict = 0;
         String facility = "";
         
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DbConnector.getPgConnection();
            ResultSet rx;

            List<String> queries = new ArrayList<String>();
            String where = getWhereClauses(region, district, facility, subDistrict);
          
            queries.add(report_case_outcome(where));
            queries.add(report_case_classification(where));
            queries.add(report_case_hospitalised(where));
            queries.add(report_case_tested(where));
            queries.add(lab_results(where));
            queries.add(cases_in_icu(where));
            queries.add(cases_by_treatment(where));
             queries.add(cases_by_origin(where));

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
           System.out.println(resp);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(greport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException se) {
            System.err.println(se);
        } catch (Exception ef) {
            System.err.println(ef);
        }
        
    }
    
    
    static String getWhereClauses(int region, int district, String facility, int subDistrict){
            if(!"0".equals(facility) && !"".equals(facility)){
                return "f.externalId = '"+facility+"' AND ";
            }else if(subDistrict != 0){
                return "f.community_id ="+subDistrict+" AND ";
            }else if(district != 0){
                return "f.district_id ="+district+" AND ";
            }else if(region != 0){
                return "f.region_id = "+region+" AND ";
            }else {
                return "";
            }
    }

    static String report_case_outcome(String where) {
        
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
                + where 
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

    static String report_case_classification(String where) {

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
                + where 
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

    static String report_case_hospitalised(String where) {

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
                + where 
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

    static String report_case_tested(String where) {

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
                + where 
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

    
    static String lab_results(String where){
        return " SELECT\n" +
"      COALESCE (f.externalid, '') orgUnit,\n" +
"      TO_CHAR( COALESCE ( C.classificationdate, C.reportdate ), 'YYYYMM' ) \"period\",\n" +
"      CASE\n" +
"         h.testresult \n" +
"         WHEN\n" +
"            'PENDING' \n" +
"         THEN\n" +
"            'HllvX50cXC0' \n" +
"		else 'HllvX50cXC0'\n" +
"      END\n" +
"      categoryOptionCombo, \n" +
"      CASE\n" +
"         h.testresult \n" +
"         WHEN\n" +
"            'PENDING' \n" +
"         THEN\n" +
"            'tLXtycv8mUh' \n" +
"		else 'Mbty4SXtpxg'\n" +
"      END\n" +
"      dataElement, COUNT (C.ID) \"value\" \n" +
"   FROM\n" +
"      cases C \n" +
"      LEFT JOIN\n" +
"         person P \n" +
"         ON C.person_id = P.ID \n" +
"      LEFT JOIN\n" +
"         facility f \n" +
"         ON C.healthfacility_id = f.ID \n" +
"      LEFT JOIN\n" +
"         samples s \n" +
"         ON C.ID = s.associatedcase_id \n" +
"      LEFT JOIN\n" +
"         pathogentest h \n" +
"         ON s.ID = h.sample_id \n" +
"   WHERE\n" + where  +
"      h.testresult = 'PENDING' \n" +
"      AND date_part( 'year', COALESCE (h.creationdate) ) = :year \n" +
"      AND date_part( 'month', COALESCE (h.creationdate) ) = :month \n" +
"      AND C.disease = 'CORONAVIRUS' \n" +
"   GROUP BY\n" +
"      f.externalid, to_char( COALESCE ( C.classificationdate, C.reportdate ), 'YYYYMM' ), h.testresult ";
    }
    
    
    
    
    
    
    static String cases_in_icu(String where){
        return "SELECT COALESCE\n" +
"	( f.externalid, '' ) orgUnit,\n" +
"	TO_CHAR( COALESCE ( h.intensivecareunitstart, h.admissiondate ), 'YYYYMM' ) \"period\",\n" +
"CASE\n" +
"		h.intensivecareunit \n" +
"		WHEN 'YES' THEN\n" +
"		'HllvX50cXC0' \n" +
"	END categoryOptionCombo,\n" +
"CASE\n" +
"	h.intensivecareunit \n" +
"	WHEN 'YES' THEN\n" +
"	'UIbgJ9wH2C6' \n" +
"	END dataElement,\n" +
"	COUNT ( DISTINCT ( C.ID ) ) \"value\" \n" +
"FROM\n" +
"	cases\n" +
"	C LEFT JOIN person P ON C.person_id = P.\n" +
"	ID LEFT JOIN facility f ON C.healthfacility_id = f.\n" +
"	ID LEFT JOIN hospitalization h ON C.hospitalization_id = h.ID \n" +
"WHERE\n" + where +
"	date_part( 'year', COALESCE ( h.intensivecareunitstart, h.admissiondate ) ) = :year \n" +
"	AND date_part( 'month', COALESCE ( h.intensivecareunitstart, h.admissiondate ) ) = :month \n" +
"	AND C.disease = 'CORONAVIRUS' \n" +
"GROUP BY\n" +
"	f.externalid,\n" +
"	to_char( COALESCE ( h.intensivecareunitstart, h.admissiondate ), 'YYYYMM' ),\n" +
"	h.intensivecareunit";
    }
    
    
    static String cases_by_treatment(String where){
        return "SELECT COALESCE\n" +
"	( f.externalid, '' ) orgUnit,\n" +
"	TO_CHAR( COALESCE ( T.treatmentdatetime ), 'YYYYMM' ) \"period\",\n" +
"CASE\n" +
"		T.treatmenttype \n" +
"	WHEN NULL THEN\n" +
"		NULL ELSE'HllvX50cXC0' \n" +
"	END categoryOptionCombo,\n" +
"CASE\n" +
"	T.treatmenttype \n" +
"	WHEN 'INVASIVE_MECHANICAL_VENTILATION' THEN\n" +
"	'UPKwAOWlnfT' \n" +
"	WHEN 'OXYGEN_THERAPY' THEN\n" +
"	'OgN0gpmQScF' \n" +
"	END dataElement,\n" +
"	COUNT ( DISTINCT ( C.ID ) ) \"value\" \n" +
"FROM\n" +
"	cases\n" +
"	C LEFT JOIN person P ON C.person_id = P.\n" +
"	ID LEFT JOIN facility f ON C.healthfacility_id = f.\n" +
"	ID LEFT JOIN treatment T ON T.therapy_id = C.therapy_id \n" +
"WHERE\n" + where +
"	date_part( 'year', COALESCE ( T.treatmentdatetime ) ) = :year \n" +
"	AND date_part( 'month', COALESCE ( T.treatmentdatetime ) ) = :month \n" +
"	AND C.disease = 'CORONAVIRUS' \n" +
"GROUP BY\n" +
"	f.externalid,\n" +
"	to_char( COALESCE ( T.treatmentdatetime ), 'YYYYMM' ),\n" +
"	T.treatmenttype";
    }
    
    static String cases_by_origin(String where){
        return "SELECT COALESCE\n" +
"	( f.externalid, '' ) orgUnit,\n" +
"	TO_CHAR( COALESCE ( C.creationdate ), 'YYYYMM' ) \"period\",\n" +
"CASE\n" +
"		C.caseorigin \n" +
"	WHEN NULL THEN\n" +
"		NULL ELSE'HllvX50cXC0' \n" +
"	END categoryOptionCombo,\n" +
"CASE\n" +
"	\n" +
"	WHEN C.caseorigin = 'POINT_OF_ENTRY' \n" +
"	AND C.caseclassification = 'SUSPECT' THEN\n" +
"	'mvZaCAISwWW' \n" +
"	WHEN C.caseorigin = 'IN_COUNTRY' \n" +
"	AND C.caseclassification = 'SUSPECT' THEN\n" +
"	'iUYqseHElNb' \n" +
"	WHEN C.caseorigin = 'POINT_OF_ENTRY' \n" +
"	AND C.caseclassification = 'CONFIRMED' THEN\n" +
"	'KmzXt7SEY5d' \n" +
"	WHEN C.caseorigin = 'IN_COUNTRY' \n" +
"	AND C.caseclassification = 'CONFIRMED' THEN\n" +
"	'aQ8B5d4K35h' \n" +
"	END dataElement,\n" +
"	COUNT ( C.ID ) \"value\" \n" +
"FROM\n" +
"	cases\n" +
"	C LEFT JOIN person P ON C.person_id = P.\n" +
"	ID LEFT JOIN facility f ON C.healthfacility_id = f.ID \n" +
"WHERE\n" + where +
"	date_part( 'year', COALESCE ( C.creationdate ) ) = :year \n" +
"	AND date_part( 'month', COALESCE ( C.creationdate ) ) = :month \n" +
"	AND C.disease = 'CORONAVIRUS' \n" +
"GROUP BY\n" +
"	f.externalid,\n" +
"	to_char( COALESCE ( C.creationdate ), 'YYYYMM' ),\n" +
"	C.caseorigin,\n" +
"	C.caseclassification,\n" +
"	C.creationdate ";
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
