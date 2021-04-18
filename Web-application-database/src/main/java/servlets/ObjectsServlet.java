package servlets;

import com.google.gson.Gson;
import data.Coordinates;
import data.GeometricalObject;
import data.Point;
import service.GeometryService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/servlets/objects/*")
public class ObjectsServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(ObjectsServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Received get request");
        if (response == null) {
            throw new IllegalArgumentException("Response must not be null.");
        }

        if (request == null) {
            throw new IllegalArgumentException("Request must not be null.");
        }

        Gson gson = new Gson();

        if (request.getPathInfo() == null || request.getPathInfo().equals("/")) {
            log.info("Received request to get all products");
            List<GeometricalObject> objects = GeometryService.getAll().getGeometricalObjects();
            response
                    .getWriter()
                    .println(gson.toJson(objects.toArray(new GeometricalObject[]{})));
            return;
        }

        String[] urls = request.getPathInfo().split("/");
        if (urls.length == 2) {
            switch (urls[1]) {
                case "remove": {
                    log.info("Received request to remove object with given id");
                    int id = Integer.parseInt(request.getParameter("id"));
                    GeometryService.removeObject(id);
                    break;
                }
                case "edit": {
                    log.info("Received request to get object with id");
                    int id = Integer.parseInt(request.getParameter("id"));
                    GeometricalObject object = GeometryService.getObjectWithId(id);
                    response.setContentType("application/json");
                    response
                            .getWriter()
                            .println(gson.toJson(object));
                    break;
                }
            }
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("Response must not be null.");
        }

        if (request == null) {
            throw new IllegalArgumentException("Request must not be null.");
        }

        String[] urls = request.getPathInfo().split("/");
        log.info("Received data from the object editor. Adding the object to the database.");
        Map<String, String[]> parameterMap = request.getParameterMap();
        GeometricalObject object = new GeometricalObject();
        object.setName(parameterMap.get("name")[0]);
        object.setType(parameterMap.get("type")[0]);
        object.setSides(Integer.parseInt(parameterMap.get("sides")[0]));
        Coordinates coordinates = getCoordinatesFromString(parameterMap.get("coordinates")[0]);
        object.setCoordinates(coordinates);
        if (urls.length == 2) {
            switch (urls[1]) {
                case "add":
                    log.info("Adding object to the database");
                    GeometryService.addObject(object);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
                case "edit":
                    log.info("Editing object in the database");
                    int id = Integer.parseInt(parameterMap.get("id")[0]);
                    object.setId(id);
                    GeometryService.editObject(object);
                    response.setStatus(HttpServletResponse.SC_OK);
                    break;
            }
        }
    }

    private Coordinates getCoordinatesFromString(String coordinates) {
        Coordinates result = new Coordinates();
        coordinates = coordinates.replace("(", "").replace(")", "");
        String[] parts = coordinates.split(", ");
        for (int i = 0; i < parts.length; i += 2) {
            Point point = new Point();
            point.setX(Float.parseFloat(parts[i]));
            point.setY(Float.parseFloat(parts[i + 1]));
            result.getPoint().add(point);
        }
        return result;
    }
}
