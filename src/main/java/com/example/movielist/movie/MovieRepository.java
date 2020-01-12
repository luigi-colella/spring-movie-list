package com.example.movielist.movie;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    Iterable<Movie> findAllByOrderByIdDesc();
}
