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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author augan
 */
public class CaseReportByOutcome {

    public String code;
    public String name;

    public String dataElement () {
        return CaseReportByOutcome.dataElement(code);
    }

    public CaseReportByOutcome (String code, String name) {
        this.code = code;
        this.name = name;
    }

    static List<CaseReportByOutcome> _list;

    public static List<CaseReportByOutcome> list() {
        if (_list != null) {
            return _list;
        }
        _list = new ArrayList<CaseReportByOutcome>();
        _list.add(new CaseReportByOutcome("DECEASED", "Number of new deaths"));
        _list.add(new CaseReportByOutcome("RECOVERED", "Number of recovered cases"));

        return _list;
    }
    
    public static String name (final String code) {
        CaseReportByOutcome item = list().stream().filter(new Predicate<CaseReportByOutcome>() {
            @Override
            public boolean test(CaseReportByOutcome n) {
                return n.code.equals(code);
            }
        }).findFirst().orElse(null);

        if (item == null) {
            return "";
        }

        return item.name;
    }
    
    public static String dataElement (final String code) {
        String name = name(code);
        return DataElement.code(name);        
    }
}
