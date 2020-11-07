package homework

import homework.CustomersQueryRepository.AllCustomers
import slick.dbio.Effect
import slick.jdbc.H2Profile.api._
import slick.lifted.{ForeignKeyQuery, ProvenShape}

import scala.concurrent.ExecutionContext



case class CoffeeOrder(id: Int,
                       name: String,
                       price: BigDecimal,
                       nickname: String,
                       customerId: Option[Int] = None,
                       discountApplied: Boolean = false)


class CoffeeOrdersTable(tag: Tag) extends Table[CoffeeOrder](tag, "COFFEE_ORDERS") {

  def orderId: Rep[Int] = column("ORDER_ID", O.PrimaryKey)

  def name: Rep[String] = column("NAME")

  def price: Rep[BigDecimal] = column("PRICE")

  def nickname: Rep[String] = column("NICKNAME")

  def discountApplied: Rep[Boolean] = column("DISCOUNT_APPLIED")

  def customerId: Rep[Option[Int]] = column("CUSTOMER_ID")

  // Это объявление нужно, т.к. для простоты схему данных мы генерируем с помощью slick. В продакшене для манипуляций со
  // схемой данных чаще всего используют специальный инструменты, например, flyway или liquibase.
  def customer: ForeignKeyQuery[CustomersTable, Customer] =
    foreignKey("CUSTOMER_FK", customerId, CustomersQueryRepository.AllCustomers)(_.id)

  override def * : ProvenShape[CoffeeOrder] = (orderId, name, price, nickname, customerId, discountApplied).mapTo[CoffeeOrder]
}

object CoffeeOrderQueryRepository {
  val AllOrders = TableQuery[CoffeeOrdersTable]

  def listCustomerOrders(customerId: Int): DIO[Seq[CoffeeOrder], Effect.Read] =
    AllOrders
      .filter(_.customerId === customerId)
      .result

  def findOrder(orderId: Int): DIO[Option[CoffeeOrder], Effect.Read] =
    DIO.successful(None) // TODO

  def addOrder(order: CoffeeOrder): DIO[Int, Effect.Write] =
    DIO.successful(0) // TODO

  def updateNickname(orderId: Int, nickname: String): DIO[Int, Effect.Write] =
    DIO.successful(0) // TODO

  def listTopNCoffees(n: Int): DIO[Seq[(String, Int)], Effect.Read] =
    DIO.successful(Seq()) // TODO

  def listTopNCustomers(n: Int)(implicit ec: ExecutionContext): DIO[Seq[(Int, BigDecimal)], Effect.Read] =
    DIO.successful(Seq()) // TODO

  def listTopNNicknames(n: Int)(implicit ec: ExecutionContext): DIO[Seq[(String, Int)], Effect.Read] =
    DIO.successful(Seq()) // TODO


  def countOrdersWithRealCustomerNames: DIO[Int, Effect.Read] =
    DIO.successful(0) // TODO

  def applyDiscount(id: Int,
                    discounts: Map[String, BigDecimal])
                   (implicit ec: ExecutionContext): DIO[Boolean, Effect.Read with Effect.Write] =
    DIO.successful(false) // TODO

}