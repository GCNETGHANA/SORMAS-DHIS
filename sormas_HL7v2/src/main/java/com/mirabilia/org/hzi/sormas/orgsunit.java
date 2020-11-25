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
            System.out.println(request.getParameter("region"));
            System.out.println("here");
            List<String> rests = new ArrayList<>();
            //loop through sormas local and get infrastructure data by level into mysql local db for futher use by the adapter.
            try {
                if (request.getParameter("region") != null && "yes".equals(request.getParameter("region"))) {

                    ps = conn.prepareStatement("SELECT name as region FROM region;");
                    rx = ps.executeQuery();
                    while (rx.next()) {
                        rests.add(rx.getString(1));
                    }
                }
                if (request.getParameter("regionSelected") != null) {
                    String region = request.getParameter("regionSelected");
                    System.out.println(region);
                    ps = conn.prepareStatement("SELECT d.name as districts from district d left join region r on r.id = d.region_id where r.name = ?");
                    ps.setString(1, region);
                    rx = ps.executeQuery();
                    while (rx.next()) {
                        rests.add(rx.getString(1));
                    }
                }

                if (request.getParameter("districtSelected") != null) {
                    String district = request.getParameter("districtSelected");
                    System.out.println(district);
                    ps = conn.prepareStatement("SELECT d.name as facilities from facility d left join district r on r.id = d.district_id where r.name = ?");
                    ps.setString(1, district);
                    rx = ps.executeQuery();
                    while (rx.next()) {
                        rests.add(rx.getString(1));
                    }
                }

                conn.close();
                String resString = this.gson.toJson(rests);
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
