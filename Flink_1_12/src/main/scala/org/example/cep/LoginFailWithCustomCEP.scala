package org.example.cep

import java.util
import java.util.Random

import org.apache.flink.cep.{PatternSelectFunction, pattern}
import org.apache.flink.cep.functions.InjectionPatternFunction
import org.apache.flink.cep.scala.{CEP1}
import org.apache.flink.cep.pattern.Pattern
import org.apache.flink.cep.pattern.conditions.{IterativeCondition, RichIterativeCondition}
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala._
import org.apache.flink.streaming.api.scala.DataStream

import scala.io.{BufferedSource, Source}


object LoginFailWithCustomCEP {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    env.setParallelism(1)

    //自定义测试数据
    val loginStream = env.addSource(new CustomGenerator).assignAscendingTimestamps(_.eventTime)

    loginStream.print()


    //在输入流的基础上应用pattern,得到匹配的pattern stream

    val patternStream = CEP1.injectionPattern(loginStream.keyBy(_.userId), new InjectionPatternFunction[LoginEvent] {
      var current: String = null

      /**
       * 初始化外部连接
       */
      override def init(): Unit = {
        val source = Source.fromFile("/Users/xz/Local/Projects/LearnDemo/Flink_1_12/src/main/resources/rulestatus.txt", "UTF-8")
        val lines = source.getLines().toArray
        source.close()
        current = lines(0)
      }

      /**
       * 动态规则注入
       *
       * @return
       */
      override def inject(): pattern.Pattern[LoginEvent, LoginEvent] = {
        println(current)
        if (current == "a") {
          Pattern.begin[LoginEvent]("begin")
            .where(new RichIterativeCondition[LoginEvent]() {
              override def filter(value: LoginEvent, ctx: IterativeCondition.Context[LoginEvent]): Boolean = {
                value.eventTpye.equals("fail")
              }
            })
            .next("next")
            .where(new RichIterativeCondition[LoginEvent]() {
              override def filter(value: LoginEvent, ctx: IterativeCondition.Context[LoginEvent]): Boolean = {
                value.eventTpye.equals("fail")
              }
            })
        } else {
          Pattern.begin[LoginEvent]("begin")
            .where(new RichIterativeCondition[LoginEvent]() {
              override def filter(value: LoginEvent, ctx: IterativeCondition.Context[LoginEvent]): Boolean = {
                value.eventTpye.equals("success")
              }
            })
            .next("next")
            .where(new RichIterativeCondition[LoginEvent]() {
              override def filter(value: LoginEvent, ctx: IterativeCondition.Context[LoginEvent]): Boolean = {
                value.eventTpye.equals("success")
              }
            })
        }
      }

      /**
       * 轮询周期(监听不需要)
       *
       * @return
       */
      override def getPeriod: Long = 5000

      /**
       * 规则是否发生变更
       *
       * @return
       */
      override def isChanged: Boolean = {
        val source = Source.fromFile("/Users/xz/Local/Projects/LearnDemo/Flink_1_12/src/main/resources/rulestatus.txt", "UTF-8")
        val lines = source.getLines().toArray
        source.close()
        val tempStatus = current
        current = lines(0)
        !lines(0).equals(tempStatus)
      }
    })

    val loginFailDataStream = patternStream.select(new MySelectFunction())

    //将得到的警告信息流输出sink
    loginFailDataStream.print("warning")

    env.execute("Login Fail Detect with CEP")
  }
}


//登录样例类
case class LoginEvent(userId: Long, ip: String, eventTpye: String, eventTime: Long)

//输出报警信息样例类
case class Warning(userId: Long, firstFailTime: Long, lastFailTime: Long, warningMSG: String)

class MySelectFunction() extends PatternSelectFunction[LoginEvent, Warning] {
  override def select(patternEvents: util.Map[String, util.List[LoginEvent]]): Warning = {
    val firstFailEvent = patternEvents.getOrDefault("begin", null).iterator().next()
    val secondEvent = patternEvents.getOrDefault("next", null).iterator().next()
    Warning(firstFailEvent.userId, firstFailEvent.eventTime, secondEvent.eventTime, "login fail warning")
  }
}

class CustomGenerator extends SourceFunction[LoginEvent] {
  private var running = true

  override def run(ctx: SourceFunction.SourceContext[LoginEvent]): Unit = {
    // 随机数生成器
    val state = Array("fail", "success")
    while (running) {
      // 利用ctx上下文将数据返回
      ctx.collect(LoginEvent(scala.util.Random.nextInt(10), "192.168.0.1", state(scala.util.Random.nextInt(2)), System.currentTimeMillis()))
      Thread.sleep(500)
    }
  }

  override def cancel(): Unit = {
    running = false
  }
}