package com.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PokemonResponse {
    private PokemonData data;

    @Getter
    @Setter
    public static class PokemonData {
        private String name;
        private String[] types;
        private int pokedexId;
    }
}
