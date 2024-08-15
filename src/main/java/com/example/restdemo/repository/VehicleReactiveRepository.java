package com.example.restdemo.repository;

import com.example.restdemo.model.Vehicle;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VehicleReactiveRepository extends ReactiveCrudRepository<Vehicle, Long> {
    Mono<Vehicle> findByName(String name);

    Mono<Boolean> existsByName(String name);
    Mono<Boolean> existsByPlateNumber(String plateNumber);

    Flux<Vehicle> findAllBySpeedGreaterThan(float speed);

    Mono<Vehicle> save(Vehicle vehicle);
}
