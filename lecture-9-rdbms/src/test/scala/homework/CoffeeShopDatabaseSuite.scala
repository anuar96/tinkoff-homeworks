package homework

import java.util.UUID

import homework.CoffeeOrderQueryRepository.AllOrders
import homework.CustomersQueryRepository.AllCustomers
import org.scalactic.source
import org.scalatest.compatible
import org.scalatest.funsuite.AsyncFunSuite
import slick.dbio.{DBIOAction, Effect, NoStream}
import slick.jdbc.H2Profile.api._
import slick.jdbc.JdbcBackend.Database

abstract class CoffeeShopDatabaseSuite extends AsyncFunSuite {
  protected def test[R, S <: NoStream, E <: Effect](testName: String)
                                                   (testFun: DBIOAction[compatible.Assertion, S, E])
                                                   (implicit pos: source.Position): Unit = {
    super.test(testName) {

      val db = Database.forURL(
        s"jdbc:h2:mem:${UUID.randomUUID()}",
        driver = "org.h2.Driver",
        keepAliveConnection = true
      )

      db.run(
        initSchema
          .andThen(AllCustomers ++= SampleCustomers)
          .andThen(AllOrders ++= SampleOrders)
      )
        .flatMap(_ => db.run(testFun))
        .andThen { case _ => db.close() }
    }
  }

  private val initSchema =
    (CoffeeOrderQueryRepository.AllOrders.schema ++ CustomersQueryRepository.AllCustomers.schema).create


  protected val SampleCustomers = Seq(
    Customer(1, "Alla", "alla@email.com", true),
    Customer(2, "Peka", "peka@peka.wat", true)
  )

  protected val SampleOrders = Seq(
    CoffeeOrder(1, "Latte", 100, "Valera"),
    CoffeeOrder(2, "Americano", 50, "Alla", Some(1)),
    CoffeeOrder(3, "Americano", 50, "Alfa", Some(1)),
    CoffeeOrder(4, "Espresso", 100, "Alla"),
    CoffeeOrder(5, "Americano", 50, "Alla", Some(1)),
    CoffeeOrder(6, "Latte", 100, "Peka", Some(2)),
    CoffeeOrder(7, "Espresso", 50, "Valera")
  )
}
