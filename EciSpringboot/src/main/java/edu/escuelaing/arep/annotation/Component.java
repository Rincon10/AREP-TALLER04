package edu.escuelaing.arep.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Iván Camilo Rincón Saavedra
 * @version 1.0 2/22/2022
 * @project EciSpringboot
 * Interface that is gonna told us the different components of our framework
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Component {
}
