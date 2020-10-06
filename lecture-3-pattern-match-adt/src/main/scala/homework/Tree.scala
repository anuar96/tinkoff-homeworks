package homework

sealed trait Tree

object Tree{
  def maxValue(tree: Tree): Option[Int] = {

    def maxValue(tree: Tree, currentMax: Int): Int ={
      tree match {
        case YellowLeaf | RedLeaf | GreenLeaf => currentMax
        case Node(value, left, right) if value > currentMax => maxValue(left, value) max maxValue(right, value)
        case Node(_, left, right) => maxValue(left, currentMax) max maxValue(right, currentMax)
      }
    }

    tree match {
      case YellowLeaf | RedLeaf | GreenLeaf => None
      case tree@Node(value, _, _) => Some(maxValue(tree, value))
    }
  }

  def countYellowAndRedValues(tree: Tree): Int = {
    tree match {
      case YellowLeaf | RedLeaf | GreenLeaf => 0
      case Node(value, RedLeaf | YellowLeaf, tree) => value + countYellowAndRedValues(tree)
      case Node(value, tree, RedLeaf | YellowLeaf) => value + countYellowAndRedValues(tree)
      case Node(value, left, right) =>value + countYellowAndRedValues(left) + countYellowAndRedValues(right)
    }
  }
}

case class Node(value: Int, left: Tree, right: Tree) extends Tree

case object RedLeaf extends Tree
case object YellowLeaf extends Tree
case object GreenLeaf extends Tree