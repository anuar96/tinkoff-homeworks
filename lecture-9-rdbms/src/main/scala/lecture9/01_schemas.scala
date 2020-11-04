package lecture9

import slick.dbio.Effect
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration


case class User(id: Int, name: String)

class UsersTable(tag: Tag) extends Table[User](tag, "USERS") {
  def id: Rep[Int] = column("id", O.PrimaryKey)
  def name: Rep[String] = column("name")

  override def * : ProvenShape[User] = (id, name).mapTo[User]
}


case class Coffee(orderId: Int,
                  name: String,
                  price: BigDecimal,
                  userId: Int)

class CoffeeTable(tag: Tag) extends Table[Coffee](tag, "COFFEES") {
  def orderId = column[Int]("ORDER_ID")
  def name = column[String]("NAME")
  def price = column[BigDecimal]("PRICE")
  def userId = column[Int]("USER_ID")

  override def * = (orderId, name, price, userId).mapTo[Coffee]
}

object CoffeeShopQueryRepository {
  val AllCoffees = TableQuery[CoffeeTable]
  val AllUsers = TableQuery[UsersTable]

  def addUser(user: User): DBIOAction[Int, NoStream, Effect.Write] =
    AllUsers += user

  def addCoffee(coffee: Coffee) =
    AllCoffees += coffee

  val expensiveCoffee: DBIOAction[Seq[Coffee], NoStream, Effect.Read] =
    AllCoffees
      .filter(_.price > BigDecimal(50))
      .result

  val userCoffee: DBIOAction[Option[Coffee], NoStream, Effect.Read] =
    AllCoffees
      .filter(_.userId === 10).result.headOption

  val allOrdersByUser = AllCoffees
    .join(AllUsers)
    .on(_.userId === _.id)
    .result
    .map(_.groupMap(_._2)(_._1))

  val allValeraOrders: DBIOAction[Seq[(User, Coffee)], NoStream, Effect.Read] =
    AllUsers.filter(_.name === "валера")
      .join(AllCoffees)
      .on(_.id === _.userId)
      .result

  val sumByCoffee: DBIOAction[Seq[Option[BigDecimal]], NoStream, Effect.Read] =
    AllCoffees
      .groupBy(_.name)
      .map(_._2.map(_.price).sum)
      .result

}

object CoffeeApp {

  val db = Database.forConfig("h2mem1")
  val program = for {
    _ <- (CoffeeShopQueryRepository.AllCoffees.schema ++ CoffeeShopQueryRepository.AllUsers.schema).create
    _ <- CoffeeShopQueryRepository.AllUsers += User(1, "Fedor")
    _ <- CoffeeShopQueryRepository.AllCoffees += Coffee(1, "Latte", 100, 1)
    _ <- CoffeeShopQueryRepository.AllCoffees += Coffee(1, "Latte", 100, 1)
    allUserOrders <- CoffeeShopQueryRepository.allOrdersByUser
  } yield allUserOrders

  def main(args: Array[String]): Unit = {
    Await.result(db.run(program).map(println), Duration.Inf)
  }

}
