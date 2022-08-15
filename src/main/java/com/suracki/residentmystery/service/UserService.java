package com.suracki.residentmystery.service;

import com.suracki.residentmystery.controller.HomeController;
import com.suracki.residentmystery.domain.User;
import com.suracki.residentmystery.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.Random;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);
    private UserRepository userRepository;

    @Autowired
    JavaMailSender emailSender;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;

        logger.info("AdminService created");
        setupAdmin();
    }

    private void setupAdmin() {
        User admin = userRepository.findByUsername("Admin");
        if (admin == null) {
            logger.info("Admin User Not Found");
            admin = new User();
            admin.setRole("Admin");
            admin.setUsername("Admin");
            admin.setEmail("simon.linford@gmail.com");
            admin.setPassword("$2a$12$3I2P2stDCpA0Hv/PUqQcEe3ivmjGu4EOHZmRNOgNz0Hg2xnjL7CtG");
            userRepository.save(admin);
            logger.info("Default Admin User Created");
        }
        else {
            logger.info("Admin User Found");
        }
    }



    public String addUser(Model model, User user) {
        return "login/register";
    }

    public String resetPasswordValidate(User user, Model model) {
        logger.info("Validate called");
        if (user.getUsername() != null) {
            logger.info("Username is not null. Checking for matching user");
            User found = userRepository.findByUsername(user.getUsername());
            if (found == null) {
                logger.info("No user found with this name.");
                model.addAttribute("user","not_found");
                return "index";
            }
            logger.info("User found. Requesting reset");
            return resetPassword(model, user.getUsername());
        }
        logger.info("Errors found, or no user provided. Returning to home.");
        model.addAttribute("user","not_found");
        return "index";
    }

    public String resetPassword(Model model, String userName) {
        try {
            User user = userRepository.findByUsername(userName);

            PasswordGenerator gen = new PasswordGenerator();
            CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
            CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
            lowerCaseRule.setNumberOfCharacters(2);

            CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
            CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
            upperCaseRule.setNumberOfCharacters(2);

            CharacterData digitChars = EnglishCharacterData.Digit;
            CharacterRule digitRule = new CharacterRule(digitChars);
            digitRule.setNumberOfCharacters(2);

            CharacterData specialChars = new CharacterData() {
                public String getErrorCode() {
                    return "";
                }

                public String getCharacters() {
                    return "!@#$%^&*()_+";
                }
            };
            CharacterRule splCharRule = new CharacterRule(specialChars);
            splCharRule.setNumberOfCharacters(2);

            String password = gen.generatePassword(10, splCharRule, lowerCaseRule,
                    upperCaseRule, digitRule);

            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            userRepository.save(user);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@simonlinford.com");
            message.setTo("simon_2k1@hotmail.com");
            message.setSubject("Password Reset");
            message.setText("Your new password for user " + user.getUsername() + " is: " + password);
            emailSender.send(message);

            model.addAttribute("user","reset");

            return "index";
        }
        catch (Exception e) {
            logger.error("Error generating & sending new password: " + e);
            model.addAttribute("user","generic_error");
            return "index";
        }
    }

    public String validate(@Valid User user, BindingResult result, Model model) {
        logger.info("Validate called");
        if (!result.hasErrors()) {
            logger.info("No errors found. Checking for duplicate.");
            User dupe = userRepository.findByUsername(user.getUsername());
            if (dupe != null) {
                //user already exists with this name
                logger.info("User already exists with this name.");

                model.addAttribute("name", user.getUsername());
                return "login/duplicate";
            }
            logger.info("No duplicate users found. Creating user.");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            model.addAttribute("user","created");
            return "index";
        }
        logger.info("Errors found. Returning to form.");
        System.out.println(result.hasErrors());
        return "login/register";
    }

}
