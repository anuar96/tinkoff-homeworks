package lecture9

import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape

import scala.concurrent.{ExecutionContext, Future}

case class PaymentTemplate(id: Int, name: String, sum: BigDecimal,
                           receiverAccount: String, description: String, version: Int)

class PaymentTemplateService(db: Database)(implicit ec: ExecutionContext) {

  def updateTemplateSum(id: Int, newSum: BigDecimal): Future[Unit] = {
    db.run(AllPaymentTemplates.filter(_.id === id).map(_.sum).update(newSum).map(_ => ()))
  }

  class PaymentTemplatesTable(tag: Tag) extends Table[PaymentTemplate](tag, "PAYMENT_TEMPLATES") {
    def id: Rep[Int] = column("id")
    def sum: Rep[BigDecimal] = column("sum")
    def version: Rep[Int] = column("version")
    // ....
    override def * : ProvenShape[PaymentTemplate] = ???
  }

  private val AllPaymentTemplates = TableQuery[PaymentTemplatesTable]
}
