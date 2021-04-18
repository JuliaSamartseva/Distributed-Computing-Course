package data;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "name",
        "type",
        "sides",
        "coordinates"
})
public class GeometricalObject {

  @XmlElement(required = true)
  protected String name;
  @XmlElement(required = true)
  protected String type;
  protected int sides;
  @XmlElement(required = true)
  protected Coordinates coordinates;
  @XmlAttribute(name = "id", required = true)
  @XmlSchemaType(name = "positiveInteger")
  protected Integer id;

  /**
   * Gets the value of the name property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * Gets the value of the type property.
   *
   * @return possible object is
   * {@link String }
   */
  public String getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   *
   * @param value allowed object is
   *              {@link String }
   */
  public void setType(String value) {
    this.type = value;
  }

  /**
   * Gets the value of the sides property.
   */
  public int getSides() {
    return sides;
  }

  /**
   * Sets the value of the sides property.
   */
  public void setSides(int value) {
    this.sides = value;
  }

  /**
   * Gets the value of the coordinates property.
   *
   * @return possible object is
   * {@link Coordinates }
   */
  public Coordinates getCoordinates() {
    return coordinates;
  }

  /**
   * Sets the value of the coordinates property.
   *
   * @param value allowed object is
   *              {@link Coordinates }
   */
  public void setCoordinates(Coordinates value) {
    this.coordinates = value;
  }

  /**
   * Gets the value of the id property.
   *
   * @return possible object is
   * {@link Integer }
   */
  public Integer getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   *
   * @param value allowed object is
   *              {@link BigInteger }
   */
  public void setId(Integer value) {
    this.id = value;
  }


}
