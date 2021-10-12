
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
@WebServlet(name = "reportconfig", urlPatterns = {"/reportconfig"})
public class reportconfig extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
     
    }
    
    

    
    
   
}
