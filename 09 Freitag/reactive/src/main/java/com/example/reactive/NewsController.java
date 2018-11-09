package com.example.reactive;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public String showNews(Model model) {
        model.addAttribute("news", newsService.findNews());
        return "news";
    }
}
