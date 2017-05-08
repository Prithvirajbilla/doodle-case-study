package doodle.backend

sealed trait Image {

  def on(that: Image): Image = On(this, that)

  def beside(that: Image): Image = Beside(this, that)

  def above(that: Image): Image = Above(this, that)

  def draw(canvas: Canvas): Unit = this match {
    case Circle(radius) => canvas.circle(0,0,radius)
    case Rectangle(width, height) => canvas.rectangle(-width/2,height/2,width/2,-height/2)
  }

}

final case class Circle(radius: Double) extends Image

final case class Rectangle(width: Double, height: Double) extends Image

final case class On(front: Image, back: Image) extends Image

final case class Beside(left: Image, right: Image) extends Image

final case class Above(above: Image, below: Image) extends Image

final case class BoundingBox(left: Double, top: Double, right: Double, bottom: Double) {
  val height = top - bottom
  val width = right - left

  def above(that: BoundingBox): BoundingBox = BoundingBox(this.left min that.left,
    this.height,this.left,this.left)
}



