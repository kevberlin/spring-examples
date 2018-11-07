package com.example.webmvc.repositories;

import com.example.webmvc.model.NewsUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsUserRepository extends JpaRepository<NewsUser, Long> {

    NewsUser findByUsername(String username);
}
