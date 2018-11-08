package com.example.webmvc;

import com.example.webmvc.repositories.NewsUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogRegistrationJob {

    private NewsUserRepository newsUserRepository;

    public LogRegistrationJob(NewsUserRepository newsUserRepository) {
        this.newsUserRepository = newsUserRepository;
    }

    @Scheduled(fixedDelay = 5000)
    public void logUserCount() {
        log.info("Reg: " + newsUserRepository.count());
    }
}
