package com.suracki.residentmystery.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FailureController implements ErrorController {

    private static final Logger logger = LogManager.getLogger(FailureController.class);

    @RequestMapping("/error")
    public String error(Model model)
    {
        logger.info("User connected to error endpoint");
        model.addAttribute("user","generic_error");
        return "index";
    }

}
