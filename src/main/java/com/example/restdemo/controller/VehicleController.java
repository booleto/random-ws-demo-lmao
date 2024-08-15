package com.example.restdemo.controller;

import com.example.restdemo.model.Vehicle;
import com.example.restdemo.service.VehicleService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/v0/vehicle")
public class VehicleController {
    private final VehicleService vehicleService;
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleController.class);

    @GetMapping("/reactive/findname")
    public Mono<Vehicle> findVehicleReact(@RequestParam String vehicleName) {
        LOGGER.info("Searching for vehicle named ".concat(vehicleName));
        return vehicleService.findByName(vehicleName);
    }

    @GetMapping("/reactive/speed")
    public Flux<Vehicle> findSpeedHigherThan(@RequestParam float speed) {
         LOGGER.info("Searching for vehicles with speed higher than " + speed);
         return vehicleService.findAllBySpeedGreaterThan(speed);
    }

    @PostMapping("/register")
    public Mono<Vehicle> registerVehicle(@ModelAttribute Vehicle vehicle) {
        LOGGER.info("Registering vehicle: ".concat(vehicle.toString()));
        return vehicleService.registerVehicle(vehicle);
    }
}
