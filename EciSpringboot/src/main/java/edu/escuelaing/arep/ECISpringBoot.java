package edu.escuelaing.arep;

import edu.escuelaing.arep.annotation.Service;
import edu.escuelaing.arep.server.HttpServer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class ECISpringBoot {
    private Map<String, Method> services = new HashMap<>();
    private static ECISpringBoot _instance = new ECISpringBoot();

    private ECISpringBoot() {
    }

    public static ECISpringBoot getInstance() {
        return _instance;
    }

    private void loadComponents() {
        String[] searchComponentList = searchComponentList();
        for (String componentName : searchComponentList) {
            loadServices(componentName);
        }
    }

    private void loadServices(String componentName) {
        try {
            Class c = Class.forName(componentName);

            //loading methods of the class
            Method[] declaredMethods = c.getDeclaredMethods();
            for (Method method : declaredMethods) {
                if (method.isAnnotationPresent(Service.class)) {
                    //annotation = @Service("data")
                    Service annotation = method.getAnnotation(Service.class);
                    services.put(annotation.value(), method);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String[] searchComponentList() {
        return new String[]{"edu.escuelaing.arep.status.StatusService"};
    }

    public String invokeService(String serviceName) {
        // buscar que el servicio exista en mis metodos
        try {
            if( !services.containsKey(serviceName)) serviceName = "notFound";
            Method serviceMethod = services.get(serviceName);
            return (String) serviceMethod.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "Service error";
    }

    public void startServer() {
        loadComponents();
        HttpServer server = new HttpServer();
        try {
            server.start();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}
