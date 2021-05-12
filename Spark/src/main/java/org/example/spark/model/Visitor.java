package org.example.spark.model;

import org.example.spark.model.dev.Beijing;
import org.example.spark.model.dev.Shanghai;
import org.example.spark.model.dev.Shenzhen;

/**
 * @author XiaShuai on 2020/4/28.
 */
public interface Visitor {
    public void visit(Beijing beijing);

    public void visit(Shanghai shanghai);

    public void visit(Shenzhen shenzhen);
}
