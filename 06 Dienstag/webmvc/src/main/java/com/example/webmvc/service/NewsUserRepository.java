package com.example.webmvc.service;

import com.example.webmvc.model.NewsUser;

public interface NewsUserRepository {
    NewsUser findByUsername(String username);
    NewsUser save(NewsUser user);
}
