package org.example.flink.stream.graph.develop.execpoint

import org.example.flink.stream.graph.template.ExecPoint
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.DataStream
import org.json.JSONObject

class BExecPoint(name: String) extends ExecPoint[JSONObject, String]{

  override def process(dataStream: DataStream[JSONObject]): DataStream[String] = {
    dataStream.map(x => x.get("value").toString)
  }
}
