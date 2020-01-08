package com.example.popcorndatabase.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/movie")
public class MovieController {

    public static final String LIST_MOVIES = "movie/listMovies";
    public static final String CREATE_MOVIE_FORM = "movie/createMovieForm";

    @Autowired
    MovieRepository movieRepository;

    @GetMapping("")
    public String showMovies (Map<String, Object> model) {
        Iterable<Movie> movies = movieRepository.findAll();
        model.put("movies", movies);
        return LIST_MOVIES;
    }

    @GetMapping("/new")
    public String showMovieForm (Map<String, Object> model) {
        Movie movie = new Movie();
        model.put("movie", movie);
        return CREATE_MOVIE_FORM;
    }

    @PostMapping("")
    public String addMovie (@Valid Movie movie, BindingResult bindingResult, Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            model.put("errors", bindingResult.getFieldErrors());
        } else {
            movieRepository.save(movie);
        }
        return CREATE_MOVIE_FORM;
    }
}
