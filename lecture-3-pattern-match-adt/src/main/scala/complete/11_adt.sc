import scala.util.Try
// Algebraic Data Type ADT
// tree SUM

sealed trait Tree

case object Leaf extends Tree
case class Node(left: Tree, right: Tree) extends Tree

val tree = Node(Node(Leaf, Node(Leaf, Leaf)), Leaf)

def countLeafs(tree: Tree): Int = tree match {
  case Leaf => 1
  case Node(left, right) => countLeafs(left) + countLeafs(right)
}

countLeafs(tree)
