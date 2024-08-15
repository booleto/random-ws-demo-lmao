package com.example.restdemo.service;

import com.example.restdemo.exception.VehicleDupeException;
import com.example.restdemo.exception.VehicleUnregisteredException;
import com.example.restdemo.model.Vehicle;
import com.example.restdemo.repository.VehicleReactiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class VehicleService {
    private final VehicleReactiveRepository vehicleReactiveRepository;

    public Mono<Vehicle> registerVehicle(Vehicle vehicle) {
        Mono<Boolean> nameExists = this.existsByName(vehicle.getName());
        Mono<Boolean> plateExists = this.existsByPlateNumber(vehicle.getPlateNumber());


        Mono.zip(nameExists, plateExists, (nameDupe, plateDupe) -> {
                    if (Boolean.TRUE.equals(nameDupe))
                        throw new VehicleDupeException("Duplicate vehicle name", 400);
                    if (Boolean.TRUE.equals(plateDupe))
                        throw new VehicleDupeException("Duplicate plate number", 400);

                    return true;
            }
        ).block();

        return vehicleReactiveRepository.save(vehicle)
                        .doOnError(throwable -> {
                            throw new VehicleDupeException("Unable to register vehicle", 400);
                        });
    }

    public Mono<Vehicle> findByName(String name) {
        return vehicleReactiveRepository.findByName(name)
                .doOnError(throwable -> {
                    throw new VehicleUnregisteredException("Unregistered vehicle", 404);
                });
    }

    public Mono<Boolean> existsByName(String name) {
        return vehicleReactiveRepository.existsByName(name);
    }

    public Mono<Boolean> existsByPlateNumber(String plateNumber) {
        return vehicleReactiveRepository.existsByPlateNumber(plateNumber);
    }

    public Flux<Vehicle> findAllBySpeedGreaterThan(float speed) {
        return vehicleReactiveRepository.findAllBySpeedGreaterThan(speed);
    }

    public Mono<Vehicle> findByVid(Long vid) {
        return vehicleReactiveRepository.findById(vid);
    }
}
