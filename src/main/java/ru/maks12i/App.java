package ru.maks12i;

import lombok.SneakyThrows;
import ru.maks12i.models.Car;
import ru.maks12i.models.CarMaker;
import ru.maks12i.annotation.ParseIndexProcessor;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class App {
    @SneakyThrows
    public static void main(String[] args) {
        FileIO fileIO = new FileIO();
        ParseIndexProcessor processor = new ParseIndexProcessor();
        Path path = Paths.get("").toAbsolutePath()
                .resolve("src")
                .resolve("main")
                .resolve("resources")
                .resolve("CAR_DATA.csv");

        //Получение и удаление данных
        List<String> rf = fileIO.readFile();
        String header = rf.get(0);
        rf.remove(0);
        Files.deleteIfExists(path);
        Files.createFile(path);
        processor.registerClass(header, Car.class);

        //Объекты
        List<Car> cars = new ArrayList<Car>();
        for (String el: rf) cars.add(processor.parseObject(el, Car.class));
        fileIO.writeFile(cars, "Car_objects.csv");

        //Сортировка по цвету
        Map<String, List<Car>> colorSort = cars.stream().collect(Collectors.groupingBy(Car::getCarColor));
        fileIO.writeFile(colorSort.entrySet(), "Color_sort.csv");

        //Car в map
        Map<String, List<Car>> carMakers = cars
                .stream()
                .collect(Collectors.groupingBy(Car::getCarMaker));
        //Map в list
        List<CarMaker> carMakerList = new ArrayList<CarMaker>();
        for (Map.Entry<String, List<Car>> elem : carMakers.entrySet()) {
            carMakerList.add(new CarMaker(elem.getKey(), elem.getValue()));
        }
        //Вывод
        fileIO.writeFile(carMakerList, "Makers_data.csv");
        String[] carMakerComp = carMakers.keySet().toArray(new String[0]);
        System.out.println(Arrays.stream(carMakerComp).collect(Collectors.joining(",")));
        //Сортировка производителей
        Map<String, List<Car>> sortMakers =
                carMakers.entrySet().stream().filter(x -> x.getValue().size() > 2)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<String> keys = sortMakers.keySet().stream().sorted().collect(Collectors.toList());
        fileIO.writeFile(keys, "Sorted_Makers.csv");
    }
}
