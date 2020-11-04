package homework

import org.scalatest.matchers.should.Matchers
import slick.jdbc.H2Profile.api._

class CoffeeOrderQueryRepositoryTest extends CoffeeShopDatabaseSuite with Matchers {

  import CoffeeOrderQueryRepository._

  test("customerOrders should query all orders") {
    for {
      orders <- AllOrders.result
    } yield orders should contain theSameElementsAs SampleOrders
  }

  test("findOrder should return order") {
    for {
      order <- findOrder(1)
    } yield assert(order.contains(SampleOrders.head))
  }

  test("findOrder should return None if order not found") {
    for {
      none <- findOrder(-1)
    } yield assert(none.isEmpty)
  }

  test("addOrder should add order to table") {
    val newOrder = CoffeeOrder(500, "Cappuccino", 80, "Karl")
    val a = for {
      _ <- addOrder(newOrder)
      order <- findOrder(500)
    } yield assert(order.contains(newOrder))
    a
  }

  test("updateNickname should update nickname") {
    val newNickname = "Batman"
    val newOrder = CoffeeOrder(501, "Latte", 500, "Superman")
    for {
      _ <- addOrder(newOrder)
      _ <- updateNickname(newOrder.id, newNickname)
      order <- findOrder(newOrder.id)
    } yield assert(order.map(_.nickname).contains(newNickname))
  }

  test("listTopNCoffees should return top N coffees") {
    for {
      top2 <- listTopNCoffees(2)
    } yield top2 should contain theSameElementsInOrderAs Seq(("Americano", 3), ("Latte", 2))
  }

  test("listTopNCustomers should return top N customers") {
    for {
      top2 <- listTopNCustomers(2)
    } yield top2 should contain theSameElementsInOrderAs Seq((1, 150.00), (2, 100.00))
  }

  test("listTopNNicknames should return top N nicknames") {
    for {
      top2 <- listTopNNicknames(2)
    } yield top2 should contain theSameElementsInOrderAs Seq("Alla" -> 3, "Valera" -> 2)
  }

  test("countOrdersWithRealCustomerNames should return count of orders where nickname is equals to customer name") {
    for {
      count <- countOrdersWithRealCustomerNames
    } yield assert(count === 3)
  }

  test("applyDiscount should apply discount") {
    for {
      applied <- applyDiscount(1, Map("Latte" -> 0.10, "Cappuccino" -> 0.15, "Espresso" -> 0.20))
      newPrice <- findOrder(1)
    } yield {
      assert(applied)
      assert(newPrice.map(_.price).contains(90))
    }
  }

  test("applyDiscount should not apply discount if discount was already applied") {
    for {
      applied1 <- applyDiscount(1, Map("Latte" -> 0.10, "Cappuccino" -> 0.15, "Espresso" -> 0.20))
      newPrice1 <- findOrder(1).map(_.map(_.price))

      applied2 <- applyDiscount(1, Map("Latte" -> 0.10, "Cappuccino" -> 0.15, "Espresso" -> 0.20))
      newPrice2 <- findOrder(1).map(_.map(_.price))
    } yield {
      assert(applied1)
      assert(newPrice1.contains(90))

      assert(!applied2)
      assert(newPrice2.contains(90))
    }
  }
}


