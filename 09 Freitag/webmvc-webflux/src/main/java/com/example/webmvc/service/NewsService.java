package com.example.webmvc.service;

import com.example.webmvc.model.NewsItem;

import java.util.List;

public interface NewsService {

    List<NewsItem> findNews(int count);
}
