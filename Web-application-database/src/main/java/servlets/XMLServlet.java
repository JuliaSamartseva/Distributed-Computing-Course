package servlets;

import com.google.gson.Gson;
import data.Geometry;
import parsers.XMLGeometry;
import service.GeometryService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/servlets/xml/*")
public class XMLServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(XMLServlet.class.getName());

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

        String[] urls = request.getPathInfo().split("/");
        if (urls.length == 2) {
            switch (urls[1]) {
                case "load": {
                    log.info("Received request to load data from file");
                    GeometryService.removeAllObjects();
                    Geometry geometry = XMLGeometry.loadFromFile("polygons.xml");
                    GeometryService.addGeometry(geometry);
                    break;
                }
                case "upload": {
                    log.info("Received request to upload data to file");
                    Geometry geometry = GeometryService.getAll();
                    XMLGeometry.saveToFile("C:\\Julia\\University\\Distributed-Computing-Course\\Web-application-database\\src\\main\\resources\\polygons.xml", geometry);
                    break;
                }
            }
        }
    }

}
