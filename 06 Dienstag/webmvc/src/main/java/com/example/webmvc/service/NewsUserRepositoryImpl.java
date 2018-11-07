package com.example.webmvc.service;

import com.example.webmvc.model.NewsUser;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class NewsUserRepositoryImpl implements NewsUserRepository {

    private Map<String, NewsUser> map = new HashMap<>();

    public NewsUserRepositoryImpl() {
        map.put("buck",
                new NewsUser("Buck", "Rogers", LocalDate.now(), "buck", "buck"));
    }

    @Override
    public NewsUser findByUsername(String username) {
        return map.get(username);
    }

    @Override
    public NewsUser save(NewsUser user) {
        map.put(user.getUsername(), user);
        return user;
    }
}
