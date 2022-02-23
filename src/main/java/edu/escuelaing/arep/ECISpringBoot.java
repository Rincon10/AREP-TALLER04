package edu.escuelaing.arep;

import edu.escuelaing.arep.annotation.Component;
import edu.escuelaing.arep.annotation.RequestMapping;
//import edu.escuelaing.arep.annotation.Service;
import edu.escuelaing.arep.server.HttpServer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static edu.escuelaing.arep.utils.Constants.DEFAULT_PATH;

/**
 * Indicates a configuration class that declares one or more methods that our class is gonna use
 */
public class ECISpringBoot {
    private File pathToSearch;
    private Map<String, Method> services = new HashMap<>();
    private static ECISpringBoot _instance = new ECISpringBoot();

    /**
     * Constructor of the SprintApplication, that set the default path to search all the annotations
     */
    private ECISpringBoot() {
        String packageName;
        packageName = ECISpringBoot.class.getPackage().getName().replace(".", "/");
        //DEFAULT_PATH + packageName
        this.pathToSearch = new File(DEFAULT_PATH + packageName);
    }

    /**
     * Constructor of the SprintApplication, that set a specific path to search all the annotations
     *
     * @param pathToSearch, File that represents the default path to search all the Springboot annotations
     */
    private ECISpringBoot(File pathToSearch) {
        this.pathToSearch = pathToSearch;
    }

    /**
     * Method that return the only instance created of our framework
     *
     * @return ECISpringBoot, Returns the unique instance created of our framework.
     */
    public static ECISpringBoot getInstance() {
        return _instance;
    }

    /**
     * Method that load all the components with the @componnet annotation
     */
    private void loadComponents() {
        List<String> searchComponentList = searchComponentList(pathToSearch);

        //Loading all the methods that have the @component
        if (searchComponentList != null) {
            searchComponentList.forEach(componentName -> {
                loadServices(componentName);
            });
        }

    }

    /**
     * Method that save all the methods in our hashmap of a specific class
     *
     * @param componentName, className of the component with a @Component annotation
     */
    private void loadServices(String componentName) {
        try {
            Class c = Class.forName(componentName);

            //loading methods of the class
            Method[] declaredMethods = c.getDeclaredMethods();
            for (Method method : declaredMethods) {
//                if (method.isAnnotationPresent(Service.class)) {
//                    //annotation = @Service("data")
//                    Service annotation = method.getAnnotation(Service.class);
//                    services.put(annotation.value(), method);
//                }
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    services.put(annotation.value(), method);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that transform path format to a class-path format
     *
     * @param path, current path to transform to class-path format
     * @return String, class-path format of the path
     */
    private String transFormPath(String path) {
        return path.replace(".java", "").replace("\\", ".").replace("..src.main.java.", "");
    }

    /**
     * Method that Seacrch all the components with @Component annotation in a specific seed path
     *
     * @param file, File with a seed path from which to start the search
     * @return List<String>, List of the name of all components with @Component annotation
     */
    private List<String> searchComponentList(File file) {
        List<String> componentList = new ArrayList();
        if (file.isDirectory()) {
            for (File source : file.listFiles()) {
                componentList.addAll(searchComponentList(source));
            }
        } else {
            if (file.getName().endsWith("java")) {
                String path = transFormPath(file.getPath());
                Class c = null;
                try {
                    c = Class.forName(path);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (c.isAnnotationPresent(Component.class)) {
                    componentList.add(path);
                }
            }
        }
        return componentList;
    }

    /**
     * Method that invoke a specific method from a class
     *
     * @param serviceName, String that represents the name service stored on our Framework
     * @return String, That represents the execution of the method
     */
    public String invokeService(String serviceName) {
        // buscar que el servicio exista en mis metodos
        try {
            if (!services.containsKey(serviceName)) serviceName = "notFound";
            Method serviceMethod = services.get(serviceName);
            return (String) serviceMethod.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return "Service error";
    }

    /**
     * Run server method
     */
    public void startServer() {
        loadComponents();
        HttpServer server = new HttpServer();
        try {
            try {
                server.start();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }

}
