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

import com.mirabilia.org.hzi.sormas.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author augan
 */
public class AgeRange {

    public String name;
    public int min;
    public int max;

    public AgeRange(String name, int min, int max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    static List<AgeRange> _list;

    public static List<AgeRange> list() {
        if (_list != null) {
            return _list;
        }
        
        _list = new ArrayList<AgeRange>();
        _list.add(new AgeRange("0-1 years", 0, 1));
        _list.add(new AgeRange("2-4 years", 2, 4));
        _list.add(new AgeRange("5-14 years", 5, 14));
        _list.add(new AgeRange("15-49 years", 15, 49));
        _list.add(new AgeRange("50-64 years", 50, 64));
        _list.add(new AgeRange("65-79 years", 65, 79));
        _list.add(new AgeRange("80+ years", 80, 1000));

        return _list;
    }
}
