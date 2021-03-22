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
package com.mirabilia.org.hzi.Util.EntryControllers;

import com.mirabilia.org.hzi.Util.PasswordUtils;
import com.mirabilia.org.hzi.Util.Users;
import com.mirabilia.org.hzi.sormas.doa.DbConnector;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Augustus otu
 */
public class AuthUser {

    static String salt = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";

    public static Users validate(String username, String password) throws ClassNotFoundException, SQLException, InvalidKeySpecException {
        PreparedStatement ps;
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DbConnector.getConnection();

        String str = "";
        try {
            ps = conn.prepareStatement("Select * from users where username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String securePass = rs.getString(3);
                boolean passwordMatch = PasswordUtils.verifyUserPassword(password, securePass, salt);
                if (passwordMatch) {
                    Users user = new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                    return user;
                } else {
                    return new Users();
                }
            }
        } finally {
            conn.close();
        }

        return new Users();
    }

    public static Users comparePassword(String username, String oldPassword, String newPassword) throws ClassNotFoundException, SQLException, InvalidKeySpecException {

        PreparedStatement ps;
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DbConnector.getConnection();

        String str = "";
        try {
            ps = conn.prepareStatement("Select * from users where username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String securePass = rs.getString(3);
                boolean passwordMatch = PasswordUtils.verifyUserPassword(oldPassword, securePass, salt);
                if (passwordMatch) {
                    ps = conn.prepareStatement("UPDATE users SET password =? where username = ?");
                    ps.setString(1, PasswordUtils.generateSecurePassword(newPassword, salt));
                    ps.setString(2, username);
                    ps.executeUpdate();
                    Users user = new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
                    return user;
                } else {
                    return new Users();
                }
            }
        } finally {
            conn.close();
        }

        return new Users();
    }
    
    
    public static List<Users> getUsers(){
        List<Users> userList = new ArrayList<Users>();
         PreparedStatement ps;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        Connection conn = DbConnector.getConnection();

        String str = "";
        try {
            ps = conn.prepareStatement("Select * from users");
            ResultSet rs =ps.executeQuery(); 
            while(rs.next()){
            Users user = new Users(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
            userList.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AuthUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(AuthUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        return userList;
    }
}
