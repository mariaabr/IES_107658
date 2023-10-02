package com.ies.servingwebcontent;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MoviequotesController {
    
    // static movies and quotes
    Movie[] movies = {
        new Movie(1, "Grinch (The Grinch), 2018", "Oh, filhoses!"),
        new Movie(1, "Grinch (The Grinch), 2018", "Sorry, I can't hear you. I don't speak ridiculous."),
        new Movie(2, "A través del mar, 2023", "Los novioes son pasajeros, los amigos son para siempre."),
        new Movie(3, "Tiny Pretty Things, 2020", "The power to fly, that's what you dream of."),
        new Movie(4, "Emily in Paris, 2020", "I like Paris, but I'm not sure Paris likes me."),
        new Movie(5, "Finding Nemo, 2003", "Fish are friends, not food."),
        new Movie(6, "The Hitman's Bodyguard, 2017", "It's not the size that matters... it's how you use it."),
        new Movie(7, "Deadpool, 2016", "I know right? You're probably thinking, \"Whose balls did I have to fondle to get my very own movie\"? I can't tell you his name, but it rhymes with Polverine."),
        new Movie(8, "Anne With An E, 2017", "Different isn't bad, it's just not the same."),
        new Movie(9, "Blue Beetle, 2023", "It went up his ass!"),
    };

    @GetMapping("/quotes")
    public Movie getQuotes(){
        return movies[new Random().nextInt(movies.length-1)+1]; // retorna uma quote random da lista de filmes e quotes
    }

    @GetMapping("/movies")
    public Movie getMovie(){
        return movies;
    }

    @GetMapping("/quotebymovie")
    public Movie getQuoteByMovie(@RequestParam(value = "show") String show){
        Integer id = Integer.parseInt(show);
        return movies[id-1];
    }
}
