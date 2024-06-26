package com.green.greengram.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade {
    public MyUser getLogInUser() {
        MyUserDetails myUserDetails = (MyUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        return myUserDetails == null ? null : myUserDetails.getMyUser();
    }

    public long getLogInUserId() {
        MyUser myUser = getLogInUser();
        if (myUser != null) {
            return getLogInUser().getUserId();
        }
        return 0;
    }
}
