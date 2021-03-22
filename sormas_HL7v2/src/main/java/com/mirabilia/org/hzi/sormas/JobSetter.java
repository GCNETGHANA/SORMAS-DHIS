/*
 * Copyright (c) 2021, Augustus otu
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

import com.mirabilia.org.Util.jobs.Scheduler;
import static com.mirabilia.org.hzi.sormas.greport.getBody;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.Policy.Parameters;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.ConfigurationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.configuration.PropertiesConfiguration;


/**
 *
 * @author Augustus otu
 */
@WebServlet(name = "JobSetter", urlPatterns = {"/JobSetter"})
public class JobSetter extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet JobSetter</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet JobSetter at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String t = getTitle("reportJobTitle");
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println(t);
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Map<String, String> payloadRequest = getBody(request);
            String title = payloadRequest.get("schedule");
            String expression = getExpression(title);
            String theTitle = getMainTitle(title);
            PrintWriter out = response.getWriter();
            response.setContentType("text/html;charset=UTF-8");
            Boolean done = setTitle(theTitle, expression);
            if(done){
                Scheduler.shutdown();
                out.println("Successful operation");
            }else{
                out.println("An error occured");
            }
        } catch (ConfigurationException ex) {
            Logger.getLogger(JobSetter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (org.apache.commons.configuration.ConfigurationException ex) {
            Logger.getLogger(JobSetter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private String getExpression(String title){
        String value = null;
        switch(title){
            case ("everyHour"):
            value =  "0 * * * *";
            break;
            case ("everyHalfDay"):
            value =  "0 */12 * * *";
            break;
            case ("everyDay"):
            value =  "0 0 * * *";
            break;
            case ("everyWeek"):
            value =  "0 0 * * 0";
            break;
            case ("everyMonth"):
            value = "0 0 1 * *";
            break;
            default:
            value =  "0 0 * * *";   
            break;
        }
        System.out.println(value +":"+title);
        return value;
    }
    private String getMainTitle(String title){
        String value = null;
        switch(title){
            case ("everyHour"):
            value =  "Every Hour";
            break;
            case ("everyHalfDay"):
            value =  "Every Half day";
            break;
            case ("everyDay"):
            value =  "Every Day";
            break;
            case ("everyWeek"):
            value =  "Every Week";
            break;
            case ("everyMonth"):
            value = "Every Month";
            break;
            default:
            value =  "Every Day";   
            break;
        }
        System.out.println(value +":"+title);
        return value;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    public String getTitle(String key) {
        InputStream inputStream = null;
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader().getResourceAsStream("job.properties");
			prop.load(inputStream);
			String value =  prop.getProperty(key);
                        System.out.println(value);
                        return value;
		} catch (FileNotFoundException ex) {
			System.err.println("Property file '" + "job.properties" + "' not found in the classpath");
			ex.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
        return "";
    }

    public Boolean setTitle(String title, String expression) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
    File propertiesFile = new File(getClass().getClassLoader().getResource("job.properties").getFile());		
		PropertiesConfiguration config = new PropertiesConfiguration(propertiesFile);		
		config.setProperty("reportJobTitle", title);
		config.setProperty("reportJobExpression", expression);
		config.save();
                return true;

    }

}
