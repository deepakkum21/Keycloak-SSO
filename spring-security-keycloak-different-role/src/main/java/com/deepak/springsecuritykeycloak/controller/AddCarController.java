package com.deepak.springsecuritykeycloak.controller;

import java.util.ArrayList;
import java.util.List;

import com.deepak.springsecuritykeycloak.model.Car;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/car")
public class AddCarController {

    private List<Car> carList = new ArrayList<>();

    @GetMapping("/all")
    public List<Car> getAllCars() {
        System.out.println("------------------------Carsssssssssssssss--------------");
        Car car1 = new Car("BC101", 2016, 22, "Petrol");
        carList.add(car1);
        Car car2 = new Car("TW102", 2018, 28, "Petrol");
        carList.add(car2);
        return carList;
    }

    @GetMapping("/getOne")
    public Car getOneCar() {
        System.out.println("------------------------Carsssssssssssssss--------------");
        Car car1 = new Car("BC101", 2016, 22, "Petrol");
        return car1;
    }

    @GetMapping(value = "/hello")
    public String index()
    {
        System.out.println("00000000000000000000000000000000000000000000000");
        return "hello";
    }
}