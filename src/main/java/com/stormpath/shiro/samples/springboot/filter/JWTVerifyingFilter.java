package com.stormpath.shiro.samples.springboot.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.stereotype.Component;

@Component
public class JWTVerifyingFilter extends AccessControlFilter {

    String message;

    @Override
    protected boolean isAccessAllowed(ServletRequest req, ServletResponse res, Object o) throws Exception {
        System.out.print("Inside Custom jwt filter");
        boolean accessAllowed = false;
        HttpServletRequest request = (HttpServletRequest) req;
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken != null) {
            Jws<Claims> claims = null;
            try {
                byte[] signKey = "my secret key".getBytes("UTF-8");
                claims = Jwts.parser()
                        .setSigningKey(signKey)
                        .parseClaimsJws(jwtToken);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return claims != null;
            //TODO implement proper verification logic with the claims

        } else {
            this.message = "NO TOKEN!";
        }
        return accessAllowed;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest req, ServletResponse res) throws Exception {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setHeader("message", message);
        return false;
    }

}
