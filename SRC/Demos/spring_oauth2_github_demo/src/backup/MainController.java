package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping(value ="/login/oauth2/code/github", method = RequestMethod.GET)
    public void getMain(){
        System.out.println("hjihi");
    }
    @RequestMapping(value ="/login/oauth2/code/github", method = RequestMethod.POST)
    public void postMain(){
        System.out.println("hjihi");
    }
    @RequestMapping(value ="/oauth2/authorization/github", method = RequestMethod.GET)
    public void getNotMain(){
        System.out.println("hjihi");
    }
}
