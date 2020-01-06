package com.example.popcorndatabase.movie;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movie")
public class MovieController {

    @GetMapping("/new")
    public String createMovieForm() {
        return "movie/createMovieForm";
    }
}
