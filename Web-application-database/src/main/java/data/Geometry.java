
package data;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "geometricalObjects"
})
@XmlRootElement(name = "geometry")
public class Geometry {

    @XmlElement(name = "geometricalObject", required = true)
    protected List<GeometricalObject> geometricalObjects;

    public List<GeometricalObject> getGeometricalObjects() {
        if (geometricalObjects == null) {
            geometricalObjects = new ArrayList<GeometricalObject>();
        }
        return this.geometricalObjects;
    }

}
