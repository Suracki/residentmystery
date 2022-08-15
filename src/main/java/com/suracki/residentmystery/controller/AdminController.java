package com.suracki.residentmystery.controller;

import com.suracki.residentmystery.domain.*;
import com.suracki.residentmystery.domain.temporary.GameDao;
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

/**
 *
 * AdminController
 *
 * Provides endpoints for administration services
 *
 * Allows for management of game data; mass import/export, and add/edit/remove for individual elements
 *
 */
@Controller
public class AdminController {

    private static final Logger logger = LogManager.getLogger(AdminController.class);

    @Autowired
    AdminService adminService;

    @Autowired
    private RoleCheck roleCheck;

    /**
     * Mapping for GET
     *
     * Serves Admin landing/home page
     *
     * @param model Model
     * @return landing page template
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to manage game entities
     *
     * @param model Model
     * @return manage entities page with full set of entities stored in Model
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to manage game entities of a specific type
     *
     * @param model Model
     * @return manage entities page with select set of entities stored in Model
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to export current game data as JSON string
     *
     * @param model Model
     * @return export data page
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to import new game data from JSON string
     *
     * @param model Model
     * @param gameDao object to hold game data
     * @return import data page
     */
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

    /**
     * Mapping for POST
     *
     * Attempts to import new game data from JSON string
     *
     * @param model Model
     * @param gameDao object to hold game data
     * @return landing page, with error notification if unsuccessful
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to add new game element
     *
     * @param model Model
     * @param room object to hold game data
     * @return add element form page
     */
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

    /**
     * Mapping for POST
     *
     * Attempts to add new game element from form provided data
     *
     * @param model Model
     * @param room object to hold game data
     * @param result validity check result
     * @return manage data page if successful, add element page if error found
     */
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

    /**
     * Mapping for GET
     *
     * Attempts to delete an element from the game data
     *
     * @param model Model
     * @param id element to be removed
     * @return manage data page
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to update existing game element
     *
     * @param model Model
     * @param id element to be updated
     * @return update element form page
     */
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

    /**
     * Mapping for POST
     *
     * Attempts to update game element from form provided data
     *
     * @param model Model
     * @param id element to be update
     * @param room object to hold game data
     * @param result validity check result
     * @return manage data page if successful, update element page if error found
     */
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

    /**
     * Mapping for GET
     *
     * Serves page to add new game element
     *
     * @param model Model
     * @param loot object to hold game data
     * @return add element form page
     */
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

    /**
     * Mapping for POST
     *
     * Attempts to add new game element from form provided data
     *
     * @param model Model
     * @param loot object to hold game data
     * @param result validity check result
     * @return manage data page if successful, add element page if error found
     */
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

    /**
     * Mapping for GET
     *
     * Attempts to delete an element from the game data
     *
     * @param model Model
     * @param id element to be removed
     * @return manage data page
     */
    @GetMapping("/admin/deleteLoot/{id}")
    public String deleteLoot(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteLoot endpoint for loot with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteLoot(id, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to update existing game element
     *
     * @param model Model
     * @param id element to be updated
     * @return update element form page
     */
    @GetMapping("/admin/updateLoot/{id}")
    public String updateLoot(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateLoot endpoint for loot with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateLoot(id, model);
    }

    /**
     * Mapping for POST
     *
     * Attempts to update game element from form provided data
     *
     * @param model Model
     * @param id element to be update
     * @param loot object to hold game data
     * @param result validity check result
     * @return manage data page if successful, update element page if error found
     */
    @PostMapping("/admin/updateLoot/{id}")
    public String updateRoom(@PathVariable("id") Integer id, Model model,
                             @Valid Loot loot, BindingResult result)
    {
        logger.info("User connected to admin/updateLoot POST endpoint for loot with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateLoot(id, loot, result, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to add new game element
     *
     * @param model Model
     * @param interactable object to hold game data
     * @return add element form page
     */
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

    /**
     * Mapping for POST
     *
     * Attempts to add new game element from form provided data
     *
     * @param model Model
     * @param interactable object to hold game data
     * @param result validity check result
     * @return manage data page if successful, add element page if error found
     */
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

    /**
     * Mapping for GET
     *
     * Attempts to delete an element from the game data
     *
     * @param model Model
     * @param id element to be removed
     * @return manage data page
     */
    @GetMapping("/admin/deleteInter/{id}")
    public String deleteInter(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteInter endpoint for interactable with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteInter(id, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to update existing game element
     *
     * @param model Model
     * @param id element to be updated
     * @return update element form page
     */
    @GetMapping("/admin/updateInter/{id}")
    public String updateInter(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateInter endpoint for interactable with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateInter(id, model);
    }

    /**
     * Mapping for POST
     *
     * Attempts to update game element from form provided data
     *
     * @param model Model
     * @param id element to be update
     * @param interactable object to hold game data
     * @param result validity check result
     * @return manage data page if successful, update element page if error found
     */
    @PostMapping("/admin/updateInter/{id}")
    public String updateInter(@PathVariable("id") Integer id, Model model,
                             @Valid Interactable interactable, BindingResult result)
    {
        logger.info("User connected to admin/updateInter POST endpoint for interactable with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateInter(id, interactable, result, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to add new game element
     *
     * @param model Model
     * @param exitMapping object to hold game data
     * @return add element form page
     */
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

    /**
     * Mapping for POST
     *
     * Attempts to add new game element from form provided data
     *
     * @param model Model
     * @param exitMapping object to hold game data
     * @param result validity check result
     * @return manage data page if successful, add element page if error found
     */
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

    /**
     * Mapping for GET
     *
     * Attempts to delete an element from the game data
     *
     * @param model Model
     * @param id element to be removed
     * @return manage data page
     */
    @GetMapping("/admin/deleteExit/{id}")
    public String deleteExit(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteExit endpoint for exit with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteExit(id, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to update existing game element
     *
     * @param model Model
     * @param id element to be updated
     * @return update element form page
     */
    @GetMapping("/admin/updateExit/{id}")
    public String updateExit(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateExit endpoint for exit with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateExit(id, model);
    }

    /**
     * Mapping for POST
     *
     * Attempts to update game element from form provided data
     *
     * @param model Model
     * @param id element to be update
     * @param exitMapping object to hold game data
     * @param result validity check result
     * @return manage data page if successful, update element page if error found
     */
    @PostMapping("/admin/updateExit/{id}")
    public String updateExit(@PathVariable("id") Integer id, Model model,
                             @Valid ExitMapping exitMapping, BindingResult result)
    {
        logger.info("User connected to admin/updateExit POST endpoint for exit with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateExit(id, exitMapping, result, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to add new game element
     *
     * @param model Model
     * @param ending object to hold game data
     * @return add element form page
     */
    @GetMapping("/admin/addEnding")
    public String addEnding(Model model, Ending ending)
    {
        logger.info("User connected to /admin/addEnding endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.addEnding(model, ending);
    }

    /**
     * Mapping for POST
     *
     * Attempts to add new game element from form provided data
     *
     * @param model Model
     * @param ending object to hold game data
     * @param result validity check result
     * @return manage data page if successful, add element page if error found
     */
    @PostMapping("/admin/addEnding/validate")
    public String addEndingValidate(Model model, @Valid Ending ending, BindingResult result)
    {
        logger.info("User connected to /admin/addEnding/validate endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.validateEnding(model, result, ending);
    }

    /**
     * Mapping for GET
     *
     * Attempts to delete an element from the game data
     *
     * @param model Model
     * @param id element to be removed
     * @return manage data page
     */
    @GetMapping("/admin/deleteEnding/{id}")
    public String deleteEnding(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteEnding endpoint for ending with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteEnding(id, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to update existing game element
     *
     * @param model Model
     * @param id element to be updated
     * @return update element form page
     */
    @GetMapping("/admin/updateEnding/{id}")
    public String updateEnding(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateEnding endpoint for ending with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateEnding(id, model);
    }

    /**
     * Mapping for POST
     *
     * Attempts to update game element from form provided data
     *
     * @param model Model
     * @param id element to be update
     * @param ending object to hold game data
     * @param result validity check result
     * @return manage data page if successful, update element page if error found
     */
    @PostMapping("/admin/updateEnding/{id}")
    public String updateEnding(@PathVariable("id") Integer id, Model model,
                             @Valid Ending ending, BindingResult result)
    {
        logger.info("User connected to admin/updateEnding POST endpoint for ending with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateEnding(id, ending, result, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to add new game element
     *
     * @param model Model
     * @param npc object to hold game data
     * @return add element form page
     */
    @GetMapping("/admin/addNpc")
    public String addNpc(Model model, Npc npc)
    {
        logger.info("User connected to /admin/addNpc endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.addNpc(model, npc);
    }

    /**
     * Mapping for POST
     *
     * Attempts to add new game element from form provided data
     *
     * @param model Model
     * @param npc object to hold game data
     * @param result validity check result
     * @return manage data page if successful, add element page if error found
     */
    @PostMapping("/admin/addNpc/validate")
    public String addEndingNpc(Model model, @Valid Npc npc, BindingResult result)
    {
        logger.info("User connected to /admin/addNpc/validate endpoint");
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.validateNpc(model, result, npc);
    }

    /**
     * Mapping for GET
     *
     * Attempts to delete an element from the game data
     *
     * @param model Model
     * @param id element to be removed
     * @return manage data page
     */
    @GetMapping("/admin/deleteNpc/{id}")
    public String deleteNpc(@PathVariable("id") Integer id, Model model) {
        logger.info("User connected to admin/deleteNpc endpoint for npc with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.deleteNpc(id, model);
    }

    /**
     * Mapping for GET
     *
     * Serves page to update existing game element
     *
     * @param model Model
     * @param id element to be updated
     * @return update element form page
     */
    @GetMapping("/admin/updateNpc/{id}")
    public String updateNpc(@PathVariable("id") Integer id, Model model)
    {
        logger.info("User connected to admin/updateNpc endpoint for npc with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateNpc(id, model);
    }

    /**
     * Mapping for POST
     *
     * Attempts to update game element from form provided data
     *
     * @param model Model
     * @param id element to be update
     * @param npc object to hold game data
     * @param result validity check result
     * @return manage data page if successful, update element page if error found
     */
    @PostMapping("/admin/updateNpc/{id}")
    public String updateNpc(@PathVariable("id") Integer id, Model model,
                               @Valid Npc npc, BindingResult result)
    {
        logger.info("User connected to admin/updateNpc POST endpoint for npc with id " + id);
        if (!roleCheck.RoleCheck("Admin")) {
            logger.info("User is not an ADMIN, logging out and redirecting");
            SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
            return "redirect:/";
        }
        return adminService.updateNpc(id, npc, result, model);
    }
}
