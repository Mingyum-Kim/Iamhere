package com.personal.member.config;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionDestroyed(HttpSessionEvent event){
        HttpSession session = event.getSession();

        if(session.getAttribute("mailVerified")!= null && session.getAttribute("userMail")!= null){
            boolean mailVerified = (boolean) session.getAttribute("mailVerified");
            String userMail = (String) session.getAttribute("userMail");
            if(mailVerified){
                session.invalidate();
                session.setAttribute("mailVerified", mailVerified);
                session.setAttribute("userMail", userMail);
            }
        }
    }
}
