package fr.elloworld.aguamenti.utils;

import java.util.Random;

public class Maths {

    public static double normalDistribution() {
        Random rdm = new Random();
        double number = rdm.nextGaussian() * 24 + 107.5;
        while (number < 35 || number > 180) {
            number = rdm.nextGaussian() * 24 + 107.5;
        }
        return number;
    }
}