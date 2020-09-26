package homework

sealed trait Tree

object Tree{
  def maxValue(tree: Tree): Option[Int] = {
    def max(a: Int, b: Int): Int ={
      if (a > b) a else b
    }

    def maxValue(tree: Tree, currentMax: Int): Int ={
      tree match {
        case YellowLeaf | RedLeaf | GreenLeaf => currentMax
        case Node(value, left, right) if value > currentMax => max(maxValue(left, value), maxValue(right, value))
        case Node(_, left, right) => max(maxValue(left, currentMax), maxValue(right, currentMax))
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
      case Node(_, RedLeaf, tree) => countYellowAndRedValues(tree) + 1
      case Node(_, tree, RedLeaf) => countYellowAndRedValues(tree) + 1
      case Node(_, tree, YellowLeaf) => countYellowAndRedValues(tree) + 1
      case Node(_, YellowLeaf, tree) => countYellowAndRedValues(tree) + 1
      case Node(_, left, right) => countYellowAndRedValues(left) + countYellowAndRedValues(right)
    }
  }
}

case class Node(value: Int, left: Tree, right: Tree) extends Tree

case object RedLeaf extends Tree
case object YellowLeaf extends Tree
case object GreenLeaf extends Tree