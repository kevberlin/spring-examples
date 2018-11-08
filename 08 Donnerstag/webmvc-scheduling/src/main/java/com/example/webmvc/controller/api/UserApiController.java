package com.example.webmvc.controller.api;

import com.example.webmvc.model.NewsUser;
import com.example.webmvc.repositories.NewsUserRepository;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;

import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.fromMethodCall;
import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@RestController
@RequestMapping("/api")
public class UserApiController {

    private NewsUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserApiController(NewsUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public Resources<NewsUserResource> findAll() {
        List<NewsUser> users = userRepository.findAll();
        List<NewsUserResource> resources = new
                NewsUserResourceAssembler().toResources(users);
        return new Resources<>(resources);
    }

    @GetMapping("/{username}")
    public ResponseEntity<NewsUserResource> findByUsername(@PathVariable("username") String username) {
        ResponseEntity<NewsUserResource> response;
        try {
            response = ResponseEntity.ok(
                    new NewsUserResourceAssembler().toResource(userRepository.findByUsername(username)));
        } catch (Exception e) {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody NewsUser user) {
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        NewsUser savedUser = userRepository.save(user);
        UriComponents uri =
                fromMethodCall(on(UserApiController.class)
                        .findByUsername(savedUser.getUsername())).build();
        return ResponseEntity.created(uri.toUri()).build();
    }
}
