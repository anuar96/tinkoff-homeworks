package lecture8.assignment

import java.util.concurrent.ConcurrentHashMap

import scala.collection.concurrent.TrieMap
import scala.collection.mutable.HashMap


class Bag {
  private val things = TrieMap[String, Double]()

  def putThing(what: String, price: Double): Unit = {
    things.put(what, price)
  }
  def size: Int = things.size
}
