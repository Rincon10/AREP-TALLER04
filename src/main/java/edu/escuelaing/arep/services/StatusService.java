package edu.escuelaing.arep.services;

import edu.escuelaing.arep.annotation.Component;
import edu.escuelaing.arep.annotation.RequestMapping;
//import edu.escuelaing.arep.annotation.Service;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/16/2022
 * @project EciSpringboot
 * Class that offer a services related with possibles status
 */
@Component
public class StatusService {
    /**
     * Method that return a html page with information of a specific status
     *
     * @return String, that represents a html page with information of a specific status
     */
    @RequestMapping("status")
    public static String status() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Title of the document</title>\n"
                + "</head>"
                + "<body>"
                + "<div>"
                + "<h1>Service Status</h1>"
                + "</div>"
                + "<p>"
                + " Ok"
                + "</p>"
                + "</body>"
                + "</html>";
    }

    @RequestMapping("notFound")
    public static String notFound() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Not found</title>\n"
                + "</head>"
                + "<body>"
                + "<div>"
                + "<h1>404 Error</h1>"
                + "</div>"
                + "<h2>Oops! This Page Could Not Be Found</h2>"
                + "<p>"
                + "Sorry but the page you are looking for does not exist or have been removed. name changed or is temporarily unavailable"
                + "</p>"
                + "</body>"
                + "</html>";
    }
}
