
package data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "geometricalObjects"
})
@XmlRootElement(name = "geometry")
public class Geometry {

    protected List<GeometricalObject> geometricalObjects;

    public List<GeometricalObject> getGeometricalObjects() {
        if (geometricalObjects == null) {
            geometricalObjects = new ArrayList<GeometricalObject>();
        }
        return this.geometricalObjects;
    }

}
