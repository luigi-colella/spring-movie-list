package com.example.popcorndatabase.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

/**
 * Controller used to handle CRUD operations and views related to movies.
 */
@Controller
@RequestMapping("/movie")
public class MovieController {

    /**
     * View names.
     */
    public static final String LIST_MOVIES = "movie/listMovies";
    public static final String CREATE_MOVIE_FORM = "movie/createMovieForm";

    @Autowired
    MovieService movieService;

    /**
     * Show the view with the movies list.
     */
    @GetMapping("")
    public String showMovies (Map<String, Object> model) {
        Iterable<Movie> movies = movieService.find();
        model.put("movies", movies);
        return LIST_MOVIES;
    }

    /**
     * Show the view with the form to add a new movie.
     */
    @GetMapping("/new")
    public String showMovieForm (Map<String, Object> model) {
        Movie movie = new Movie();
        model.put("movie", movie);
        return CREATE_MOVIE_FORM;
    }

    /**
     * Save a movie in the database.
     */
    @PostMapping("")
    public String addMovie (@Valid Movie movie, BindingResult bindingResult, Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            model.put("errors", bindingResult.getFieldErrors());
            return CREATE_MOVIE_FORM;
        }

        movieService.save(movie);
        return "redirect:/movie";
    }
}
