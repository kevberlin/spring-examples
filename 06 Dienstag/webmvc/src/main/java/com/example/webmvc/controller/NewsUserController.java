package com.example.webmvc.controller;

import com.example.webmvc.model.NewsUser;
import com.example.webmvc.service.NewsUserRepository;
import com.example.webmvc.service.UnknownNewsUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
@Slf4j
public class NewsUserController {

    private NewsUserRepository newsUserRepository;
    private PasswordEncoder passwordEncoder;

    public NewsUserController(NewsUserRepository newsUserRepository, PasswordEncoder passwordEncoder) {
        this.newsUserRepository = newsUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile/{username}")
    public String showProfile(Model model,
                              @PathVariable("username") String username) throws UnknownNewsUserException {
        if (!model.containsAttribute("newsUser")) {
            NewsUser user = newsUserRepository.findByUsername(username);
            if (user == null) {
                throw new UnknownNewsUserException("User not found");
            }
            model.addAttribute("newsUser", user);
        }
        return "profile";
    }

    @GetMapping("/register")
    public String showRegistration(Model model) {
        model.addAttribute("newsUser", new NewsUser());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@Valid NewsUser user,
                                      BindingResult bindingResult,
                                      Model model,
                                      RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newsUser", user);
            model.addAttribute("errors", bindingResult.getFieldErrors());
            return "register";
        }
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        newsUserRepository.save(user);
        redirectAttributes.addFlashAttribute("newsUser", user);
        return "redirect:/user/profile/" + user.getUsername();
    }

    @ExceptionHandler(UnknownNewsUserException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String userNotFound() {
        return "usernotfound";
    }
}
