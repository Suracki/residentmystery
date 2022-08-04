package com.suracki.residentmystery.controller;

import com.suracki.residentmystery.domain.User;
import com.suracki.residentmystery.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {

    private static final Logger logger = LogManager.getLogger(GameController.class);

    @Autowired
    GameService gameService;


    @GetMapping("/game/start")
    public String start(Model model)
    {
        logger.info("User connected to /game/start endpoint");
        return gameService.start(model);
    }

    @GetMapping("/game/interactWith")
    public String interact(@RequestParam(value="interact") String interactableName, Model model)
    {
        logger.info("User connected to /game/interactWith/ endpoint");
        return gameService.interact(model, interactableName);
    }

    @GetMapping("/game/takeLoot")
    public String loot(@RequestParam(value="loot") String lootName, Model model)
    {
        logger.info("User connected to /game/takeLoot/ endpoint");
        return gameService.loot(model, lootName);
    }

    @GetMapping("/game/useExit")
    public String exit(@RequestParam(value="exit") String exitKey, Model model)
    {
        logger.info("User connected to /game/useExit/ endpoint");
        return gameService.exit(model, exitKey);
    }

    @GetMapping("/game/restart")
    public String restart(Model model)
    {
        logger.info("User connected to /game/restart endpoint");
        return gameService.restart(model);
    }


}
