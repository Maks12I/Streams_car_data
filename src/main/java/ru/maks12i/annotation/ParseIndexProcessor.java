package ru.maks12i.annotation;

import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ParseIndexProcessor {
    private final String delimiter = ",";
    private final Map<Class, Map<Integer, Method>> map = new HashMap<>();

    @SneakyThrows
    public boolean registerClass(String header, Class<?> clazz) {
        String[] headers = header.split(delimiter);

        for (Field field : clazz.getDeclaredFields()) {
            ParseIndex annotation = field.getAnnotation(ParseIndex.class);
            if (annotation != null) {
                String setterName = "set" +
                        field.getName().substring(0,1).toUpperCase()  +
                        field.getName().substring(1);
                Method setter = clazz.getDeclaredMethod(setterName, field.getType());
                int index = annotation.headerIndex();
                if(index<0) {
                String name = annotation.headerName();
                index = Arrays.asList(headers).indexOf(name);
                }
                Map<Integer, Method> setters = map.getOrDefault(clazz, new HashMap<>());
                if(setters.containsKey(index)) {
                    return false;
                }
                setters.put(index, setter);
                map.put(clazz, setters);
            }
        }
        return true;
    }

    @SneakyThrows
    public <T> T parseObject(String line, Class<T> clazz) {
        Constructor<T> constructor = clazz.getConstructor();
        T instance = constructor.newInstance();
        String[] values = line.split(delimiter);
        Map<Integer, Method> integerMethodMap = map.get(clazz);
        for (Map.Entry<Integer, Method> entry : integerMethodMap.entrySet()) {
            Integer key = entry.getKey();
            entry.getValue().invoke(instance, values[key]);
        }
        return instance;
    }

}
