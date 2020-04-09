package com.company.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {
    
    @RequestMapping(path = {"/"}, method = RequestMethod.GET)
    public String indexMapping(){
        return "index";
    }
    
    @RequestMapping(path = {"comision"}, method = RequestMethod.POST)
    public String getFee(@RequestParam("summ") double summ, @RequestParam("currency") String currency){
        System.out.println("sum : " + summ + " currency : " + currency);
        return "index";
    }
    
}
