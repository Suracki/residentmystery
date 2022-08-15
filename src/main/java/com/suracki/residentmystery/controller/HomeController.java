package com.suracki.residentmystery.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * HomeController
 *
 * Provides endpoint for home/landing page
 *
 */
@Controller
public class HomeController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    /**
     * Mapping for GET
     *
     * Serves landing/home page
     *
     * @param model Model
     * @return landing page template
     */
    @RequestMapping("/")
    public String home(Model model)
    {
        logger.info("User connected to root home endpoint");
        model.addAttribute("user","");
        return "index";
    }

}
