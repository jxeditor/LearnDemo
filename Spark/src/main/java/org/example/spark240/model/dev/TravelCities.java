package org.example.spark240.model.dev;

import org.example.spark240.model.City;
import org.example.spark240.model.Visitor;

/**
 * @author XiaShuai on 2020/4/28.
 */
public class TravelCities implements City {
    City[] cities;

    public TravelCities() {
        cities = new City[]{new Beijing(), new Shanghai(), new Shenzhen()};
    }

    @Override
    public void accept(Visitor visitor) {
        for (int i = 0; i < cities.length; i++) {
            cities[i].accept(visitor);
        }
    }
}
