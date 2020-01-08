package com.example.popcorndatabase.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Iterable<Movie> find () {
        return movieRepository.findAll();
    }

    public Movie save (Movie movie) {
        return movieRepository.save(movie);
    }
}
