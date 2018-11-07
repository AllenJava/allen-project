package com.infinite.java8;

import java.util.Optional;

import com.infinite.service.bo.CurrentUserInfo;

public class OptionalTest {
    
    public static void main(String[] args) {
        
//        CurrentUserInfo cuser=new CurrentUserInfo();
//        String roleName=cuser.getRoleInfo().getName();
//        System.out.println(roleName);
        
        //使用optional
        CurrentUserInfo cuser=null;
        //Optional<CurrentUserInfo> optUser=Optional.of(cuser);
        Optional<CurrentUserInfo> optUser=Optional.ofNullable(cuser);
        System.out.println(optUser.get().getRoleInfo());
    }

}
