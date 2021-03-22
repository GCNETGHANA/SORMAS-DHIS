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
package com.mirabilia.org.hzi.Util;

/**
 *
 * @author Augustus otu
 */
public class Users {
    public int id;
    public Boolean loggedIn;
    public String username, password, region, regionValue, access, createdAt;
    public Users(){
        this.loggedIn = false;
    }
    
    public Users(int id, String username, String password, String region, String regionValue, String access, String createdAt){
        this.id = id;
        this.username = username;
        this.password = password;
        this.region = region;
        this.regionValue = regionValue;
        this.access = access;
        this.createdAt = createdAt;
        this.loggedIn = true;
        
    }
    public Users( String username, String password, String region, String regionValue, String access){
        this.username = username;
        this.password = password;
        this.region = region;
        this.regionValue = regionValue;
        this.access = access;
        
    }
}
