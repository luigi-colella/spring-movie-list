package com.example.popcorndatabase.movie;

import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    Iterable<Movie> findAllByOrderByIdDesc();
}
