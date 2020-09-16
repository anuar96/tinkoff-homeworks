// Path dependent types

case class Account(amount: Double, owner: String)

case class Owner(name: String){

 def createAccount(amount: Double) = Account(amount, name)

 def combine(a1: Account, a2: Account) = {
  require(a1.owner == a2.owner)
  Account(a1.amount + a2.amount, a1.owner)
 }
}

val bob = Owner("Bob")
val alice = Owner("Alice")

val a1 = bob.createAccount(100)
val a2 = alice.createAccount(100)

bob.combine(a1, a2)

case class OwnerV2(name: String){

 case class AccountV2(amount: Double, owner: String)

 def createAccount(amount: Double) = AccountV2(amount, name)

 def combine(a1: AccountV2, a2: AccountV2) = {
  Account(a1.amount + a2.amount, a1.owner)
 }
}

val bob2 = OwnerV2("Bob2")
val alice2 = OwnerV2("Alice2")

val a3 = bob2.createAccount(100)
val a4 = alice2.createAccount(100)

bob2.combine(a3, a4)
