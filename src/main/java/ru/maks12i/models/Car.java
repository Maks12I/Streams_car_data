package ru.maks12i.models;

import lombok.Data;
import lombok.ToString;
import ru.maks12i.annotation.ParseIndex;

@Data
@ToString
public class Car {

    @ParseIndex(headerIndex = 0)
    private String carModel;

    @ParseIndex(headerIndex = 2)
    private String carModelYear;

    @ParseIndex(headerIndex = 3)
    private String carColor;

    @ParseIndex(headerIndex = 1)
    private String carMaker;

}
