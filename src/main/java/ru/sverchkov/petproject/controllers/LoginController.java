package ru.sverchkov.petproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sverchkov.petproject.model.dto.UserDto;
import ru.sverchkov.petproject.service.UserService;

import javax.validation.Valid;

@Controller
public class LoginController {

    private final UserService userSevice;

    @Autowired
    public LoginController(UserService userSevice) {
        this.userSevice = userSevice;
    }


    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (!userDto.getPassword().equals(userDto.getRepeatPassword())) {
            bindingResult.rejectValue("password", "Пароли не совпадают");
            return "register";
        }

        userSevice.createUser(userDto);
        return "redirect:/login";
    }
}
