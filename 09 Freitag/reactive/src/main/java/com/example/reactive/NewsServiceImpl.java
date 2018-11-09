package com.example.reactive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class NewsServiceImpl implements NewsService {

    private static final String API =
            "https://newsapi.org/v2/top-headlines?" +
                    "country=de&" +
                    "apiKey={apikey}&" +
                    "pageSize=5&" +
                    "page=0";

    private WebClient webClient;

    private String key;

    public NewsServiceImpl(@Value("${newsapi.key}") String apikey, WebClient webClient) {
        this.webClient = webClient;
        this.key = apikey;
    }

    @Override
    public Mono<List<NewsItem>> findNews() {
        Mono<NewsItemContainer> container =
            this.webClient.get()
                    .uri(API, key)
                    .retrieve()
                    .bodyToMono(NewsItemContainer.class);
        return container.map(c -> c.getArticles());
    }
}
