package data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "x",
        "y"
})
public class Point {

  protected float x;
  protected float y;

  /**
   * Gets the value of the x property.
   */
  public float getX() {
    return x;
  }

  /**
   * Sets the value of the x property.
   */
  public void setX(float value) {
    this.x = value;
  }

  /**
   * Gets the value of the y property.
   */
  public float getY() {
    return y;
  }

  /**
   * Sets the value of the y property.
   */
  public void setY(float value) {
    this.y = value;
  }

}
