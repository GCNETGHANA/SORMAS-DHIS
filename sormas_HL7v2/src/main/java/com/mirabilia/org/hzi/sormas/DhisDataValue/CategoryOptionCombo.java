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
public class CategoryOptionCombo {

    public String name;
    public String code;

    public CategoryOptionCombo(String name, String code) {
        this.name = name;
        this.code = code;
    }

    static List<CategoryOptionCombo> _list;

    public static List<CategoryOptionCombo> list() {
        if (_list != null) {
            return _list;
        }
        
        _list = new ArrayList<CategoryOptionCombo>();
        _list.add(new CategoryOptionCombo("0-1 years, Male", "DFarWJgBUzs"));
        _list.add(new CategoryOptionCombo("0-1 years, Female", "s9jIoEZfhis"));
        _list.add(new CategoryOptionCombo("2-4 years, Male", "u4qovKOiWp3"));
        _list.add(new CategoryOptionCombo("2-4 years, Female", "tjHJPYaKDw5"));
        _list.add(new CategoryOptionCombo("5-14 years, Male", "GPdx8dY2rUQ"));
        _list.add(new CategoryOptionCombo("5-14 years, Female", "wIJGxmWx77w"));
        _list.add(new CategoryOptionCombo("15-49 years, Male", "yHxBhsyra0s"));
        _list.add(new CategoryOptionCombo("15-49 years, Female", "j1rXiqATlZ0"));
        _list.add(new CategoryOptionCombo("50-64 years, Male", "n5xLnGJxdSY"));
        _list.add(new CategoryOptionCombo("50-64 years, Female", "Ys9vFeSqLTr"));
        _list.add(new CategoryOptionCombo("65-79 years, Male", "E19zZXrQicF"));
        _list.add(new CategoryOptionCombo("65-79 years, Female", "MCfMP9XnUFb"));
        _list.add(new CategoryOptionCombo("80+ years, Male", "Cizo3NsSYtl"));
        _list.add(new CategoryOptionCombo("80+ years, Female", "cYoRyk1HDXR"));

        _list.add(new CategoryOptionCombo("default", "Joer6DI3Xaf"));

        _list.add(new CategoryOptionCombo("Known Cluster", "BYFKGvQj8CE"));
        _list.add(new CategoryOptionCombo("Mechanical ventilation", "iOkiG5qOpKi"));
        _list.add(new CategoryOptionCombo("Admitted in intensive care unit (ICU)", "qJr1qHMKd9Q"));
        _list.add(new CategoryOptionCombo("Imported", "nAPHg41lnbH"));
        _list.add(new CategoryOptionCombo("ECMO", "ucHvuTQyhbm"));
        _list.add(new CategoryOptionCombo("Unknown Classification", "qnodejqk3WT"));
        _list.add(new CategoryOptionCombo("Community transmission", "N1gkTFt8yqs"));

        return _list;
    }

    public static String code(final String name) {
        CategoryOptionCombo item = list().stream().filter(new Predicate<CategoryOptionCombo>() {
            @Override
            public boolean test(CategoryOptionCombo n) {
                return n.name.equals(name);
            }
        }).findFirst().orElse(null);

        if (item == null) {
            return "";
        }

        return item.code;
    }
}
