package org.example.spark240.model.dev;

import org.example.spark240.model.City;
import org.example.spark240.model.Visitor;

/**
 * @author XiaShuai on 2020/4/28.
 */
public class Beijing implements City {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
