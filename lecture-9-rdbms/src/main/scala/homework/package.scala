import slick.dbio.{DBIO, DBIOAction, Effect, NoStream}

package object homework {
  type DIO[+R, -E <: Effect] = DBIOAction[R, NoStream, E]
  val DIO = DBIO
}
