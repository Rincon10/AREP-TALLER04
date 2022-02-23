package edu.escuelaing.arep.services;

import edu.escuelaing.arep.annotation.Component;
import edu.escuelaing.arep.annotation.RequestMapping;
import edu.escuelaing.arep.annotation.Service;
import edu.escuelaing.arep.server.HttpServer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import static edu.escuelaing.arep.server.HttpServer.typesMap;
import static edu.escuelaing.arep.utils.Constants.RESOURCES_PATH;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/22/2022
 * @project EciSpringboot
 * Class that offer a services related with images
 */
@Component
public class ImageService {

    /**
     * Method that return a image
     *
     * @return String, default image loaded by the framework
     * @throws URISyntaxException, in case the given uri is not correct
     */
    @RequestMapping("image")
    public static String computeImage() throws URISyntaxException {
        String responseContent;
        String extensionUri = "png";
        URI resourceURI = new URI("/public/img.png");

        responseContent = "HTTP/1.1 200 OK \r\n"
                + "Content-Type: " + typesMap.get(extensionUri) + "\r\n"
                + "\r\n";

        File file = new File(RESOURCES_PATH + resourceURI.getPath());
        try {
            BufferedImage bi = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(HttpServer.outputStream);
            ImageIO.write(bi, extensionUri, byteArrayOutputStream);
            dataOutputStream.writeBytes(responseContent);
            dataOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseContent;
    }
}
