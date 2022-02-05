package com.sample.webservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller class for the Swagger API doc.
 *
 * @author Vishnu Viswambharan
 * @version 1.0
 * @since 2020-09-26
 */
@Controller
public class ApiDocController {
    @RequestMapping("/apidoc")
    public String home() {
        // By default following is the access URL of Swagger API doc.
        // So we are redirecting the request as follows
        return "redirect:/swagger-ui/index.html";
    }
}
