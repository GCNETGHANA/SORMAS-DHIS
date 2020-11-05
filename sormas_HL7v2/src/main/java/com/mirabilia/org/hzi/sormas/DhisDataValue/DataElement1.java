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
public class DataElement {

    public String name;
    public String code;

    public DataElement(String name, String code) {
        this.name = name;
        this.code = code;
    }

    static List<DataElement> _list;

    public static List<DataElement> list() {
        if (_list != null) {
            return _list;
        }
        
        _list = new ArrayList<DataElement>();
        _list.add(new DataElement("Number of recovered cases", "pppW1UbL0JL"));
        _list.add(new DataElement("Number of new deaths", "cw4v7izzo7L"));
        
        _list.add(new DataElement("Number of new suspected cases", "ouOn4KYL9JY"));
        _list.add(new DataElement("Number of new confirmed cases", "MLWvGWMYfvl"));

        _list.add(new DataElement("Number of new cases hospitalised", "TO8IanhHkHA"));
        _list.add(new DataElement("Suspected cases by transmission classification\\t", "X75THcHgnjq"));
        _list.add(new DataElement("Number of cases tested", "MBVAaUTsYi6"));
        _list.add(new DataElement("Number of new cases treated", "tEeMGqyVCVx"));
        _list.add(new DataElement("Confirmed cases by transmission classification", "PbR7KDrmkUV"));

        return _list;
    }

    public static String code(final String name) {
        DataElement item = list().stream().filter(new Predicate<DataElement>() {
            @Override
            public boolean test(DataElement n) {
                return n.name.equals(name);
            }
        }).findFirst().orElse(null);

        if (item == null) {
            return "";
        }

        return item.code;
    }
}
