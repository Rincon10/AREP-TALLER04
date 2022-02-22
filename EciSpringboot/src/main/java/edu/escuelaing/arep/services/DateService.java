package edu.escuelaing.arep.services;

import edu.escuelaing.arep.annotation.Component;
import edu.escuelaing.arep.annotation.RequestMapping;

import java.util.Date;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/22/2022
 * @project EciSpringboot
 */
@Component
public class DateService {
    @RequestMapping("date")
    public static String date() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<meta charset=\"UTF-8\">"
                + "<title>Date</title>\n"
                + "</head>"
                + "<body>"
                + "<h1>Current Date</h1>"
                + "</div>"
                + "<p>"
                + new Date().toString()
                + "</p>"
                + "</body>"
                + "</html>";
    }
}
