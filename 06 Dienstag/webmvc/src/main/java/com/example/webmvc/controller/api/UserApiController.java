package com.example.webmvc.controller.api;

import com.example.webmvc.model.NewsUser;
import com.example.webmvc.model.PartialUser;
import com.example.webmvc.repositories.NewsUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<PartialUser> findAll() {
        return userRepository.findAll()
                .stream()
                .map(PartialUser::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{username}")
    public ResponseEntity<PartialUser> findByUsername(@PathVariable("username") String username) {
        ResponseEntity<PartialUser> response;
        try {
            response = ResponseEntity.ok(
                    new PartialUser(userRepository.findByUsername(username)));
        } catch (Exception e) {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<NewsUser> save(@RequestBody NewsUser user) {
        String pass = passwordEncoder.encode(user.getPassword());
        user.setPassword(pass);
        user = userRepository.save(user);
        UriComponents uri =
                fromMethodCall(on(UserApiController.class)
                        .findByUsername(user.getUsername())).build();
        return ResponseEntity.created(uri.toUri()).build();
    }
}
