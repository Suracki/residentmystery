package com.suracki.residentmystery.controller;

import com.suracki.residentmystery.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * GameController
 *
 * Provides endpoints for gameplay
 *
 */
@Controller
public class GameController {

    private static final Logger logger = LogManager.getLogger(GameController.class);

    @Autowired
    GameService gameService;

    /**
     * Mapping for GET
     *
     * Serves game landing/start page
     *
     * @param model Model
     * @return room page
     */
    @GetMapping("/game/start")
    public String start(Model model)
    {
        logger.info("User connected to /game/start endpoint");
        return gameService.start(model);
    }

    /**
     * Mapping for GET
     *
     * Handles requests to interact with a game object
     *
     * @param model Model
     * @param interactableName
     * @return interactable page
     */
    @GetMapping("/game/interactWith")
    public String interact(@RequestParam(value="interact") String interactableName, Model model)
    {
        logger.info("User connected to /game/interactWith/ endpoint");
        return gameService.interact(model, interactableName);
    }

    /**
     * Mapping for GET
     *
     * Handles requests to pick up loot in game
     *
     * @param model Model
     * @param lootName
     * @return loot page
     */
    @GetMapping("/game/takeLoot")
    public String loot(@RequestParam(value="loot") String lootName, Model model)
    {
        logger.info("User connected to /game/takeLoot/ endpoint");
        return gameService.loot(model, lootName);
    }

    /**
     * Mapping for GET
     *
     * Handles requests to examine loot in game
     *
     * @param model Model
     * @param lootName
     * @return examine item page
     */
    @GetMapping("/game/examine")
    public String examine(@RequestParam(value="loot") String lootName, Model model)
    {
        logger.info("User connected to /game/examine/ endpoint");
        return gameService.examine(model, lootName);
    }

    /**
     * Mapping for GET
     *
     * Handles requests to use an exit from a room
     *
     * @param model Model
     * @param exitKey
     * @return room page for destination room
     */
    @GetMapping("/game/useExit")
    public String exit(@RequestParam(value="exit") String exitKey, Model model)
    {
        logger.info("User connected to /game/useExit/ endpoint");
        return gameService.exit(model, exitKey);
    }

    /**
     * Mapping for GET
     *
     * Handles requests to restart the game
     *
     * @param model Model
     * @return room page
     */
    @GetMapping("/game/restart")
    public String restart(Model model)
    {
        logger.info("User connected to /game/restart endpoint");
        return gameService.restart(model);
    }

    /**
     * Mapping for GET
     *
     * Handles requests to interact with an NPC
     *
     * @param model Model
     * @param npcName
     * @return npc page
     */
    @GetMapping("/game/speakWith")
    public String speak(@RequestParam(value="npc") String npcName, Model model)
    {
        logger.info("User connected to /game/interactWith/ endpoint");
        return gameService.speak(model, npcName);
    }


}
