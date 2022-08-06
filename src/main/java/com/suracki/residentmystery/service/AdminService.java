package com.suracki.residentmystery.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.suracki.residentmystery.domain.ExitMapping;
import com.suracki.residentmystery.domain.Interactable;
import com.suracki.residentmystery.domain.Loot;
import com.suracki.residentmystery.domain.Room;
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

    private Gson gson;

    private static final Logger logger = LogManager.getLogger(AdminService.class);

    public AdminService(UserRepository userRepository, RoomRepository roomRepository,
                       InteractableRepository interactableRepository, LootRepository lootRepository,
                       ExitMappingRepository exitMappingRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.interactableRepository = interactableRepository;
        this.lootRepository = lootRepository;
        this.exitMappingRepository = exitMappingRepository;
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

        return "admin/manage";
    }

    public String exportData(Model model) {

        logger.info("AdminService 'export' called.");

        List<Room> rooms = roomRepository.findAll();
        List<Interactable> interactables = interactableRepository.findAll();
        List<Loot> loots = lootRepository.findAll();
        List<ExitMapping> exitMappings = exitMappingRepository.findAll();

        GameData gameData = new GameData();
        gameData.setRooms(rooms);
        gameData.setLoots(loots);
        gameData.setInteractables(interactables);
        gameData.setExitMappings(exitMappings);

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

            logger.info("JSON parsed successfully. Data found:");
            logger.info("Rooms: " + rooms.size());
            logger.info("Interactables: " + interactables.size());
            logger.info("Loots: " + loots.size());
            logger.info("Exit Mappings: " + exitMappings.size());

            logger.info("Starting database: Rooms: " + roomRepository.findAll().size() +
                    " Interactables: " + interactableRepository.findAll().size() +
                    " Loots: " + lootRepository.findAll().size() +
                    " Exit Mappings: " + exitMappingRepository.findAll().size());

            roomRepository.deleteAll();
            interactableRepository.deleteAll();
            lootRepository.deleteAll();
            exitMappingRepository.deleteAll();

            logger.info("Cleared database: Rooms: " + roomRepository.findAll().size() +
                    " Interactables: " + interactableRepository.findAll().size() +
                    " Loots: " + lootRepository.findAll().size() +
                    " Exit Mappings: " + exitMappingRepository.findAll().size());

            roomRepository.saveAll(rooms);
            interactableRepository.saveAll(interactables);
            lootRepository.saveAll(loots);
            exitMappingRepository.saveAll(exitMappings);

            logger.info("Updated database: Rooms: " + roomRepository.findAll().size() +
                    " Interactables: " + interactableRepository.findAll().size() +
                    " Loots: " + lootRepository.findAll().size() +
                    " Exit Mappings: " + exitMappingRepository.findAll().size());

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

}
