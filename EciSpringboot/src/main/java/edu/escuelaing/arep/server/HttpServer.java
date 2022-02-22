package edu.escuelaing.arep.server;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/16/2022
 * @project EciSpringboot
 */

import edu.escuelaing.arep.ECISpringBoot;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static edu.escuelaing.arep.utils.Constants.TYPES;

public class HttpServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private URI resourceURI;
    public static OutputStream outputStream;

    public final static Map<String, String> typesMap = new HashMap<String, String>();

    public HttpServer() {
        setTypes();
    }

    private void setTypes() {
        for (String[] type : TYPES) {
            typesMap.put(type[0], type[1]);
        }
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 35000;
    }

    private String getDefaultHTML() {
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
                + "My Web Site HOLIUIIIIIIII"
                + "</body>"
                + "</html>";
    }

    public void serverConnection(Socket clientSocket) throws IOException, URISyntaxException {
        this.clientSocket = clientSocket;
        serverConnection();
    }

    public void serverConnection() throws IOException, URISyntaxException {
        outputStream = clientSocket.getOutputStream();
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        String inputLine, outputLine;
        ArrayList<String> request = new ArrayList<>();

        while ((inputLine = in.readLine()) != null) {
            System.out.println("Received: " + inputLine);
            request.add(inputLine);
            if (!in.ready()) {
                break;
            }
        }

        String file;
        // Example: 0= "GET /public/css/index.css HTPP/1.1"
        file = request.get(0).split(" ")[1];
        resourceURI = new URI(file);
        if (file.startsWith("/Services/")) {
            outputLine = invokeService(file.replace("/Services/", ""));
        }
        else if (file.startsWith("/public/")) {
            outputLine = invokeService(file.replace("/public/", ""));
        }
        else if (file.length() == 1) {
            outputLine = getDefaultHTML();
        } else {
            String[] controller = file.split("/");
            outputLine = invokeService(controller[1]);
        }
        out.println(outputLine);
    }


    public void start() throws IOException, URISyntaxException {
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }

        boolean running = true;
        while (running) {
            clientSocket = null;

            System.out.println("Listo para recibir ...");
            clientSocket = serverSocket.accept();

            serverConnection(clientSocket);
            closeConnection();
        }
        serverSocket.close();
    }

    private static boolean isAnImage(String extensionUri) {
        return extensionUri.equals("png") || extensionUri.equals("jpg") || extensionUri.equals("jpge");
    }

    private String invokeService(String file) {
        return ECISpringBoot.getInstance().invokeService(file);

    }

    public void closeConnection() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
    }
}