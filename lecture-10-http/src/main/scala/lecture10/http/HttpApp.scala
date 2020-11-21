package lecture10.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.{Route, RouteConcatenation}
import com.softwaremill.macwire.{wire, wireSet}
import lecture10.circejson.CoffeeOrder

import scala.collection.concurrent.TrieMap
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

// routes <--- маршрутизация + сериализация/десериализация <---- соб
// сервисный уровень <--- бизнес-логика <----- 95 %
// dal - data access layer <----
object HttpApp extends App {
  import akka.http.scaladsl.server.Directives._

  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext = actorSystem.dispatcher

  val storage = new CoffeeOrderStorage()
  val service = new SimpleCoffeeShopService(storage)
  val coffeeShopRoutesV1 = new CoffeeShopRoutesApiV1(service).route

  val coffeeShopRoutes = pathPrefix("api" / "v1") {
    coffeeShopRoutesV1
  }
  val bookingRoutes = wire[Booking].route

  val allRoutes: Set[Route] = wireSet[Route]

  Http()
    .newServerAt("localhost", 8080)
    .bind(coffeeShopRoutes)
//    .bind(RouteConcatenation.concat(allRoutes.toList: _*))
    .foreach(s => println(s"server started at: $s"))
}


class Booking {
  def route: Route = ???
}


class CoffeeShopRoutesApiV1(service: CoffeeShopService) {

  import akka.http.scaladsl.server.Directives._
  import de.heikoseeberger.akkahttpcirce.ErrorAccumulatingCirceSupport._

  private val coffeePleaseRoute: Route = (post & path("coffee" / "please")) {
    entity(as[CoffeeOrder]) { coffeeOrder =>
      complete(service.makeCoffee(coffeeOrder))
    }
  }
  private val getMyOrderRoute: Route = (get & path("coffee" / IntNumber)) { orderId =>
    complete(service.getCoffeeOrder(orderId))
  }

  val route: Route = coffeePleaseRoute ~ getMyOrderRoute
}


class SimpleCoffeeShopService(storage: CoffeeOrderStorage)(implicit ec: ExecutionContext) extends CoffeeShopService {
  override def makeCoffee(coffeeOrder: CoffeeOrder): Future[Int] = {
    val orderId = Random.nextInt()
    storage.put(orderId, coffeeOrder)
      .map(_ => orderId)
  }

  override def getCoffeeOrder(id: Int): Future[CoffeeOrder] = storage.get(id)
}


class CoffeeOrderStorage {
  def put(id: Int, coffeeOrder: CoffeeOrder): Future[Unit] = {
    coffeeOrders.put(id, coffeeOrder)
    Future.unit
  }

  def get(id: Int): Future[CoffeeOrder] = Future.successful(coffeeOrders(id))

  private val coffeeOrders = TrieMap[Int, CoffeeOrder]()
}


