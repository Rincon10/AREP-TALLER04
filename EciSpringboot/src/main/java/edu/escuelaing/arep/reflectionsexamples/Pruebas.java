package edu.escuelaing.arep.reflectionsexamples;

import edu.escuelaing.arep.annotation.Test;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/16/2022
 * @project EciSpringboot
 */
public class Pruebas {

    public static void m1() {
    }

    @Test
    public static void m2() {
        throw new RuntimeException("Boom m2");
    }

    @Test
    public static void m3() {
    }

    @Test
    public static void m4() {
        throw new RuntimeException("Boom m2");
    }

    public static void m5() {
    }
}
