package org.example.demand

object GraphDemo {
  // 边
  var edgeSet: Set[String] = Set[String]()
  // 节点对应出度
  var nodeMap: scala.collection.mutable.Map[String, Seq[String]] = scala.collection.mutable.Map[String, Seq[String]]()

  /**
   * 生成节点对应出度信息
   * @param list
   */
  def generate(list: List[String]): Unit = {
    list.map(x => {
      val kv = x.split("->")
      var value: Seq[String] = nodeMap.getOrElse(kv(0), Seq[String]())
      value = value :+ kv(1)
      nodeMap.put(kv(0),value)
    })
  }

  /**
   * 输出指定节点到叶子节点的所有路径
   * @param root
   * @param path
   */
  def dis(root: String, path: String): Unit = {
    val nodes = nodeMap.getOrElse(root, Seq[String]())
    nodes.foreach(x => {
      val temp = path + x
      dis(x, temp)
    })
    if(nodes.isEmpty){
      println(path)
    }
  }

  def main(args: Array[String]): Unit = {
    val list = List("a->b", "c->d", "c->e", "b->c", "e->f")
    generate(list)
    println(nodeMap)
    dis("a", "a")
  }
}