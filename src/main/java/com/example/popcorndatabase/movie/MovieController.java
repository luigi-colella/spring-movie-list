package com.example.popcorndatabase.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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
    public static final String SHOW_MOVIE = "movie/showMovie";
    public static final String CREATE_MOVIE_FORM = "movie/createMovieForm";

    @Autowired
    MovieService movieService;

    /**
     * Set empty strings in the command / form objects to null as default.
     */
    @InitBinder
    public void setEmptyStringsToNullInBinding(WebDataBinder binder)
    {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor( true ));
    }

    /**
     * Show the view with the movies list.
     */
    @GetMapping("")
    public String showMovies(Map<String, Object> model) {
        Iterable<Movie> movies = movieService.find();
        model.put("movies", movies);
        return LIST_MOVIES;
    }

    /**
     * Show the view with the movie details.
     */
    @GetMapping("/{id}")
    public String showMovie(@PathVariable Integer id, Map<String, Object> model) {
        Movie movie = this.movieService.findOrThrow404(id);
        model.put("movie", movie);
        return SHOW_MOVIE;
    }

    /**
     * Show the view with the form to add a new movie.
     */
    @GetMapping("/new")
    public String showMovieForm(Map<String, Object> model) {
        Movie movie = new Movie();
        model.put("movie", movie);
        return CREATE_MOVIE_FORM;
    }

    /**
     * Save a movie in the database.
     */
    @PostMapping("")
    public String addMovie(@Valid Movie movie, BindingResult bindingResult, Map<String, Object> model) {
        if (bindingResult.hasErrors()) {
            model.put("errors", bindingResult.getFieldErrors());
            return CREATE_MOVIE_FORM;
        }

        movieService.save(movie);
        return "redirect:/movie";
    }

    /**
     * Save the selected movie in the database.
     */
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.delete(id);
        return "redirect:/movie";
    }
}
