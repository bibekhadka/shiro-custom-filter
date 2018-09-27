package com.stormpath.shiro.samples.springboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//NO NEED AUTH FOR THIS

@RestController
@RequestMapping(value = "/test")
public class TestController {    

    @RequestMapping(method = RequestMethod.GET, value = "/djkhaleed")
    public String getTest() {        
        return "BLESS UP!";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/anotherone")
    public String getAdUnit() {
        
        return "Another One";
    }
}
