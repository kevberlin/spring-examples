package com.example.webmvc.controller;

import com.example.webmvc.model.NewsItem;
import com.example.webmvc.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    private NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String showRecentNews(Model model,
                                 @RequestParam(name = "count", defaultValue = "5") int count) {
        List<NewsItem> news = newsService.findNews(count);
        model.addAttribute("news", news);
        return "news";
    }
}