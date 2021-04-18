package servlets;

import javax.servlet.annotation.WebServlet;
import java.util.logging.Logger;

@WebServlet("/servlets/xml/*")
public class XMLServlet {
    private static final Logger log = Logger.getLogger(XMLServlet.class.getName());
}
