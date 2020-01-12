package com.example.movielist.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/movie")
public class MovieController {

    /**
     * View names.
     */
    public static final String VIEW_LIST_MOVIES = "movie/listMovies";
    public static final String VIEW_SHOW_MOVIE = "movie/showMovie";
    public static final String VIEW_ADD_MOVIE = "movie/saveMovie";
    public static final String REDIRECT_TO_VIEW_LIST_MOVIES = "redirect:/movie";

    @Autowired
    MovieService movieService;

    /**
     * Set empty strings in the form objects to null as default.
     */
    @InitBinder
    public void setEmptyStringsToNullInBinding(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    /**
     * Show the view with the movies list.
     */
    @GetMapping("")
    public String showMoviesView(Map<String, Object> model) {
        Iterable<Movie> movies = movieService.find();
        model.put("movies", movies);
        return VIEW_LIST_MOVIES;
    }

    /**
     * Show the view with the movie details.
     */
    @GetMapping("/{id}")
    public String showMovieView(@PathVariable Integer id, Map<String, Object> model) {
        Movie movie = movieService.findOrThrow404(id);
        model.put("movie", movie);
        return VIEW_SHOW_MOVIE;
    }

    /**
     * Show the view to add a new movie.
     */
    @GetMapping("/new")
    public String addMovieView(Map<String, Object> model) {
        Movie movie = new Movie();
        model.put("movie", movie);
        return VIEW_ADD_MOVIE;
    }

    /**
     * Show the view to update an existing movie.
     */
    @GetMapping("/update/{id}")
    public String updateMovieView(@PathVariable Integer id, Map<String, Object> model) {
        Movie movie = movieService.findOrThrow404(id);
        model.put("movie", movie);
        return VIEW_ADD_MOVIE;
    }

    /**
     * Save or update a movie in the database.
     */
    @PostMapping("")
    public String saveMovie(
            @Valid Movie movie,
            BindingResult bindingResult,
            @RequestParam("image") Optional<MultipartFile> image,
            Map<String, Object> model,
            HttpServletResponse response
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpStatus.PRECONDITION_FAILED.value());
            model.put("errors", bindingResult);
            return VIEW_ADD_MOVIE;
        }
        Movie savedMovie = movieService.save(movie, image);
        model.put("movie", savedMovie);
        return VIEW_SHOW_MOVIE;
    }

    /**
     * Delete a movie in the database.
     */
    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Integer id) {
        movieService.delete(id);
        return REDIRECT_TO_VIEW_LIST_MOVIES;
    }
}
