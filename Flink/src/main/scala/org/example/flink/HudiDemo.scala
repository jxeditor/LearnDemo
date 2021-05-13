package org.example.flink

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.EnvironmentSettings
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

object HudiDemo {
  def main(args: Array[String]): Unit = {
    System.setProperty("fs.defaultFS","hdfs://mac:9000/")
    val bsEnv = StreamExecutionEnvironment.getExecutionEnvironment
    val bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build
    bsEnv.enableCheckpointing(10000)
    bsEnv.setParallelism(1)
    val tableEnv = StreamTableEnvironment.create(bsEnv, bsSettings)
    val hudiSource =
      s"""
         |CREATE TABLE t1(
         |  uuid VARCHAR(20), -- 要么给定uuid,要么PRIMARY KEY(field) NOT ENFORCED指定主键,否则会报错
         |  name VARCHAR(10),
         |  age INT,
         |  ts TIMESTAMP(3), -- ts是必须字段,在前面有介绍过,用来觉得数据的新旧的
         |  `partition` VARCHAR(20)
         |)
         |PARTITIONED BY (`partition`)
         |WITH (
         |  'connector' = 'hudi',
         |  'path' = 'hdfs://mac:9000/t1',
         |  'write.tasks' = '1', -- default is 4 ,required more resource
         |  'compaction.tasks' = '1', -- default is 10 ,required more resource
         |  'table.type' = 'MERGE_ON_READ', -- this creates a MERGE_ON_READ table, by default is COPY_ON_WRITE
         |  'read.streaming.enabled'= 'true',
         |  'read.streaming.check-interval'= '4',
         |  'write.log.max.size' = '10'
         |)
         |""".stripMargin
    tableEnv.executeSql(hudiSource)

    val printSink =
      s"""
         |CREATE TABLE print_table (
         |  f0 INT
         |) WITH (
         |  'connector' = 'print'
         |)
         |""".stripMargin
    tableEnv.executeSql(printSink)

    tableEnv.executeSql(
      s"""
         |insert into print_table
         |select cast(count(1) as int) f0 from t1 where `partition` = 'par1'
         |""".stripMargin)

  }
}
