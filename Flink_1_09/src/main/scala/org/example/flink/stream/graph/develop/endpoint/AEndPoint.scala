package org.example.flink.stream.graph.develop.endpoint

import org.example.flink.stream.graph.template.EndPoint
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.DataStream

class AEndPoint(name: String) extends EndPoint[String] {
  override def process(dataStream: DataStream[String]): Unit = {
    dataStream.map(x => "AAAAAEndPoint ===>" + x).print()
  }
}
