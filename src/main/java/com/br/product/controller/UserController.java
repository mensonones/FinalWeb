package com.br.product.controller;

import com.br.product.dto.UserRegistrationDto;
import com.br.product.model.User;
import com.br.product.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/access-denied")
    public String accessDenied() {
        return "error/access-denied";
    }

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping(value = "/login")
    public String showRegistrationForm(Model model) {
        return "login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result, Model model) {
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "Esse E-mail já está em uso");
        }

        if (result.hasErrors()) {
            return "login";
        }

        userService.save(userDto);

        return "redirect:/login?success";
    }
}
