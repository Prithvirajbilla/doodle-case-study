package doodle.backend

sealed trait Image {

  val boundingBox: BoundingBox = this match {
    case Circle(r) => BoundingBox(-r, r, r, -r)
    case Rectangle(w,h) => BoundingBox(-w/2, h/2, w/2, h/2)
    case Above(a,b) => a.boundingBox above b.boundingBox
    case Beside(a,b) => a.boundingBox beside b.boundingBox
    case On(a,b) => a.boundingBox on b.boundingBox
  }

  def on(that: Image): Image = On(this, that)

  def beside(that: Image): Image = Beside(this, that)

  def above(that: Image): Image = Above(this, that)

  def draw(canvas: Canvas): Unit = draw(canvas, 0.0, 0.0)

  def draw(canvas: Canvas, originX: Double, originY: Double):Unit =
    this match {
      case Circle(radius) => canvas.circle(originX,originY,radius)
      case Rectangle(width, height) => canvas.rectangle(-width/2+originX,height/2+originY,width/2+originX,-height/2+originY)
      case Above(a,b) => {
        val box = this.boundingBox
        val aBox = a.boundingBox
        val bBox = b.boundingBox

        val aboveOriginY = originY + box.top - (aBox.height / 2)
        val belowOriginY = originY + box.bottom + (bBox.height /2)

        a.draw(canvas, originX, aboveOriginY)
        b.draw(canvas, originX, belowOriginY)
      };
      case Beside(l,r) => {
        val box = this.boundingBox
        val lBox = l.boundingBox
        val rBox = r.boundingBox

        val leftOriginX = originX + box.left + (lBox.width/2)
        val rightOriginX = originX + box.right - (rBox.width/2)
        l.draw(canvas, leftOriginX, originY)
        r.draw(canvas, rightOriginX, originY)
      };
      case On(a,b) => a.draw(canvas, originX, originY); b.draw(canvas,originX,originY)
  }

}

final case class Circle(radius: Double) extends Image

final case class Rectangle(width: Double, height: Double) extends Image

final case class On(front: Image, back: Image) extends Image

final case class Beside(left: Image, right: Image) extends Image

final case class Above(above: Image, below: Image) extends Image

final case class BoundingBox(left: Double, top: Double, right: Double, bottom: Double) {
<<<<<<< HEAD

=======
>>>>>>> f0f3f3b7e34e4148f880ff3ee99c13e0b35c120e
  val height = top - bottom
  val width = right - left

  def above(that: BoundingBox): BoundingBox = BoundingBox(this.left min that.left,
<<<<<<< HEAD
    (this.height+that.height)/2,this.right max that.right,-(this.height + that.height)/2)

  def beside(that: BoundingBox): BoundingBox =
    BoundingBox(-(this.width+that.width)/2, this.top max that.top, (this.width + that.width)/2, this.bottom min that.bottom)

  def on(that: BoundingBox): BoundingBox =
    BoundingBox(this.left min that.left, this.top max that.top, this.right max that.right, this.bottom min that.bottom)
=======
    this.height,this.left,this.left)
>>>>>>> f0f3f3b7e34e4148f880ff3ee99c13e0b35c120e
}



