package data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "point"
})
public class Coordinates {

  protected List<Point> point;

  /**
   * Gets the value of the point property.
   *
   * <p>
   * This accessor method returns a reference to the live list,
   * not a snapshot. Therefore any modification you make to the
   * returned list will be present inside the JAXB object.
   * This is why there is not a <CODE>set</CODE> method for the point property.
   *
   * <p>
   * For example, to add a new item, do as follows:
   * <pre>
   *    getPoint().add(newItem);
   * </pre>
   *
   *
   * <p>
   * Objects of the following type(s) are allowed in the list
   * {@link Point }
   */
  public List<Point> getPoint() {
    if (point == null) {
      point = new ArrayList<Point>();
    }
    return this.point;
  }

}
