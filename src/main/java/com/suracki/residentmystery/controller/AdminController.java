package com.suracki.residentmystery.controller;

import com.suracki.residentmystery.domain.ExitMapping;
import com.suracki.residentmystery.domain.Interactable;
import com.suracki.residentmystery.domain.Loot;
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
        logger.info("User connected to /admin/addRoom endpoint");
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
        logger.info("User connected to /admin/addRoom/validate endpoint");
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

    @GetMapping("/admin/addLoot")
    public String addLoot(Model model, Loot loot)
    {
        logger.info("User connected to /admin/addLoot endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.addLoot(model, loot);
    }

    @PostMapping("/admin/addLoot/validate")
    public String addLootValidate(Model model, @Valid Loot loot, BindingResult result)
    {
        logger.info("User connected to /admin/addLoot/validate endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.validateLoot(model, result, loot);
    }

    @GetMapping("/admin/deleteLoot/{id}")
    public String deleteLoot(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteLoot endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteLoot(id, model);
    }

    @GetMapping("/admin/updateLoot/{id}")
    public String updateLoot(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateLoot endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateLoot(id, model);
    }

    @PostMapping("/admin/updateLoot/{id}")
    public String updateRoom(@PathVariable("id") Integer id, Model model,
                             @Valid Loot loot, BindingResult result)
    {
        logger.info("User connected to admin/updateLoot POST endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateLoot(id, loot, result, model);
    }

    @GetMapping("/admin/addInter")
    public String addInter(Model model, Interactable interactable)
    {
        logger.info("User connected to /admin/addInter endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.addInter(model, interactable);
    }

    @PostMapping("/admin/addInter/validate")
    public String addInterValidate(Model model, @Valid Interactable interactable, BindingResult result)
    {
        logger.info("User connected to /admin/addInter/validate endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.validateInter(model, result, interactable);
    }

    @GetMapping("/admin/deleteInter/{id}")
    public String deleteInter(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteInter endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteInter(id, model);
    }

    @GetMapping("/admin/updateInter/{id}")
    public String updateInter(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateInter endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateInter(id, model);
    }

    @PostMapping("/admin/updateInter/{id}")
    public String updateInter(@PathVariable("id") Integer id, Model model,
                             @Valid Interactable interactable, BindingResult result)
    {
        logger.info("User connected to admin/updateInter POST endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateInter(id, interactable, result, model);
    }

    @GetMapping("/admin/addExit")
    public String addExit(Model model, ExitMapping exitMapping)
    {
        logger.info("User connected to /admin/addExit endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.addExit(model, exitMapping);
    }

    @PostMapping("/admin/addExit/validate")
    public String addExitValidate(Model model, @Valid ExitMapping exitMapping, BindingResult result)
    {
        logger.info("User connected to /admin/addExit/validate endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.validateExit(model, result, exitMapping);
    }

    @GetMapping("/admin/deleteExit/{id}")
    public String deleteExit(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteExit endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteExit(id, model);
    }

    @GetMapping("/admin/updateExit/{id}")
    public String updateExit(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateExit endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateExit(id, model);
    }

    @PostMapping("/admin/updateExit/{id}")
    public String updateExit(@PathVariable("id") Integer id, Model model,
                             @Valid ExitMapping exitMapping, BindingResult result)
    {
        logger.info("User connected to admin/updateExit POST endpoint for room with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateExit(id, exitMapping, result, model);
    }
}
