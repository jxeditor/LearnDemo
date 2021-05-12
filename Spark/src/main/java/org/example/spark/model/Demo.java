package org.example.spark.model;

import org.example.spark.model.dev.TravelCities;

/**
 * @author XiaShuai on 2020/4/28.
 */
public class Demo {
    public static void main(String[] args) {
        TravelCities travelCities = new TravelCities();
        travelCities.accept(new SingleVisitor());
    }
}
