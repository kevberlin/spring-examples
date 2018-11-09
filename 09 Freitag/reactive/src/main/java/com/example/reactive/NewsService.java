package com.example.reactive;

import reactor.core.publisher.Mono;

import java.util.List;

public interface NewsService {

    Mono<List<NewsItem>> findNews();
}
