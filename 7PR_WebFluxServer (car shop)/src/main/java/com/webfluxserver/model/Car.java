package com.webfluxserver.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("cars")
public class Car {
    @Id
    private Long id;
    @Column("name")
    private String name;
    @Column("brand")
    private String brand;
    @Column("color")
    private String color;
    @Column("mileage")
    private Integer mileage;
    @Column("year_release")
    private Integer year;
    @Column("price")
    private Integer price;
}