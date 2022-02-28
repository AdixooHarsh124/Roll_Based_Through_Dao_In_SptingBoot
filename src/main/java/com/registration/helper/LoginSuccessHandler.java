package com.registration.helper;

import com.registration.services.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    {
//        String value;
        try {

            System.out.println("is authenticate"+authentication.getAuthorities());
           CustomUserDetails customUserDetails= (CustomUserDetails) authentication.getPrincipal();
            String redirectURL= request.getContextPath();
            for (GrantedAuthority authority : authentication.getAuthorities()) {

                if(authority.getAuthority().equals("ADMIN"))
                {
                    redirectURL+="/admin/home";
                    System.out.println("user url : "+redirectURL);
                }  else if(authority.getAuthority().equals("USER"))
                {
                    redirectURL+="/user/home";
                    System.out.println("admin url : "+redirectURL);
                }
            }
            response.sendRedirect(redirectURL);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
