package com.evenement.evenement.controller;

import com.evenement.evenement.service.OpenCageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private OpenCageService openCageService;

    @GetMapping
    public String getLocation(@RequestParam double latitude, @RequestParam double longitude) {
        return openCageService.getLocationName(latitude, longitude);
    }
}
