package com.example.restdemo.model;

import io.asyncer.r2dbc.mysql.internal.NotNullByDefault;
import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNullFields;
import reactor.util.annotation.NonNull;

@AllArgsConstructor
@Table("vehicle")
@Data
@ToString
public class Vehicle {
    @Id
//    @NonNull
    @Column("vid")
    private final Long vid;

    @Column("plateNumber")
//    @NonNull
    private final String plateNumber;

    @Column("name")
//    @NonNull
    private final String name;

    @Column("online")
    private Boolean online;

    @Column("engineOn")
    private Boolean engineOn;

    @Column("speed")
    private Float speed;
}
