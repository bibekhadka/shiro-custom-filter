package com.stormpath.shiro.samples.springboot.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/")
public class ApiController {    

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public String signin(@RequestParam(value = "email") String email) {
        String jwt = null;
        try{
        byte[] signKey = "my secret key".getBytes("UTF-8");
        jwt = Jwts.builder()
                .setSubject(email)
                .claim("customclaim", "Any custom claim")
                .signWith(
                        SignatureAlgorithm.HS256,
                        signKey
                )
                .compact();
        }catch(Exception e){
            e.printStackTrace();
        }
        return jwt;
    }
   

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/secret")
    public String getTermsAndConditions(@PathVariable(value = "id") String userId) {
        return "Secret api authorized with jwt";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/private")
    public String getPrivacy(@PathVariable(value = "id") String userId) {
        return "Private api authorized with jwt";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/important")
    public String getImportant(@PathVariable(value = "id") String userId) {
        return "Important api authorized with jwt";
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    String invalidResource(HttpServletResponse response, Exception e) {
        response.setHeader("message", e.getMessage());
        return "Sorry";
    }

}
