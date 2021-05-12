package org.example.spark.model;

import org.example.spark.model.dev.Beijing;
import org.example.spark.model.dev.Shanghai;
import org.example.spark.model.dev.Shenzhen;

/**
 * @author XiaShuai on 2020/4/28.
 */
public class SingleVisitor implements Visitor {
    @Override
    public void visit(Beijing beijing) {
        System.out.println("bj");
    }

    @Override
    public void visit(Shanghai shanghai) {
        System.out.println("sh");
    }

    @Override
    public void visit(Shenzhen shenzhen) {
        System.out.println("sz");
    }
}
