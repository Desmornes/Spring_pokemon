package com.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.security.entity.Pokemon;

@Service
public class PokemonScrapingService {
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public PokemonScrapingService(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<Pokemon> getPokemonData(String pokemonName) {
        return webClientBuilder.baseUrl("https://tyradex.vercel.app")
                .build()
                .get()
                .uri("/pokemon/" + pokemonName)
                .retrieve()
                .bodyToMono(Pokemon.class);
    }
}
