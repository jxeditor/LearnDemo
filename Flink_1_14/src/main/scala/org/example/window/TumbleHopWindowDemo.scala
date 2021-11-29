package org.example.window

import org.apache.flink.api.common.ExecutionConfig
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment

/**
 * @Author xz
 * @Date 2021/11/29 16:11
 * @Description TODO
 */
object TumbleHopWindowDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val tEnv = StreamTableEnvironment.create(env)

    tEnv.executeSql(
      s"""
         |CREATE TABLE test (
         |    name        STRING,
         |    ctime       TIMESTAMP_LTZ(3) ,
         |    WATERMARK FOR ctime AS ctime - INTERVAL '5' SECOND
         |) WITH (
         |  'connector' = 'datagen',
         |  'rows-per-second' = '1'
         |)
         |""".stripMargin)

    val tumble = tEnv.sqlQuery(
      s"""
         | SELECT now(),name,COUNT(1) as pv,
         | TUMBLE_START(ctime, INTERVAL '5' second) as t_start,
         | TUMBLE_END(ctime, INTERVAL '5' second) as t_end
         | FROM (
         |  SELECT substr(name,1,1) name,ctime
         |  FROM test
         | ) tmp
         | GROUP BY name,TUMBLE(ctime, INTERVAL '5' second)
         |""".stripMargin).execute()

    val hop = tEnv.sqlQuery(
      s"""
         | SELECT now(),name,COUNT(1) as pv,
         | HOP_START(ctime, INTERVAL '5' second, INTERVAL '10' second) as t_start,
         | HOP_END(ctime, INTERVAL '5' second, INTERVAL '10' second) as t_end
         | FROM (
         |  SELECT substr(name,1,1) name,ctime
         |  FROM test
         | ) tmp
         | GROUP BY name,HOP(ctime, INTERVAL '5' second, INTERVAL '10' second)
         |""".stripMargin).execute()
    hop.print()
  }
}
