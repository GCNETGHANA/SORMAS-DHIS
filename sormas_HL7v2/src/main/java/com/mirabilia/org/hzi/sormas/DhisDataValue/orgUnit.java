/*
 * Copyright (c) 2020, augan
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
package com.mirabilia.org.hzi.sormas.DhisDataValue;

import com.mirabilia.org.hzi.sormas.doa.DbConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author augan
 */
public class orgUnit {

    public String id;
    public String externalId;

    public orgUnit(String id, String externalId) {
        this.id = id;
        this.externalId = externalId;
    }

    static List<orgUnit> _list;

    public static List<orgUnit> list() {
        if (_list != null) {
            return _list;
        }
        _list = new ArrayList<orgUnit>();
        Connection conn = DbConnector.getConnection();
        ResultSet rx;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT CONVERT(uid, CHAR) id,externalid from sormas_local WHERE externalid <> '0'");
            rx = ps.executeQuery();
            while (rx.next()) {
                _list.add(new orgUnit(rx.getString("id"), rx.getString("externalid")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(orgUnit.class.getName()).log(Level.SEVERE, null, ex);

        }
       
        return _list;
    }

    public static String externalid(final String id) {
        orgUnit item = list().stream().filter(new Predicate<orgUnit>() {
            @Override
            public boolean test(orgUnit n) {
                return n.id.equals(id);
            }
        }).findFirst().orElse(null);

        if (item == null) {
            return "";
        }

        return item.externalId;
    }
}
