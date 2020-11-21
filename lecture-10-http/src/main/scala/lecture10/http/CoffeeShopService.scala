package lecture10.http

import lecture10.circejson.CoffeeOrder

import scala.concurrent.Future

trait CoffeeShopService {

  def makeCoffee(coffeeOrder: CoffeeOrder): Future[Int]

  def getCoffeeOrder(id: Int): Future[CoffeeOrder]
}
