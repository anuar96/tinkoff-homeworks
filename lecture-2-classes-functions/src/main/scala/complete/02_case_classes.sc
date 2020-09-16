// case class

case class AccountV1(money: Double)

// equals, toString

val a1 = AccountV1(1000)

val a1copy = AccountV1(1000)
a1 == a1copy
a1.eq(a1copy)

// copy


case class AccountV2(money: Double, owner: String)

val a2 = AccountV2(1000, "Bob")

a2.copy(owner = "Alice")



