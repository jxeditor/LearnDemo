package org.example.sink

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

/**
 * @Author xz
 * @Date 2021/11/26 09:39
 * @Description
 * Flink DDL主键设置与否与外部Sink表数据结构的关系
 */
object UpsertStreamTableSinkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = StreamTableEnvironment.create(env)

    // 模拟数据源
    tEnv.executeSql(
      s"""
         |CREATE TABLE test (
         |    name1        STRING,
         |    name2        STRING,
         |    price        BIGINT,
         |    ctime        TIMESTAMP(3)
         |) WITH (
         |  'connector' = 'datagen',
         |  'rows-per-second' = '1'
         |)
         |""".stripMargin)

    // sink1和sink2都是外部表没有设置主键
    // sink3和sink4都是外部表设置了联合主键
    // sink5和sink6都是外部表设置了联合索引
    // sink7是外部表设置了额外字段作为主键,所以需要Flink DLL新增一个对应主键信息
    // sink8是Flink DDL设置联合主键顺序与外部表设置的联合主键,顺序不一致
    // sink9是Flink DDL字段顺序与外部数据的字段顺序不一致

    // Flink DDL不设置主键,外部数据源不设置主键
    tEnv.executeSql(
      s"""
         |
         |CREATE TABLE sink1 (
         |  x STRING,
         |  m STRING,
         |  price BIGINT,
         |  PRIMARY KEY (x,m) NOT ENFORCED
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp',
         |     'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink1'
         |)
         |""".stripMargin)

    // Flink DDL设置主键,外部数据源不设置主键
    tEnv.executeSql(
      s"""
         |CREATE TABLE sink2 (
         |  x STRING,
         |  m STRING,
         |  price BIGINT,
         |  PRIMARY KEY (x,m) NOT ENFORCED
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp?useSSL=false',
         |   'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink1'
         |)
         |""".stripMargin)

    // Flink DDL不设置主键,外部数据源设置主键(x,m)
    tEnv.executeSql(
      s"""
         |CREATE TABLE sink3 (
         |  x STRING,
         |  m STRING,
         |  price BIGINT
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp?useSSL=false',
         |   'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink2'
         |)
         |""".stripMargin)

    // Flink DDL设置主键,外部数据源设置主键(x,m)
    tEnv.executeSql(
      s"""
         |CREATE TABLE sink4 (
         |  x STRING,
         |  m STRING,
         |  price BIGINT,
         |  PRIMARY KEY (x,m) NOT ENFORCED
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp?useSSL=false',
         |   'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink2'
         |)
         |""".stripMargin)

    // Flink DDL不设置主键,外部数据源设置唯一索引(x,m)
    tEnv.executeSql(
      s"""
         |CREATE TABLE sink5 (
         |  x STRING,
         |  m STRING,
         |  price BIGINT
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp?useSSL=false',
         |   'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink3'
         |)
         |""".stripMargin)

    // Flink DDL设置主键,外部数据源设置唯一索引(x,m)
    tEnv.executeSql(
      s"""
         |CREATE TABLE sink6 (
         |  x STRING,
         |  m STRING,
         |  price BIGINT,
         |  PRIMARY KEY (x,m) NOT ENFORCED
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp?useSSL=false',
         |   'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink3'
         |)
         |""".stripMargin)

    // 主键由已有数据生成
    tEnv.executeSql(
      s"""
         |CREATE TABLE sink7 (
         |  id STRING,
         |  x STRING,
         |  m STRING,
         |  price BIGINT,
         |  PRIMARY KEY (id) NOT ENFORCED
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp?useSSL=false',
         |   'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink4'
         |)
         |""".stripMargin)

    // 联合主键Flink DDL与外部数据结构相反,Flink定义(m,x),外部数据是(x,m)
    tEnv.executeSql(
      s"""
         |CREATE TABLE sink8 (
         |  x STRING,
         |  m STRING,
         |  price BIGINT,
         |  PRIMARY KEY (m,x) NOT ENFORCED
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp?useSSL=false',
         |   'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink2'
         |)
         |""".stripMargin)

    // 字段顺序与外部数据不一致
    tEnv.executeSql(
      s"""
         |CREATE TABLE sink9 (
         |  price BIGINT,
         |  x STRING,
         |  m STRING,
         |  PRIMARY KEY (x,m) NOT ENFORCED
         |) WITH (
         |   'connector' = 'jdbc',
         |   'url' = 'jdbc:mysql://localhost:3306/temp?useSSL=false',
         |   'username' = 'root',
         |   'password' = '123456',
         |   'table-name' = 'sink2'
         |)
         |""".stripMargin)

    tEnv.executeSql(
      s"""
         |insert into sink8
         |select substring(name1,1,1) x,substring(name2,1,2) m,abs(price)%10 price
         |from test
         |""".stripMargin).print()

  }
}
