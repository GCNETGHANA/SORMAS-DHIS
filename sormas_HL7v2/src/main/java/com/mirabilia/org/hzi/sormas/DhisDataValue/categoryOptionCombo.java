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
public class categoryOptionCombo {

    public String name;
    public String code;

    public categoryOptionCombo(String name, String code) {
        this.name = name;
        this.code = code;
    }

    static List<categoryOptionCombo> _list;

    public static List<categoryOptionCombo> list() {
        if (_list != null) {
            return _list;
        }
        
        _list = new ArrayList<categoryOptionCombo>();
        _list.add(new categoryOptionCombo("15-49 years, Male", "ujcU9CRK17O"));
        _list.add(new categoryOptionCombo("15-49 years, Female", "jYt2qZSSHWp"));
        _list.add(new categoryOptionCombo("2-4 years, Male", "n9pdJamZz11"));
        _list.add(new categoryOptionCombo("50-64 years, Female", "EWofZ3FULsh"));
        _list.add(new categoryOptionCombo("5-14 years, Male", "XpRz5kz7KC0"));
        _list.add(new categoryOptionCombo("0-1 years, Male", "qeDIPI8DGWH"));
        _list.add(new categoryOptionCombo("5-14 years, Female", "XBlE4pXHRv5"));
        _list.add(new categoryOptionCombo("0-1 years, Female", "G1vthcQ3koH"));
        _list.add(new categoryOptionCombo("2-4 years, Female", "p3TXpPEK0oh"));
        _list.add(new categoryOptionCombo("50-64 years, Male", "kdIGdK69Kko"));
        _list.add(new categoryOptionCombo("Male, Female, Male, Female, Male, Female, Male, Female, Male, Female, Male, Female", "T8hRxKHspPL"));
        _list.add(new categoryOptionCombo("Female, Female, Female, Female, Female, Female", "xW9RbGoEfPp"));
        _list.add(new categoryOptionCombo("Male, Male, Male, Male, Male, Male", "Jun9KkSrwyq"));

        _list.add(new categoryOptionCombo("default", "HllvX50cXC0"));
        _list.add(new categoryOptionCombo("Known Cluster", "BYFKGvQj8CE"));
        _list.add(new categoryOptionCombo("Mechanical ventilation", "iOkiG5qOpKi"));
        _list.add(new categoryOptionCombo("Admitted in intensive care unit (ICU)", "qJr1qHMKd9Q"));
        _list.add(new categoryOptionCombo("Imported", "nAPHg41lnbH"));
        _list.add(new categoryOptionCombo("ECMO", "ucHvuTQyhbm"));
        _list.add(new categoryOptionCombo("Unknown Classification", "qnodejqk3WT"));
        _list.add(new categoryOptionCombo("Community transmission", "N1gkTFt8yqs"));

        return _list;
    }

    public static String code(final String name) {
        categoryOptionCombo item = list().stream().filter(new Predicate<categoryOptionCombo>() {
            @Override
            public boolean test(categoryOptionCombo n) {
                return n.name.equals(name);
            }
        }).findFirst().orElse(null);

        if (item == null) {
            return "";
        }

        return item.code;
    }
}
