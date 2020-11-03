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

import static com.mirabilia.org.hzi.sormas.DhisDataValue.categoryOptionCombo._list;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author augan
 */
public class dataElement {

    public String name;
    public String code;

    public dataElement(String name, String code) {
        this.name = name;
        this.code = code;
    }

    static List<dataElement> _list;

    public static List<dataElement> list() {
        if (_list != null) {
            return _list;
        }
        
        _list = new ArrayList<dataElement>();
        _list.add(new dataElement("Number of new cases hospitalised", "TO8IanhHkHA"));
        _list.add(new dataElement("Number of new deaths", "MV3otDWIPpz"));
        _list.add(new dataElement("Suspected cases by transmission classification\\t", "X75THcHgnjq"));
        _list.add(new dataElement("Number of cases tested", "MBVAaUTsYi6"));
        _list.add(new dataElement("Number of new cases treated", "tEeMGqyVCVx"));
        _list.add(new dataElement("Number of recovered cases", "xa3lXAIiGM2"));
        _list.add(new dataElement("Number of new suspected cases", "bgq6BpHz9BS"));
        _list.add(new dataElement("Number of new confirmed cases", "NktJlhFFDhf"));
        _list.add(new dataElement("Confirmed cases by transmission classification", "PbR7KDrmkUV"));

        return _list;
    }

    public static String code(final String name) {
        dataElement item = list().stream().filter(new Predicate<dataElement>() {
            @Override
            public boolean test(dataElement n) {
                return n.name.equals(name);
            }
        }).findFirst().orElse(null);

        if (item == null) {
            return "";
        }

        return item.code;
    }
}
