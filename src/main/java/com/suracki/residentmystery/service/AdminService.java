package com.suracki.residentmystery.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suracki.residentmystery.domain.*;
import com.suracki.residentmystery.domain.temporary.GameDao;
import com.suracki.residentmystery.domain.temporary.GameData;
import com.suracki.residentmystery.domain.temporary.GameState;
import com.suracki.residentmystery.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
public class AdminService {

    UserRepository userRepository;
    RoomRepository roomRepository;
    InteractableRepository interactableRepository;
    LootRepository lootRepository;
    ExitMappingRepository exitMappingRepository;
    EndingRepository endingRepository;

    private Gson gson;

    private static final Logger logger = LogManager.getLogger(AdminService.class);

    public AdminService(UserRepository userRepository, RoomRepository roomRepository,
                       InteractableRepository interactableRepository, LootRepository lootRepository,
                       ExitMappingRepository exitMappingRepository, EndingRepository endingRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.interactableRepository = interactableRepository;
        this.lootRepository = lootRepository;
        this.exitMappingRepository = exitMappingRepository;
        this.endingRepository = endingRepository;
        gson = new GsonBuilder().setLenient().setPrettyPrinting().create();
        logger.info("AdminService created");


    }

    public String manage(Model model, String type) {
        logger.info("AdminService 'manage' called.");

        model.addAttribute("type", type);
        model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("interactables", interactableRepository.findAll());
        model.addAttribute("loots", lootRepository.findAll());
        model.addAttribute("exitMappings", exitMappingRepository.findAll());
        model.addAttribute("endings", endingRepository.findAll());

        return "admin/manage";
    }

    public String exportData(Model model) {

        logger.info("AdminService 'export' called.");

        List<Room> rooms = roomRepository.findAll();
        List<Interactable> interactables = interactableRepository.findAll();
        List<Loot> loots = lootRepository.findAll();
        List<ExitMapping> exitMappings = exitMappingRepository.findAll();
        List<Ending> endings = endingRepository.findAll();

        GameData gameData = new GameData();
        gameData.setRooms(rooms);
        gameData.setLoots(loots);
        gameData.setInteractables(interactables);
        gameData.setExitMappings(exitMappings);
        gameData.setEndings(endings);

        String gameJson = gson.toJson(gameData);
        model.addAttribute("gameData", gameJson);

        return "/admin/export";
    }

    public String importData(Model model, GameDao gameDao) {

        logger.info("AdminService 'import' called. JSON recieved: ");
        logger.info(gson.toJson(gameDao));

        try {
            GameData importedData = gson.fromJson(gameDao.getJson(), GameData.class);

            List<Room> rooms = importedData.getRooms();
            List<Interactable> interactables = importedData.getInteractables();
            List<Loot> loots = importedData.getLoots();
            List<ExitMapping> exitMappings = importedData.getExitMappings();
            List<Ending> endings = importedData.getEndings();

            logger.info("JSON parsed successfully. Data found:");
            logger.info("Rooms: " + rooms.size());
            logger.info("Interactables: " + interactables.size());
            logger.info("Loots: " + loots.size());
            logger.info("Exit Mappings: " + exitMappings.size());
            logger.info("Endings: " + endings.size());

            logger.info("Starting database: Rooms: " + roomRepository.findAll().size() +
                    " Interactables: " + interactableRepository.findAll().size() +
                    " Loots: " + lootRepository.findAll().size() +
                    " Exit Mappings: " + exitMappingRepository.findAll().size() +
                    " Endings: " + endingRepository.findAll().size());

            roomRepository.deleteAll();
            interactableRepository.deleteAll();
            lootRepository.deleteAll();
            exitMappingRepository.deleteAll();
            endingRepository.deleteAll();

            logger.info("Cleared database: Rooms: " + roomRepository.findAll().size() +
                    " Interactables: " + interactableRepository.findAll().size() +
                    " Loots: " + lootRepository.findAll().size() +
                    " Exit Mappings: " + exitMappingRepository.findAll().size() +
                    " Endings: " + endingRepository.findAll().size());

            roomRepository.saveAll(rooms);
            interactableRepository.saveAll(interactables);
            lootRepository.saveAll(loots);
            exitMappingRepository.saveAll(exitMappings);
            endingRepository.saveAll(endings);

            logger.info("Updated database: Rooms: " + roomRepository.findAll().size() +
                    " Interactables: " + interactableRepository.findAll().size() +
                    " Loots: " + lootRepository.findAll().size() +
                    " Exit Mappings: " + exitMappingRepository.findAll().size() +
                    " Endings: " + endingRepository.findAll().size());

            model.addAttribute("imported", "true");
            return "/admin/landing";
        }
        catch (Exception e) {
            logger.error("GSON failed to parse JSON string: " + gameDao.getJson());
            model.addAttribute("imported", "gson_fail");
            return "/admin/landing";
        }
    }

    public String addRoom(Model model, Room room) {
        logger.info("AdminService 'addRoom' called.");
        return "admin/gui/addRoom";
    }

    public String validateRoom(Model model, BindingResult result, Room room) {
        logger.info("AdminService 'validateRoom' called.");
        if (!result.hasErrors()) {
            roomRepository.save(room);
            return manage(model, "room");
        }
        return "admin/gui/addRoom";
    }

    public String deleteRoom(Integer id, Model model) {
        roomRepository.deleteById(id);
        return manage(model, "room");
    }

    public String updateRoom(int id, Model model) {
        try {
            Room room = roomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
            model.addAttribute("room", room);
            return "admin/gui/editRoom";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("edit","id_not_found");
            return "/admin/landing";
        }

    }

    public String updateRoom(int id, Room room, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/gui/editRoom";
        }
        room.setId(id);
        roomRepository.save(room);
        return manage(model, "room");
    }

    public String addLoot(Model model, Loot loot) {
        logger.info("AdminService 'addRoom' called.");
        return "admin/gui/addLoot";
    }

    public String validateLoot(Model model, BindingResult result, Loot loot) {
        logger.info("AdminService 'validateRoom' called.");
        if (!result.hasErrors()) {
            lootRepository.save(loot);
            return manage(model, "loot");
        }
        return "admin/gui/addLoot";
    }

    public String deleteLoot(Integer id, Model model) {
        lootRepository.deleteById(id);
        return manage(model, "loot");
    }

    public String updateLoot(int id, Model model) {
        try {
            Loot loot = lootRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
            model.addAttribute("loot", loot);
            return "admin/gui/editLoot";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("edit","id_not_found");
            return "/admin/landing";
        }

    }

    public String updateLoot(int id, Loot loot, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/gui/editLoot";
        }
        loot.setId(id);
        lootRepository.save(loot);
        return manage(model, "loot");
    }

    public String addInter(Model model, Interactable interactable) {
        logger.info("AdminService 'addInter' called.");
        return "admin/gui/addInter";
    }

    public String validateInter(Model model, BindingResult result, Interactable interactable) {
        logger.info("AdminService 'validateInter' called.");
        if (!result.hasErrors()) {
            interactableRepository.save(interactable);
            return manage(model, "interactable");
        }
        return "admin/gui/addInter";
    }

    public String deleteInter(Integer id, Model model) {
        interactableRepository.deleteById(id);
        return manage(model, "interactable");
    }

    public String updateInter(int id, Model model) {
        try {
            Interactable interactable = interactableRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
            model.addAttribute("interactable", interactable);
            return "admin/gui/editInter";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("edit","id_not_found");
            return "/admin/landing";
        }

    }

    public String updateInter(int id, Interactable interactable, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/gui/editRoom";
        }
        interactable.setId(id);
        interactableRepository.save(interactable);
        return manage(model, "interactable");
    }

    public String addExit(Model model, ExitMapping exitMapping) {
        logger.info("AdminService 'addExit' called.");
        return "admin/gui/addExitMap";
    }

    public String validateExit(Model model, BindingResult result, ExitMapping exitMapping) {
        logger.info("AdminService 'validateExit' called.");
        if (!result.hasErrors()) {
            exitMappingRepository.save(exitMapping);
            return manage(model, "exitMapping");
        }
        return "admin/gui/addExitMap";
    }

    public String deleteExit(Integer id, Model model) {
        exitMappingRepository.deleteById(id);
        return manage(model, "exitMapping");
    }

    public String updateExit(int id, Model model) {
        try {
            ExitMapping exitMapping = exitMappingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
            model.addAttribute("exitMapping", exitMapping);
            return "admin/gui/editExitMap";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("edit","id_not_found");
            return "/admin/landing";
        }

    }

    public String updateExit(int id, ExitMapping exitMapping, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/gui/editExit";
        }
        exitMapping.setId(id);
        exitMappingRepository.save(exitMapping);
        return manage(model, "exitMapping");
    }

    public String addEnding(Model model, Ending ending) {
        logger.info("AdminService 'addEnding' called.");
        return "admin/gui/addEnding";
    }

    public String validateEnding(Model model, BindingResult result, Ending ending) {
        logger.info("AdminService 'validateEnding' called.");
        if (!result.hasErrors()) {
            endingRepository.save(ending);
            return manage(model, "ending");
        }
        return "admin/gui/addEnding";
    }

    public String deleteEnding(Integer id, Model model) {
        endingRepository.deleteById(id);
        return manage(model, "ending");
    }

    public String updateEnding(int id, Model model) {
        try {
            Ending ending = endingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
            model.addAttribute("ending", ending);
            return "admin/gui/editEnding";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("edit","id_not_found");
            return "/admin/landing";
        }

    }

    public String updateEnding(int id, Ending ending, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "admin/gui/editEnding";
        }
        ending.setId(id);
        endingRepository.save(ending);
        return manage(model, "ending");
    }
}
