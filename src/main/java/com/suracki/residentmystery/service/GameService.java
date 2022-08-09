package com.suracki.residentmystery.service;

import com.suracki.residentmystery.domain.*;
import com.suracki.residentmystery.domain.temporary.GameState;
import com.suracki.residentmystery.repository.*;
import com.suracki.residentmystery.security.RoleCheck;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
public class GameService {

    UserRepository userRepository;
    RoomRepository roomRepository;
    InteractableRepository interactableRepository;
    LootRepository lootRepository;
    ExitMappingRepository exitMappingRepository;
    EndingRepository endingRepository;
    static Map<String, GameState> gameStates;

    private RoleCheck roleCheck;

    private static int concurrentPlayerCacheSize = 1000;

    private static final Logger logger = LogManager.getLogger(GameService.class);

    public GameService(UserRepository userRepository, RoomRepository roomRepository,
                       InteractableRepository interactableRepository, LootRepository lootRepository,
                       ExitMappingRepository exitMappingRepository, EndingRepository endingRepository,
                       RoleCheck roleCheck) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.interactableRepository = interactableRepository;
        this.lootRepository = lootRepository;
        this.exitMappingRepository = exitMappingRepository;
        this.endingRepository = endingRepository;
        this.roleCheck = roleCheck;
        gameStates = new LinkedHashMap<>() {
            @Override
            protected boolean removeEldestEntry(final Map.Entry eldest) {
                return size() > concurrentPlayerCacheSize;
            }
        };
        List<Room> roomList = roomRepository.findAll();
        logger.info("GameService created");
        if (roomList.size() == 0) {
            logger.info("No rooms found in database. Performing default setup.");
            setupDemoAdventure();
        }

    }

    private void setupDemoAdventure() {
        //set up loot items:
        Loot redKey = new Loot();
        redKey.setLootName("Red Key");
        redKey.setLootDesc("An old key with a red gem.");
        redKey.setRoomName("Entrance Hall");
        lootRepository.save(redKey);
        Loot ring = new Loot();
        ring.setLootName("Ring");
        ring.setLootDesc("A gold ring, with something indecipherable engraved around the band.");
        ring.setRoomName("Dining Room");
        lootRepository.save(ring);
        Loot goldKey = new Loot();
        goldKey.setLootName("Gold Key");
        goldKey.setLootDesc("An old key made from gold.");
        lootRepository.save(goldKey);
        Loot medal = new Loot();
        medal.setLootName("Medal");
        medal.setLootDesc("A gold medal, with 'you win!' engraved on it.");
        lootRepository.save(medal);

        //set up interactables:
        Interactable redDoor = new Interactable();
        redDoor.setInteractableName("Red Door");
        redDoor.setInteractableDesc("A large ornate door, with a red gem in the handle.");
        redDoor.setLocked(true);
        redDoor.setKeyName("Red Key");
        redDoor.setSolvedText("The door is now unlocked and can be used.");
        redDoor.setAlreadySolvedText("You have already unlocked this door.");
        redDoor.setRoomName("Entrance Hall");
        redDoor.setTarget("");
        interactableRepository.save(redDoor);
        Interactable mainDoor = new Interactable();
        mainDoor.setInteractableName("Main Door");
        mainDoor.setInteractableDesc("The mansion's front door. Large and imposing. The only barrier to your freedom...");
        mainDoor.setLocked(true);
        mainDoor.setKeyName("");
        mainDoor.setSolvedText("");
        mainDoor.setAlreadySolvedText("The door appears to have been unlocked by some hidden mechanism.");
        mainDoor.setRoomName("Entrance Hall");
        mainDoor.setTarget("");
        interactableRepository.save(mainDoor);
        Interactable statue = new Interactable();
        statue.setInteractableName("Statue");
        statue.setInteractableDesc("A life size statue of a person, with an outstretched hand. The ring finger is conspicuously bare.");
        statue.setLocked(true);
        statue.setKeyName("Ring");
        statue.setSolvedText("The ring fits the statue perfectly, and once placed there is a satisfying click sound. The main door opens are you are free again... for now.");
        statue.setAlreadySolvedText("You have already placed the Ring on the statue's finger. There is nothing else to do here.");
        statue.setRoomName("Entrance Hall");
        statue.setTarget("Main Door");
        statue.setContents("Gold Key");
        interactableRepository.save(statue);
        Interactable chest = new Interactable();
        chest.setInteractableName("Chest");
        chest.setInteractableDesc("A large wooden chest");
        chest.setLocked(true);
        chest.setKeyName("Red Key");
        chest.setSolvedText("Your key fits the lock without any trouble, and you unlock the chest.");
        chest.setAlreadySolvedText("The unlocked chest is empty.");
        chest.setRoomName("Outside");
        chest.setContents("Medal");
        chest.setTarget("");
        interactableRepository.save(chest);
        Interactable escape = new Interactable();
        escape.setInteractableName("Escape Route");
        escape.setInteractableDesc("The way out. A large door, with a small round indendation.");
        escape.setLocked(true);
        escape.setKeyName("Medal");
        escape.setSolvedText("You place the medal in the indentation, and the door opens.");
        escape.setAlreadySolvedText("The door is open.");
        escape.setRoomName("Outside");
        escape.setContents("");
        escape.setTarget("");
        escape.setGameEnd(true);
        escape.setEndName("Good End");
        interactableRepository.save(escape);

        //set up exit mappings

        ExitMapping mainExit = new ExitMapping();
        mainExit.setExitName("Main Door");
        mainExit.setExitDirection("South");
        mainExit.setRoomName("Entrance Hall");
        exitMappingRepository.save(mainExit);
        ExitMapping outsideExit = new ExitMapping();
        outsideExit.setExitName("Main Door");
        outsideExit.setExitDirection("North");
        outsideExit.setRoomName("Outside");
        exitMappingRepository.save(outsideExit);
        ExitMapping entranceHallExit = new ExitMapping();
        entranceHallExit.setExitName("Red Door");
        entranceHallExit.setExitDirection("West");
        entranceHallExit.setRoomName("Entrance Hall");
        exitMappingRepository.save(entranceHallExit);
        ExitMapping diningExit = new ExitMapping();
        diningExit.setExitName("Red Door");
        diningExit.setExitDirection("East");
        diningExit.setRoomName("Dining Room");
        exitMappingRepository.save(diningExit);

        //set up rooms
        Room entranceHall = new Room();
        entranceHall.setRoomName("Entrance Hall");
        entranceHall.setRoomDesc("The room is large in size and contains many different types of architecture and grand artwork, including paintings, vases, and candles lining the walls.");
        entranceHall.setStartRoom(true);
        roomRepository.save(entranceHall);
        Room diningRoom = new Room();
        diningRoom.setRoomName("Dining Room");
        diningRoom.setRoomDesc("The room is long but relatively narrow, with a large table stretching it's full length, with chairs along each side. The walls are lined with candles, and there are more candles along the center of the table.");
        diningRoom.setStartRoom(false);
        roomRepository.save(diningRoom);
        Room outside = new Room();
        outside.setRoomName("Outside");
        outside.setRoomDesc("You have escaped from the mansion. Congratulations!");
        outside.setStartRoom(false);
        roomRepository.save(outside);

        //set up ending
        Ending ending = new Ending();
        ending.setEndingText("You have escaped the mansion! You are now free, you definitely should not go right back inside to try again. Oh no, definitely not.");
        ending.setEndingName("Good End");
        endingRepository.save(ending);

        logger.info("GameService setup completed.");
    }


    public String start(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        logger.info("GameService 'start' called for user " + name);

        User user = userRepository.findByUsername(name);
        if (user == null) {
            //error loading user
            logger.error("No user found in database with name: " + name);
            model.addAttribute("user","error");
            return "index";
        }

        roleCheck(model);

        if (gameStates.get(user.getUsername())!=null) {
            //User is already mid-game
            logger.info("GameState found for User; User is mid-game in room " + gameStates.get(user.getUsername()).getCurrentRoom() + ", continuing...");
            return continueGame(model);
        }



        GameState gameState = new GameState();
        gameState.setRooms(roomRepository.findAll());
        gameState.setInteractables(interactableRepository.findAll());
        gameState.setLoots(lootRepository.findAll());
        gameState.setStartTime(LocalDateTime.now());

        gameStates.put(user.getUsername(), gameState);

        Room startingRoom = roomRepository.findStartingRoom();

        gameState.setCurrentRoom(startingRoom.getRoomName());
        gameState.setCurrentLoot(new ArrayList<>());
        gameState.logState();

        Room room = gameState.findRoom(gameState.getCurrentRoom());
        List<ExitMapping> exitMappings = exitMappingRepository.findByRoomname(startingRoom.getRoomName());
        logger.info("Exit mappings found for room: " + exitMappings.size());

        if (exitMappings == null || exitMappings.size()==0) {
            model.addAttribute("exits", new ArrayList<>());
            model.addAttribute("exitDirections", new ArrayList<>());
        }
        else {
            List<String> exits = new ArrayList<>();
            List<String> exitDirections = new ArrayList<>();

            for (ExitMapping mapping : exitMappings) {
                exits.add(mapping.getExitName());
                exitDirections.add(mapping.getExitDirection());
            }

            logger.info("Adding " + exits.size() + " exit names, and " + exitDirections.size() + " exit directions to model.");

            model.addAttribute("exits", exits);
            model.addAttribute("exitDirections", exitDirections);
        }

        List<Loot> loots = lootRepository.findByRoomname(startingRoom.getRoomName());
        List<String> inventory = gameState.getCurrentLoot();
        if (loots == null || loots.size() == 0) {
            loots = new ArrayList<>();
        }
        else {
            Loot found = null;
            for (Loot loot : loots) {
                for (String invItem : inventory) {
                    if (loot.getLootName().equals(invItem)) {
                        found = loot;
                    }
                }
            }
            if (found!=null) {
                loots.remove(found);
            }
        }


        model.addAttribute("roomName", startingRoom.getRoomName());
        model.addAttribute("roomDesc", startingRoom.getRoomDesc());
        model.addAttribute("interactables", interactableRepository.findByRoomname(startingRoom.getRoomName()));
        model.addAttribute("loots", loots);

        logger.info("Starting new game. User " + name + " is starting in room " + startingRoom.getRoomName() + ".");
        logger.info("Room contains " + interactableRepository.findByRoomname(startingRoom.getRoomName()).size() + " interactables, "
                + loots + " loots, " + exitMappings.size() + " exits.");

        return "game/room";
    }

    public String restart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        logger.info("GameService 'restart' called for user " + name);

        User user = userRepository.findByUsername(name);
        if (user == null) {
            //error loading user
            logger.error("No user found in database with name: " + name);
            model.addAttribute("user","error");
            return "index";
        }

        if (gameStates.get(user.getUsername())==null) {
            //User is not already in a game, no need to reset
            logger.info("No GameState found for User; User has not yet started the game. Starting new game...");
            return start(model);
        }

        gameStates.remove(user.getUsername());
        logger.info("GameState cleared for user " + name + ". Starting new game...");
        return start(model);
    }

    public String continueGame(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);

        logger.info("GameService 'continueGame' called for user " + name);

        if (user == null) {
            logger.error("No user found in database with name: " + name);
            model.addAttribute("user","error");
            return "index";
        }

        roleCheck(model);

        GameState gameState = gameStates.get(user.getUsername());
        if (gameStates.get(user.getUsername())==null) {
            //No GameState found for user. Need to restart the game.
            logger.error("No gamestate found for name: " + name + ", restarting and creating new session.");
            return start(model);
        }
        gameState.logState();

        Room room = gameState.findRoom(gameState.getCurrentRoom());
        List<ExitMapping> exitMappings = exitMappingRepository.findByRoomname(room.getRoomName());
        logger.info("Exit mappings found for room: " + exitMappings.size());

        if (exitMappings == null || exitMappings.size()==0) {
            model.addAttribute("exits", new ArrayList<>());
            model.addAttribute("exitDirections", new ArrayList<>());
        }
        else {
            List<String> exits = new ArrayList<>();
            List<String> exitDirections = new ArrayList<>();

            for (ExitMapping mapping : exitMappings) {
                exits.add(mapping.getExitName());
                exitDirections.add(mapping.getExitDirection());
            }

            logger.info("Adding " + exits.size() + " exit names, and " + exitDirections.size() + " exit directions to model.");

            model.addAttribute("exits", exits);
            model.addAttribute("exitDirections", exitDirections);
        }

        List<Loot> loots = lootRepository.findByRoomname(room.getRoomName());
        List<String> inventory = gameState.getCurrentLoot();
        if (loots == null || loots.size() == 0) {
            loots = new ArrayList<>();
        }
        else {
            Loot found = null;
            for (Loot loot : loots) {
                for (String invItem : inventory) {
                    if (loot.getLootName().equals(invItem)) {
                        found = loot;
                    }
                }
            }
            if (found!=null) {
                loots.remove(found);
            }
        }

        model.addAttribute("roomName", room.getRoomName());
        model.addAttribute("roomDesc", room.getRoomDesc());
        model.addAttribute("interactables", interactableRepository.findByRoomname(room.getRoomName()));
        model.addAttribute("loots", loots);
        model.addAttribute("inventory", inventory);

        logger.info("GameState found for user " + name + ", continuning game from room " + room.getRoomName() + ".");
        logger.info("Room contains " + interactableRepository.findByRoomname(room.getRoomName()).size() + " interactables, "
                + loots + " loots, " + exitMappings.size() + " exits.");

        return "game/room";
    }

    public String interact(Model model, String interactableName) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);

        logger.info("GameService 'interact' called for user " + name + ", object " + interactableName);

        if (user == null) {
            logger.error("No user found in database with name: " + name);
            model.addAttribute("user","error");
            return "index";
        }

        GameState gameState = gameStates.get(user.getUsername());
        if (gameStates.get(user.getUsername())==null) {
            //No GameState found for user. Need to restart the game.
            logger.error("No gamestate found for name: " + name + ", restarting and creating new session.");
            return start(model);
        }
        gameState.logState();

        Interactable interactable = gameState.findInteractable(interactableName);
        Room room = gameState.findRoom(gameState.getCurrentRoom());

        if (interactable.isLocked()) {
            logger.info("Interactable " + interactable.getInteractableName() + " is locked.");
            // need to unlock
            if (gameState.hasLoot(interactable.getKeyName())) {
                logger.info("User has the key, unlocking!");
                interactable.setLocked(false);
                model.addAttribute("lockstate", "unlocking");

                if (!interactable.getTarget().equals("")){
                    logger.info("Interactable has target object: " + interactable.getTarget() + ". Unlocking...");
                    for (Interactable iterInteractable : gameState.getInteractables()){
                        if (iterInteractable.getInteractableName().equals(interactable.getTarget())) {
                            iterInteractable.setLocked(false);
                            logger.info(iterInteractable.getInteractableName() + " unlocked!");
                        }
                    }

                }

                if (!interactable.getContents().equals("")) {
                    logger.info("Solved interactable contains loot! Redirecting to loot method.");
                    model.addAttribute("interactableName", interactable.getInteractableName());
                    model.addAttribute("interactableSolvedText", interactable.getSolvedText());
                    return loot(model, interactable.getContents());
                }

                if(interactable.isGameEnd()) {
                    logger.info("User has solved the game, forwarding to ending.");
                    return ending(model, interactableName);
                }
            }
            else {
                logger.info("User does not have the key!");
                model.addAttribute("lockstate", "nokey");
            }
            model.addAttribute("interactableName", interactable.getInteractableName());
            model.addAttribute("interactableDesc", interactable.getInteractableDesc());
            model.addAttribute("interactableSolvedText", interactable.getSolvedText());
            model.addAttribute("interactableAlreadySolvedText", interactable.getAlreadySolvedText());

            model.addAttribute("roomName", room.getRoomName());

        }
        else {

            logger.info("Interactable " + interactable.getInteractableName() + " has already been unlocked.");

            model.addAttribute("lockstate", "notlocked");

            model.addAttribute("interactableName", interactable.getInteractableName());
            model.addAttribute("interactableDesc", interactable.getInteractableDesc());
            model.addAttribute("interactableSolvedText", interactable.getSolvedText());
            model.addAttribute("interactableAlreadySolvedText", interactable.getAlreadySolvedText());

            model.addAttribute("roomName", room.getRoomName());

        }


        return "game/interactable";
    }

    public String ending(Model model, String interactableName) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);

        logger.info("GameService 'ending' called for user " + name + ", from object " + interactableName);

        if (user == null) {
            logger.error("No user found in database with name: " + name);
            model.addAttribute("user","error");
            return "index";
        }

        GameState gameState = gameStates.get(user.getUsername());
        if (gameStates.get(user.getUsername())==null) {
            //No GameState found for user. Need to restart the game.
            logger.error("No gamestate found for name: " + name + ", restarting and creating new session.");
            return start(model);
        }
        gameState.logState();
        Interactable interactable = interactableRepository.findByInteractableName(interactableName);

        model.addAttribute("endingName", endingRepository.findByName(interactable.getEndName()).getEndingName());
        model.addAttribute("ending", endingRepository.findByName(interactable.getEndName()).getEndingText());
        model.addAttribute("time", DurationFormatUtils.formatDuration(Duration.between(gameState.getStartTime(), LocalDateTime.now()).toMillis(), "**H:mm:ss**", true));

        return "game/ending";





    }

    public String loot(Model model, String lootName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);

        logger.info("GameService 'loot' called for user " + name + ", object " + lootName);

        if (user == null) {
            logger.error("No user found in database with name: " + name);
            model.addAttribute("user","error");
            return "index";
        }

        GameState gameState = gameStates.get(user.getUsername());
        if (gameStates.get(user.getUsername())==null) {
            //No GameState found for user. Need to restart the game.
            logger.error("No gamestate found for name: " + name + ", restarting and creating new session.");
            return start(model);
        }
        gameState.logState();

        Loot loot = gameState.findLoot(lootName);
        Room room = gameState.findRoom(gameState.getCurrentRoom());

        gameState.addLoot(lootName);
        logger.info("Item " + lootName + " from " + room.getRoomName() + " added to inventory.");

        model.addAttribute("lootName", loot.getLootName());
        model.addAttribute("lootDescription", loot.getLootDesc());

        model.addAttribute("roomName", room.getRoomName());

        return "game/loot";

    }

    public String examine(Model model, String lootName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);

        logger.info("GameService 'examine' called for user " + name + ", object " + lootName);

        if (user == null) {
            logger.error("No user found in database with name: " + name);
            model.addAttribute("user","error");
            return "index";
        }

        GameState gameState = gameStates.get(user.getUsername());
        if (gameStates.get(user.getUsername())==null) {
            //No GameState found for user. Need to restart the game.
            logger.error("No gamestate found for name: " + name + ", restarting and creating new session.");
            return start(model);
        }
        gameState.logState();

        Loot loot = gameState.findLoot(lootName);
        Room room = gameState.findRoom(gameState.getCurrentRoom());

        model.addAttribute("lootName", loot.getLootName());
        model.addAttribute("lootDescription", loot.getLootDesc());

        model.addAttribute("roomName", room.getRoomName());

        return "game/examine";

    }

    public String exit(Model model, String exitKey) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        User user = userRepository.findByUsername(name);

        logger.info("GameService 'exit' called for user " + name + ", exit " + exitKey);

        if (user == null) {
            logger.error("No user found in database with name: " + name);
            model.addAttribute("user","error");
            return "index";
        }

        GameState gameState = gameStates.get(user.getUsername());
        if (gameStates.get(user.getUsername())==null) {
            //No GameState found for user. Need to restart the game.
            logger.error("No gamestate found for name: " + name + ", restarting and creating new session.");
            return start(model);
        }
        gameState.logState();

        Interactable interactable = gameState.findInteractable(exitKey);
        Room room = gameState.findRoom(gameState.getCurrentRoom());

        if (interactable.isLocked()) {
            logger.info("Interactable " + interactable.getInteractableName() + " is locked.");
            // need to unlock
            if (gameState.hasLoot(interactable.getKeyName())) {
                logger.info("User has the key, unlocking!");
                interactable.setLocked(false);
                model.addAttribute("lockstate", "unlocking");
            }
            else {
                logger.info("User does not have the key!");
                model.addAttribute("lockstate", "nokey");
            }
            model.addAttribute("interactableName", interactable.getInteractableName());
            model.addAttribute("interactableDesc", interactable.getInteractableDesc());
            model.addAttribute("interactableSolvedText", interactable.getSolvedText());
            model.addAttribute("interactableAlreadySolvedText", interactable.getAlreadySolvedText());

            model.addAttribute("roomName", room.getRoomName());

            logger.info("Presenting user with interactable page for " + exitKey);
            return "game/interactable";

        }
        else {
            logger.info("Exit " + interactable.getInteractableName() + " is not locked. Using it.");

            model.addAttribute("lockstate", "notlocked");

            model.addAttribute("interactableName", interactable.getInteractableName());
            model.addAttribute("interactableDesc", interactable.getInteractableDesc());
            model.addAttribute("interactableSolvedText", interactable.getSolvedText());
            model.addAttribute("interactableAlreadySolvedText", interactable.getAlreadySolvedText());

            model.addAttribute("roomName", room.getRoomName());

        }

        List<ExitMapping> exitMappings = exitMappingRepository.findByRoomname(room.getRoomName());
        List<String> exits = new ArrayList<>();
        List<String> exitDirections = new ArrayList<>();
        if (exitMappings == null || exitMappings.size()==0) {
            logger.error("No exit mappings found for room");
            model.addAttribute("user","error");
            return "index";

        }
        else {
            for (ExitMapping mapping : exitMappings) {
                exits.add(mapping.getExitName());
                exitDirections.add(mapping.getExitDirection());
            }
        }

        String exitDirection = exitDirections.get(exits.indexOf(exitKey));
        String roomName = "";
        List<ExitMapping> allExitMappings = exitMappingRepository.findAll();
        for (ExitMapping mapping : allExitMappings) {
            if (mapping.getExitName().equals(exitKey) && !mapping.getExitDirection().equals(exitDirection)) {
                roomName = mapping.getRoomName();
            }
        }

        if (roomName.equals("")) {
            logger.error("Unable to find matching mapping for this exit.");
            model.addAttribute("user","error");
            return "index";
        }

        gameState.setCurrentRoom(roomName);
        return continueGame(model);
    }

    private void roleCheck(Model model) {
        if (roleCheck.RoleCheck("Admin")) {
            logger.info("User is an ADMIN");
            model.addAttribute("admin", "true");
        }
    }


}
