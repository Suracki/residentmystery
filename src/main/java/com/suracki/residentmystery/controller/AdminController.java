package com.suracki.residentmystery.controller;

import com.suracki.residentmystery.domain.Room;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        logger.info("User connected to /admin/landing endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return "/admin/landing";
    }

    @GetMapping("/admin/manage")
    public String manage(Model model)
    {
        logger.info("User connected to /admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.manage(model, "all");
    }

    @GetMapping("/admin/filter")
    public String filter(@RequestParam(value="type") String type, Model model)
    {
        logger.info("User connected to /admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.manage(model, type);
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

    @GetMapping("/admin/addRoom")
    public String addRoom(Model model, Room room)
    {
        logger.info("User connected to /admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.addRoom(model, room);
    }

    @PostMapping("/admin/addRoom/validate")
    public String addRoomValidate(Model model, @Valid Room room, BindingResult result)
    {
        logger.info("User connected to /admin/manage endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.validateRoom(model, result, room);
    }

    @GetMapping("/admin/deleteRoom/{id}")
    public String deleteRoom(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteRoom endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteRoom(id, model);
    }

    @GetMapping("/admin/updateRoom/{id}")
    public String updateRoom(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateRoom endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateRoom(id, model);
    }

    @PostMapping("/admin/updateRoom/{id}")
    public String updateRoom(@PathVariable("id") Integer id, Model model,
                             @Valid Room room, BindingResult result)
    {
        logger.info("User connected to admin/updateRoom POST endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateRoom(id, room, result, model);
    }

}
