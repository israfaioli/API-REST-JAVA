package br.com.utils;

import com.github.javafaker.Faker;

public class Helpers {

    public static String getRandomTitle() {
        return new Faker().letterify("apiRest" + "??????");
    }

    public static Integer getRandomNumber() {
        return new Faker().number().numberBetween(1, 1000);
    }
}
