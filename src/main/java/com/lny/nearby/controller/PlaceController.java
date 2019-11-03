package com.lny.nearby.controller;

import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResult;
import com.lny.nearby.service.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

//@RestController
//@RequestMapping("/places")
public class PlaceController {

//    @Autowired
//    private PlaceService placeService;
//
//    @GetMapping
//    public Flux<PlacesSearchResult> greeting() {
//        return placeService.findPlace(new LatLng(new Double("-23.5668698"), new Double("-46.6608874")), "starbucks");
//    }
}
