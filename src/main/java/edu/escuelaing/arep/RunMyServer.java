package edu.escuelaing.arep;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/16/2022
 * @project EciSpringboot
 * Main class that start our EciSpringBoot
 */
public class RunMyServer {

    /**
     * Main method that starts our framework
     * @param args
     */
    public static void main(String[] args) {
        ECISpringBoot.getInstance().startServer();
    }
}
