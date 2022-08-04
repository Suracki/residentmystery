package com.suracki.residentmystery.controller;

import com.suracki.residentmystery.domain.User;
import com.suracki.residentmystery.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {

    private static final Logger logger = LogManager.getLogger(GameController.class);

    @Autowired
    GameService gameService;


    @GetMapping("/game/start")
    public String start(Model model)
    {
        logger.info("User connected to /login/register endpoint");
        return gameService.start(model);
    }

}
