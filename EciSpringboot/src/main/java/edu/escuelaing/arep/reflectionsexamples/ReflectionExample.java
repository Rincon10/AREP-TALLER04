package edu.escuelaing.arep.reflectionsexamples;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import static java.lang.System.out;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/16/2022
 * @project EciSpringboot
 */
public class ReflectionExample {
    public static void main(String[] args) {
        out.println("Hola".getClass());
        printMembers("Hola".getClass().getDeclaredMethods(),"Methods");
    }

    private static void printMembers(Member[] mbrs, String s) {
        out.format("%s:%n", s);
        for (Member mbr : mbrs) {
            if (mbr instanceof Field)
                out.format(" %s%n", ((Field) mbr).toGenericString());
            else if (mbr instanceof Constructor)
                out.format(" %s%n", ((Constructor) mbr).toGenericString());
            else if (mbr instanceof Method)
                out.format(" %s%n", ((Method) mbr).toGenericString());
        }
        if (mbrs.length == 0)
            out.format(" -- No %s --%n", s);
        out.format("%n");
    }
}
