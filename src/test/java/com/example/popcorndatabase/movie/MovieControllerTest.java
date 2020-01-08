package com.example.popcorndatabase.movie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void saveMovieShouldReturnErrorsIfFieldsAreNotValid() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(post("/movie/").param("year", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name(MovieController.CREATE_MOVIE_FORM))
                .andReturn();

        HashMap<String, String> expectedErrorMessages = new HashMap<>();
        expectedErrorMessages.put("title", "must not be empty");
        expectedErrorMessages.put("year", "must be greater than or equal to 1878");

        @SuppressWarnings("unchecked") List<FieldError> errors = (List<FieldError>) Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("errors");
        errors.forEach(error -> {
            String expectedErrorMessage = expectedErrorMessages.get(error.getField());
            assertEquals(expectedErrorMessage, error.getDefaultMessage());
        });
    }

    @Test
    public void shouldSaveMovie() throws Exception {
        Movie movie = new Movie();
        movie.setTitle("Title");
        movie.setYear(2000);
        movie.setGenre("Genre");
        movie.setPlot("Plot");

        MvcResult mvcResult = this.mockMvc
                .perform(post("/movie/")
                        .param("title", movie.getTitle())
                        .param("year", movie.getYear().toString())
                        .param("genre", movie.getGenre())
                        .param("plot", movie.getPlot())
                )
                .andExpect(status().isOk())
                .andExpect(model().attributeDoesNotExist("errors"))
                .andExpect(view().name(MovieController.CREATE_MOVIE_FORM))
                .andReturn();

        Integer movieId = ((Movie) Objects.requireNonNull(mvcResult.getModelAndView()).getModel().get("movie")).getId();
        Optional<Movie> foundMovie = this.movieRepository.findById(movieId);
        if (!foundMovie.isPresent()) throw new Exception("No movie found");
        Movie actualMovie = foundMovie.get();
        assertEquals(movie.getTitle(), actualMovie.getTitle());
        assertEquals(movie.getYear(), actualMovie.getYear());
        assertEquals(movie.getGenre(), actualMovie.getGenre());
        assertEquals(movie.getPlot(), actualMovie.getPlot());
    }
}
