package com.suracki.residentmystery.controller;

import com.suracki.residentmystery.domain.temporary.GameDao;
import com.suracki.residentmystery.domain.temporary.GameData;
import com.suracki.residentmystery.security.RoleCheck;
import com.suracki.residentmystery.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AdminController {

    private static final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    private RoleCheck roleCheck;

    @GetMapping("/admin/landing")
    public String start(Model model)
    {
        logger.info("User connected to /admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return "/admin/landing";
    }

    @GetMapping("/admin/export")
    public String exportData(Model model)
    {
        logger.info("User connected to /admin/export endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.exportData(model);
    }

    @GetMapping("/admin/import")
    public String importData(GameDao gameDao, Model model)
    {
        logger.info("User connected to /admin/export endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return "admin/import";
    }

    @PostMapping("/admin/import/perform")
    public String importDataPerform(@Valid GameDao gameDao, Model model)
    {
        logger.info("User connected to /admin/export endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.importData(model, gameDao);
    }

}
