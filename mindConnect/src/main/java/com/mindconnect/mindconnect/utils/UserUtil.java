package com.mindconnect.mindconnect.utils;

import com.mindconnect.mindconnect.exceptions.MindConnectException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtil {


    public static String getLoggedInUser(){
        Object Principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Principal != null && Principal instanceof UserDetails){
            return ((UserDetails)Principal).getUsername();
        } else if(Principal != null){
            return Principal.toString();
        } else {
            throw new MindConnectException("no user is currently logged in");
        }
    }
}
