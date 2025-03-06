package it.eng.core.service.server;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class IrisApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<Class<?>>();
        // register resources
        classes.add(ServiceRestStore.class);
        classes.add(ServiceExceptionMapper.class);
        return classes;
    }

}