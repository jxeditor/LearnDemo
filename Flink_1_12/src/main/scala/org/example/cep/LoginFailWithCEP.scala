//package org.example.cep
//
//import java.util
//import java.util.Random
//
//import org.apache.flink.cep.PatternSelectFunction
//import org.apache.flink.cep.scala.{CEP, CEP1}
//import org.apache.flink.cep.scala.pattern.Pattern1
//import org.apache.flink.streaming.api.TimeCharacteristic
//import org.apache.flink.streaming.api.functions.source.SourceFunction
//import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
//import org.apache.flink.streaming.api.windowing.time.Time
//import org.apache.flink.streaming.api.scala._
//
//object LoginFailWithCEP {
//  def main(args: Array[String]): Unit = {
//    val env = StreamExecutionEnvironment.getExecutionEnvironment
//    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
//    env.setParallelism(1)
//
//    //自定义测试数据
//    val loginStream = env.addSource(new CustomGenerator).assignAscendingTimestamps(_.eventTime)
//
//    loginStream.print()
//
//    //定义pattern.对事件流进行模式匹配
//    val loginFailPattern = Pattern1.begin[LoginEvent]("begin")
//      .where(_.eventTpye.equals("fail"))
//      .next("next")
//      .where(_.eventTpye.equals("fail"))
//    //      .within(Time.seconds(2))
//
//    //在输入流的基础上应用pattern,得到匹配的pattern stream
//    val patternStream = CEP.pattern(loginStream.keyBy(_.userId), loginFailPattern)
//
//
//
//    val loginFailDataStream = patternStream.select(new MySelectFunction())
//
//    //将得到的警告信息流输出sink
//    loginFailDataStream.print("warning")
//
//    env.execute("Login Fail Detect with CEP")
//  }
//}
//
//
////登录样例类
//case class LoginEvent(userId: Long, ip: String, eventTpye: String, eventTime: Long)
//
////输出报警信息样例类
//case class Warning(userId: Long, firstFailTime: Long, lastFailTime: Long, warningMSG: String)
//
//class MySelectFunction() extends PatternSelectFunction[LoginEvent, Warning] {
//  override def select(patternEvents: util.Map[String, util.List[LoginEvent]]): Warning = {
//    val firstFailEvent = patternEvents.getOrDefault("begin", null).iterator().next()
//    val secondEvent = patternEvents.getOrDefault("next", null).iterator().next()
//    Warning(firstFailEvent.userId, firstFailEvent.eventTime, secondEvent.eventTime, "login fail warning")
//  }
//}
//
//class CustomGenerator extends SourceFunction[LoginEvent] {
//  private var running = true
//
//  override def run(ctx: SourceFunction.SourceContext[LoginEvent]): Unit = {
//    // 随机数生成器
//    val state = Array("fail","success")
//    while (running) {
//      // 利用ctx上下文将数据返回
//      ctx.collect(LoginEvent(scala.util.Random.nextInt(10), "192.168.0.1", state(scala.util.Random.nextInt(2)), System.currentTimeMillis()))
//      Thread.sleep(500)
//    }
//  }
//
//  override def cancel(): Unit = {
//    running = false
//  }
//}