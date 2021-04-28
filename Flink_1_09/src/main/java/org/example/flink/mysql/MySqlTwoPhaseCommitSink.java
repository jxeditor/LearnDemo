package org.example.flink.mysql;

import org.apache.flink.api.common.ExecutionConfig;
import org.apache.flink.api.common.typeutils.base.VoidSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer;
import org.apache.flink.streaming.api.functions.sink.TwoPhaseCommitSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

/**
 * @author XiaShuai on 2020/4/16.
 */
public class MySqlTwoPhaseCommitSink extends TwoPhaseCommitSinkFunction<Tuple2<String,Integer>, Connection,Void> {
    private static final Logger log = LoggerFactory.getLogger(MySqlTwoPhaseCommitSink.class);

    public MySqlTwoPhaseCommitSink(){
        super(new KryoSerializer<>(Connection.class,new ExecutionConfig()), VoidSerializer.INSTANCE);
    }

    /**
     * 执行数据库入库操作  task初始化的时候调用
     * @param connection
     * @param tp
     * @param context
     * @throws Exception
     */
    @Override
    protected void invoke(Connection connection, Tuple2<String,Integer> tp, Context context) throws Exception {
        log.info("start invoke...");
        String value = tp.f0;
        Integer total = tp.f1;
        String sql = "insert into `t_test` (`value`,`total`,`insert_time`) values (?,?,?)";
        log.info("====执行SQL:{}===",sql);
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, value);
        ps.setInt(2, total);
        ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        log.info("要插入的数据:{}----{}",value,total);
        if (ps != null) {
            String sqlStr = ps.toString().substring(ps.toString().indexOf(":")+2);
            log.error("执行的SQL语句:{}",sqlStr);
        }
        //执行insert语句
        ps.execute();
    }

    /**
     * 获取连接，开启手动提交事物（getConnection方法中）
     * @return
     * @throws Exception
     */
    @Override
    protected Connection beginTransaction() throws Exception {
        log.info("start beginTransaction.......");
        String url = "jdbc:mysql://192.168.86.245:3306/rh_datatool?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false&autoReconnect=true";
        Connection connection = DBConnectUtil.getConnection(url, "root", "123456");
        return connection;
    }

    /**
     *预提交，这里预提交的逻辑在invoke方法中
     * @param connection
     * @throws Exception
     */
    @Override
    protected void preCommit(Connection connection) throws Exception {
        log.info("start preCommit...");
    }

    /**
     * 如果invoke方法执行正常，则提交事务
     * @param connection
     */
    @Override
    protected void commit(Connection connection) {
        log.info("start commit...");
        DBConnectUtil.commit(connection);
    }

    /**
     * 如果invoke执行异常则回滚事物，下一次的checkpoint操作也不会执行
     * @param connection
     */
    @Override
    protected void abort(Connection connection) {
        log.info("start abort rollback...");
        DBConnectUtil.rollback(connection);
    }
}
