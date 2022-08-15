package com.suracki.residentmystery.controller;

import com.suracki.residentmystery.domain.User;
import com.suracki.residentmystery.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 *
 * LoginController
 *
 * Provides endpoints for user account creation & login
 *
 */
@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    /**
     * Mapping for GET
     *
     * Serves register new user page
     *
     * @param model Model
     * @return register user form
     */
    @GetMapping("/login/register")
    public String register(Model model, User user)
    {
        logger.info("User connected to /login/register endpoint");
        return userService.addUser(model, user);
    }

    /**
     * Mapping for POST
     *
     * Attempts to create a user from form data
     *
     * If unsuccessful due to errors, will return to form
     * If unscucessful due to user already existing, will prompt to reset password
     *
     * @param model Model
     * @param user object to user data
     * @return result page
     */
    @PostMapping("/login/register/validate")
    public String validate(@Valid User user, BindingResult result, Model model)
    {
        logger.info("User connected to /login/register/validate endpoint");
        return userService.validate(user, result, model);
    }

    /**
     * Mapping for GET
     *
     * Serves reset password page
     *
     * @param model Model
     * @return reset password form
     */
    @GetMapping("/login/resetForm")
    public String resetPasswordForm(User user, Model model)
    {
        logger.info("User connected to /login/resetForm endpoint");
        return "login/reset";
    }

    /**
     * Mapping for POST
     *
     * Attempts to reset a user's password via email
     *
     * @param model Model
     * @param user object to user data
     * @return result page
     */
    @PostMapping("/login/reset/validate")
    public String resetPasswordValidate(User user, Model model)
    {
        logger.info("User connected to /login/reset/validate endpoint");
        return userService.resetPasswordValidate(user, model);
    }

    /**
     * Mapping for GET
     *
     * Serves reset password page
     *
     * @param model Model
     * @return reset password form
     */
    @GetMapping("/login/reset")
    public String resetPassword(Model model, @RequestParam(value="user") String user)
    {
        logger.info("User connected to /login/reset endpoint");
        return userService.resetPassword(model, user);
    }

}
