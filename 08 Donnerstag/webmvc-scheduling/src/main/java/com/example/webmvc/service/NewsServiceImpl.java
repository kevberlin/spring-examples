package com.example.webmvc.service;

import com.example.webmvc.model.NewsItem;
import com.example.webmvc.model.NewsItemContainer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.cache.annotation.CacheResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class NewsServiceImpl implements NewsService {

    private static final String api =
            "https://newsapi.org/v2/top-headlines?" +
                    "country=de&" +
                    "apiKey={apikey}&" +
                    "pageSize={count}&" +
                    "page=0";

    private String apikey;
    private RestTemplate restTemplate;
    public NewsServiceImpl(@Value("${newsapi.key}") String apikey,
                           RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.apikey = apikey;
    }

    @Override
    @PreAuthorize("isFullyAuthenticated()")
    @CacheResult(cacheName = "newscache")
    public List<NewsItem> findNews(int count) {
        log.warn("Called findNews function");
        Map<String, String> param = new HashMap<>();
        param.put("count", String.valueOf(count));
        param.put("apikey", apikey);
        List<NewsItem> tmp;
        try {
            tmp = restTemplate.getForObject(api, NewsItemContainer.class, param)
                    .getArticles();
        } catch (RestClientException e) {
            tmp = null;
        }
        return tmp;
    }
}
