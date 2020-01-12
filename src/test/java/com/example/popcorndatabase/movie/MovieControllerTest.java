package com.example.popcorndatabase.movie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Iterator;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieService movieService;

    @Test
    public void bindingShouldSetEmptyStringsToNull() throws Exception {
        mockMvc.perform(
                post("/movie")
                        .param("title", "abc")
                        .param("plot", "   ")
        );

        movieService.find().forEach((Movie movie) -> assertNull(movie.getPlot()));
    }

    @Test
    public void shouldShowMovies() throws Exception {
        for (int i = 0; i < 3; i++) {
            Movie movie = new Movie();
            movie.setTitle("abc");
            movieService.save(movie);
        }

        MvcResult mvcResult = mockMvc
                .perform(get("/movie"))
                .andExpect(status().isOk())
                .andExpect(view().name(MovieController.VIEW_LIST_MOVIES))
                .andReturn();

        Iterable<Movie> movies = (Iterable<Movie>) mvcResult.getModelAndView().getModel().get("movies");
        // Check that movies are returned by id in descending order
        Iterator<Movie> iterator = movies.iterator();
        Movie firstMovie = iterator.next();
        Movie secondMovie = iterator.next();
        Movie thirdMovie = iterator.next();
        assertTrue(firstMovie.getId() > secondMovie.getId());
        assertTrue(secondMovie.getId() > thirdMovie.getId());
    }

    @Test
    public void shouldShowMovieDetails() throws Exception {
        Movie movieToSave = new Movie();
        movieToSave.setTitle("abc");
        Movie movie = movieService.save(movieToSave);

        mockMvc
                .perform(get("/movie/{id}", movie.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("movie", hasProperty("title", equalTo(movie.getTitle()))))
                .andExpect(view().name(MovieController.VIEW_SHOW_MOVIE));
    }

    @Test
    public void shouldShowMovieForm() throws Exception {
        mockMvc
                .perform(get("/movie/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(MovieController.VIEW_ADD_MOVIE))
                .andExpect(model().attribute("movie", instanceOf(Movie.class)));
    }

    @Test
    public void saveMovieShouldReturnErrorsIfFieldsAreNotValid() throws Exception {
        mockMvc
                .perform(post("/movie").param("year", "0"))
                .andExpect(status().isPreconditionFailed())
                .andExpect(model().attributeHasFieldErrorCode("movie", "title", "NotEmpty"))
                .andExpect(model().attributeHasFieldErrorCode("movie", "year", "Min"))
                .andExpect(view().name(MovieController.VIEW_ADD_MOVIE));
    }

    @Test
    public void shouldSaveMovie() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Title");
        movie.setYear(2000);
        movie.setGenre("Genre");
        movie.setPlot("Plot");

        mockMvc
                .perform(post("/movie")
                        .param("title", movie.getTitle())
                        .param("year", movie.getYear().toString())
                        .param("genre", movie.getGenre())
                        .param("plot", movie.getPlot())
                )
                .andExpect(status().isOk())
                .andExpect(view().name(MovieController.VIEW_SHOW_MOVIE));
    }

    @Test
    public void shouldUpdateMovie() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("OLD TITLE");
        Movie movieToUpdate = movieService.save(movie);

        mockMvc
                .perform(post("/movie")
                        .param("id", movie.getId().toString())
                        .param("title", "NEW TITLE")
                )
                .andExpect(status().isOk())
                .andExpect(view().name(MovieController.VIEW_SHOW_MOVIE));

        Movie updatedMovie = movieService.findOrThrow404(movieToUpdate.getId());
        assertEquals("NEW TITLE", updatedMovie.getTitle());
    }

    @Test
    public void shouldDeleteMovie() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("abc");
        Movie movieToDelete = movieService.save(movie);

        mockMvc
                .perform(delete("/movie/{id}", movieToDelete.getId().toString()))
                .andExpect(status().isFound())
                .andExpect(view().name(MovieController.REDIRECT_TO_VIEW_LIST_MOVIES));

        Optional<Movie> deletedMovie = movieService.find(movieToDelete.getId());
        assertFalse(deletedMovie.isPresent());
    }
}
