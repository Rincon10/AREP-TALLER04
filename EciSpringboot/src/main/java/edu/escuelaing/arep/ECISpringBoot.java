package edu.escuelaing.arep;

import edu.escuelaing.arep.annotation.Component;
import edu.escuelaing.arep.annotation.RequestMapping;
import edu.escuelaing.arep.annotation.Service;
import edu.escuelaing.arep.server.HttpServer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private ECISpringBoot(){
        String packageName = ECISpringBoot.class.getPackageName().replace(".","/");
        this.pathToSearch = new File(DEFAULT_PATH+packageName);
    }

    /**
     * Constructor of the SprintApplication, that set a specific path to search all the annotations
     * @param pathToSearch, File that represents the default path to search all the Springboot annotations
     */
    private ECISpringBoot( File pathToSearch ) {
        this.pathToSearch = pathToSearch;
    }

    /**
     * Method that return the only instance created of our framework
     * @return ECISpringBoot, Returns the unique instance created of our framework.
     */
    public static ECISpringBoot getInstance() {
        return _instance;
    }

    /**
     * Method that load all the components with different annotations
     */
    private void loadComponents() {
        List<String> searchComponentList = searchComponentList( pathToSearch);

        //Loading all the methods that have the @component
        searchComponentList.forEach(componentName -> {
            loadServices(componentName);
        });
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
                else if(method.isAnnotationPresent(RequestMapping.class)){
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    services.put(annotation.value(), method);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String transFormPath( String path ){
        return path.replace(".java", "").replace("\\",".").replace("..src.main.java.","");
    }

    private List<String> searchComponentList(File file) {
        List<String> componentList = new ArrayList();
        if( file.isDirectory()){
            for( File source : file.listFiles()){
                componentList.addAll( searchComponentList(source));
            }
        }
        else{
            if( file.getName().endsWith("java") ){
                String path = transFormPath(file.getPath());
                Class c = null;
                try {
                    c = Class.forName(path);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (c.isAnnotationPresent(Component.class) ){
                    componentList.add(path);
                }
            }
        }
        return componentList;
    }

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
