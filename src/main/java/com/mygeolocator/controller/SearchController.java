package com.mygeolocator.controller;

import com.mygeolocator.utils.ConnectionToSearchService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@RestController
@RequestMapping("v1/search")
public class SearchController {

    @GetMapping()
    public String getSearchResponse(@RequestParam String searchName) throws IOException, InterruptedException {
        return ConnectionToSearchService.requestApi(searchName);
    }
}
