package ru.maks12i.models;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CarMaker {

    private String carMakerName;

    private List<Car> cars;

    public CarMaker(String makerName, List<Car> cars) {
        carMakerName = makerName;
        this.cars = cars;
    }
}
