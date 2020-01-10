package com.example.popcorndatabase.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Iterable<Movie> find() {
        return movieRepository.findAllByOrderByIdDesc();
    }

    public Optional<Movie> find(Integer id) {
        return movieRepository.findById(id);
    }

    public Movie findOrThrow404(Integer id) {
        Optional<Movie> movie = find(id);
        if (movie.isPresent()) {
            return movie.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The selected movie was not found.");
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void delete(Integer id) {
        movieRepository.deleteById(id);
    }
}
