package org.example.spark.model.dev;

import org.example.spark.model.City;
import org.example.spark.model.Visitor;

/**
 * @author XiaShuai on 2020/4/28.
 */
public class Shanghai implements City {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
