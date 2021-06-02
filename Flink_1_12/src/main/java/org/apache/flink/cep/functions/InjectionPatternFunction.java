package org.apache.flink.cep.functions;


import org.apache.flink.api.common.functions.Function;
import org.apache.flink.cep.pattern.Pattern;


import java.io.Serializable;


/**
 * @param <T>
 */
public interface InjectionPatternFunction<T> extends Function, Serializable {
    /**
     * 初始化外部连接
     */
    public void init() throws Exception;


    /**
     * 动态规则注入
     *
     * @return
     */
    public Pattern<T, T> inject() throws Exception;


    /**
     * 轮询周期(监听不需要)
     *
     * @return
     */
    public long getPeriod() throws Exception;


    /**
     * 规则是否发生变更
     *
     * @return
     */
    public boolean isChanged() throws Exception;
}