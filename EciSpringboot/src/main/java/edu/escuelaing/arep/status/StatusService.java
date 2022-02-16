package edu.escuelaing.arep.status;

import edu.escuelaing.arep.annotation.Service;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/16/2022
 * @project EciSpringboot
 */
public class StatusService {

    @Service("status")
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
                + "Service Status Ok"
                + "</body>"
                + "</html>";
    }
}
