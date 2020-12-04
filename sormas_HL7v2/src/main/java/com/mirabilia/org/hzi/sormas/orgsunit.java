package com.mirabilia.org.hzi.sormas;

import com.mirabilia.org.hzi.sormas.doa.DbConnector;
import static com.mirabilia.org.hzi.sormas.getterSetters.Localizer_Deleter;
import java.io.IOException;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.mirabilia.org.hzi.sormas.DhisDataValue.FacilityData;
import com.mirabilia.org.hzi.sormas.DhisDataValue.RegionData;

/**
 *
 * @author Mathew Official
 */
@WebServlet(name = "orgsunit", urlPatterns = {"/orgsunit"})
public class orgsunit extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
            String resString;

            List<RegionData> rests = new ArrayList<>();
            List<FacilityData> FacRests = new ArrayList<>();
            //loop through sormas local and get infrastructure data by level into mysql local db for futher use by the adapter.
            try {
                if (request.getParameter("region") != null && "yes".equals(request.getParameter("region"))) {

                    ps = conn.prepareStatement("SELECT name as region, id as regionId FROM region;");
                    rx = ps.executeQuery();
                    while (rx.next()) {
                        RegionData rD = new RegionData(rx.getString(1), rx.getInt(2));
                        rests.add(rD);
                    }
                }
                if (request.getParameter("districtSelected") != null) {
                    int district = Integer.parseInt(request.getParameter("districtSelected"));
                
                    ps = conn.prepareStatement("SELECT name, id FROM community where district_id  = ?");
                    ps.setInt(1, district);
                    rx = ps.executeQuery();
                    while (rx.next()) {

                        RegionData rD = new RegionData(rx.getString(1), rx.getInt(2));
                        rests.add(rD);
                    }
                }

                if (request.getParameter("subDistrictSelected") != null) {
                    int district = Integer.parseInt(request.getParameter("subDistrictSelected"));

                    ps = conn.prepareStatement("SELECT d.name as facilityName , d.externalid as facilityId FROM facility d where d.externalid is not null AND d.community_id = ?");

                    ps.setInt(1, district);
                    rx = ps.executeQuery();
                    while (rx.next()) {
                        FacilityData rD = new FacilityData(rx.getString(1), rx.getString(2));
                        FacRests.add(rD);
                    }
                }

                if (request.getParameter("regionSelected") != null) {
                    int region = Integer.parseInt(request.getParameter("regionSelected"));
                    ps = conn.prepareStatement("SELECT d.name as districts, d.id as districtsId from district d left join region r on r.id = d.region_id where r.id = ?");
                    ps.setInt(1, region);
                    rx = ps.executeQuery();
                    while (rx.next()) {
                        RegionData rD = new RegionData(rx.getString(1), rx.getInt(2));
                        rests.add(rD);
                    }
                }

                conn.close();
                if (request.getParameter("subDistrictSelected") != null) {
                    resString = this.gson.toJson(FacRests);
                } else {
                    resString = this.gson.toJson(rests);
                }
                PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(resString);
                out.flush();

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

    }

}
