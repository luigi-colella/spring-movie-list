package com.example.popcorndatabase.movie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Base64;
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

    public Movie save(Movie movie, MultipartFile posterImage) throws IOException {
        if (!posterImage.isEmpty()) {
            // This app is only for demonstration purposes then images are stored as string values in database
            // but in production it would be better to store them on a filesystem or on an external service
            String base64Image = Base64.getEncoder().encodeToString(posterImage.getBytes());
            movie.setPosterImage(base64Image);
        }
        return movieRepository.save(movie);
    }

    public void delete(Integer id) {
        movieRepository.deleteById(id);
    }
}
