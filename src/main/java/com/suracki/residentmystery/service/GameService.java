package com.suracki.residentmystery.service;

import com.suracki.residentmystery.domain.Interactable;
import com.suracki.residentmystery.domain.Loot;
import com.suracki.residentmystery.domain.Room;
import com.suracki.residentmystery.domain.User;
import com.suracki.residentmystery.repository.InteractableRepository;
import com.suracki.residentmystery.repository.LootRepository;
import com.suracki.residentmystery.repository.RoomRepository;
import com.suracki.residentmystery.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {

    UserRepository userRepository;
    RoomRepository roomRepository;
    InteractableRepository interactableRepository;
    LootRepository lootRepository;

    public GameService(UserRepository userRepository, RoomRepository roomRepository,
                       InteractableRepository interactableRepository, LootRepository lootRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.interactableRepository = interactableRepository;
        this.lootRepository = lootRepository;
        List<Room> roomList = roomRepository.findAll();
        if (roomList.size() == 0) {
            //No rooms stored, perform default setup
            setupDemoAdventure();
        }

    }

    private void setupDemoAdventure() {
        Loot redKey = new Loot();
        redKey.setLootName("Red Key");
        redKey.setLootDesc("An old key with a red gem.");
        lootRepository.save(redKey);
        Interactable redDoor = new Interactable();
        redDoor.setInteractableName("Red Door");
        redDoor.setInteractableDesc("A large ornate door, with a red gem in the handle.");
        redDoor.setLocked(true);
        redDoor.setKeyName("Red Key");
        redDoor.setSolvedText("The door is now unlocked and can be used.");
        interactableRepository.save(redDoor);
        Interactable statue = new Interactable();
        statue.setInteractableName("Statue");
        statue.setInteractableDesc("A life size statue of a person, with an outstretched hand. The ring finger is conspicuously bare.");
        statue.setLocked(true);
        statue.setKeyName("Ring");
        statue.setSolvedText("The ring fits the statue perfectly, and once placed there is a satisfying click sound. The main door opens are you are free again... for now.");
        interactableRepository.save(statue);
        Room entranceHall = new Room();
        entranceHall.setRoomName("Entrance Hall");
        entranceHall.setRoomDesc("The room is large in size and contains many different types of architecture and grand artwork, including paintings, vases, and candles lining the walls.");
        entranceHall.setLoots(new String[] {"Red Key"});
        entranceHall.setInteractables(new String[] {"Red Door"});
        entranceHall.setStartRoom(true);
        Map<String, String> exits = new HashMap<>();
        exits.put("Red Door", "West");
        entranceHall.setExits(exits);
        roomRepository.save(entranceHall);

        Loot ring = new Loot();
        ring.setLootName("Ring");
        ring.setLootDesc("A gold ring, with something indecipherable engraved around the band.");
        lootRepository.save(ring);
        Room diningRoom = new Room();
        entranceHall.setRoomName("Dining Room");
        entranceHall.setRoomDesc("The room is long but relatively narrow, with a large table stretching it's full length, with chairs along each side. The walls are lined with candles, and there are more candles along the center of the table.");
        entranceHall.setLoots(new String[] {"Ring"});
        entranceHall.setInteractables(new String[] {});
        entranceHall.setStartRoom(false);
        Map<String, String> diningExits = new HashMap<>();
        exits.put("Red Door", "East");
        entranceHall.setExits(diningExits);
        roomRepository.save(diningRoom);
    }


    public String start(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        User user = userRepository.findByUsername(name);
        if (user == null) {
            //error loading user
            System.out.println("ERROR LOADING USER");
            return "";
        }

        if (user.getCurrentRoom()!=null) {
            //User is already mid-game
            System.out.println("USER IS MID-GAME: " + user.getCurrentRoom());
            return continueGame(model);
        }

        Room startingRoom = roomRepository.findStartingRoom();
        model.addAttribute("roomName", startingRoom.getRoomName());
        model.addAttribute("roomDesc", startingRoom.getRoomDesc());
        model.addAttribute("interactables", startingRoom.getInteractables());
        model.addAttribute("loots", startingRoom.getLoots());
        model.addAttribute("exits", startingRoom.getExits());


        return "game/room";
    }

    public String continueGame(Model model) {

        return"";
    }


}
