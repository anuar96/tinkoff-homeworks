// Поля и методы

// Счет Пустой класс, с полем, с конструктором
class AccountV0

class AccountV1 {
  val money = 1.0
}

class AccountV2(money: Double){

  def getMoney: Double = money
}

class AccountV3(val money: Double)

val a1 = new AccountV3(1000)
a1.money

// Транзакция с методом transact
class TransactionV1(val from: AccountV3, val to: AccountV3, val amount: Double) {

  def transact: (AccountV3, AccountV3) =
    (new AccountV3(from.money - amount), new AccountV3(to.money + amount))

}

val a2 = new AccountV3(100)
val (newAc1, newAc2) = new TransactionV1(a1, a2, 450).transact

newAc1.money
newAc2.money

// Методы класса Any
// toString, hashCode, equals(то же самое что и ==)

// Версии с переопределенными toString
class AccountV4(val money: Double) {
  override def toString = "Account. money:" + money
}

class TransactionV2(val from: AccountV4, val to: AccountV4, val amount: Double) {

  def transact: (AccountV4, AccountV4) =
    (new AccountV4(from.money - amount), new AccountV4(to.money + amount))

  override def toString = "Transaction from " + from +" to " + to + ". Amount:" + amount
}

val a3 = new AccountV4(1000)
val a4 = new AccountV4(100)

val tr = new TransactionV2(a3, a4, 450)

val a3copy = new AccountV4(1000)

a3copy == a3

// Дополнительные конструкторы
class AccountV5(val money: Double){

  def this(promo: String) = {
    this(
      if(promo == "1234")1000 else 10
    )
    println("promo account initialization")
  }

}

new AccountV5("1234").money

