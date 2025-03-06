package it.eng.core.service.bean;

import it.eng.core.annotation.Module;
import it.eng.core.annotation.Operation;
import it.eng.core.annotation.Service;
import it.eng.core.config.ConfigUtil;
import it.eng.core.service.serialization.impl.XmlServiceSerialize;

import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

/**
 *  
 * @author Russo
 *
 */
public class MetadataRegistry {
	private static  Logger log = Logger.getLogger(MetadataRegistry.class);
	
	
	//reflections
	private Reflections reflections = null;
	//elenco dei servizi
	private Set<Class<?>> services;
	//elenco classi jaxb
	private Set<Class<?>> jaxbbeans;
	//elenco dei moduli
	private Set<Class<?>> moduli;
	// other custom class added by modules
	private Map<String,Set<Class<?>>> customClasses;
	//mappa dei moduli
	private Map<String,Class<?>> moduleMap ;
	//mappa dei servizi
	private Map<String,Class<?>> serviceMap;
	private static MetadataRegistry istance;
	
	private MetadataRegistry() {
	}
	
	public static synchronized MetadataRegistry getInstance() throws Exception{
		if(istance==null){
			
			istance=new MetadataRegistry();
			istance.customClasses = Collections.synchronizedMap(new HashMap<String, Set<Class<?>>>());
			istance.moduleMap = Collections.synchronizedMap(new HashMap<String,Class<?>>());
			istance.serviceMap=Collections.synchronizedMap(new HashMap<String,Class<?>>());
			istance.init();
		}
		return istance;
	}
 
	 private synchronized void init() throws Exception{
		 log.info("init ServiceRegistry......");
		 //set reflections
		 istance.setReflections(new Reflections(ConfigUtil.getServicePackage()) );
		 //set jaxbbeans
	     istance.setJaxbbeans(istance.getReflections().getTypesAnnotatedWith(XmlRootElement.class));
		 //set services
		 istance.setServices(istance.getReflections().getTypesAnnotatedWith(Service.class));
		 //set moduli TODO potresti non trovare il modulo se è in un package diverso
		 istance.setModuli(istance.getReflections().getTypesAnnotatedWith(Module.class));
		 istance.validate();
		 log.info("ServiceRegistry initialized");
	 }
	 
	public void validate()throws Exception{
		log.debug("start validate Registry..");
		log.debug("validating "+istance.getModuli().size()+"  modules");
		 
		for (Class<?> class1 : istance.getModuli()) {
			Module modulo=class1.getAnnotation(Module.class);
			if(moduleMap.containsKey(modulo.name())){
				throw new RuntimeException("duplicate module name "+modulo.name()+" available in \n"+
						moduleMap.get(modulo.name()) +"and in \n" +
						class1);
			}else{
				moduleMap.put(modulo.name(), class1);
			}
		}
		
		log.debug("modules validation ok");
		log.debug("validating "+istance.getServices().size()+" services");
		 
		for (Class<?> class1 : istance.getServices()) {
			Service service=class1.getAnnotation(Service.class);
			if(istance.serviceMap.containsKey(service.name())){
				throw new RuntimeException("duplicate module name "+service.name()+" available in \n"+
						istance.serviceMap.get(service.name()) +"and in \n" +
						class1);
			}else{
				//controllo che nella stessa classe non ci siano due operation uguali
				List<String> operations = new ArrayList<String>();
			    final Method[] declaredMethods = class1.getDeclaredMethods();
			    for (final Method method : declaredMethods)
			    {
			        if (method.isAnnotationPresent(Operation.class))
			        {
			           Operation op= method.getAnnotation(Operation.class);
			           if(operations.contains(op.name())){
			        	   throw new RuntimeException("duplicate operation "+op.name()+" on class "+class1);
			           }
			        }
			    }

			    istance.serviceMap.put(service.name(), class1);
				
			}
		}
		log.debug("service validation ok");
		
	}
	
	public Set<Class<?>> getServices() {
		return services;
	}

	public void setServices(Set<Class<?>> services) {
		this.services = services;
	}

	public Reflections getReflections() {
		return reflections;
	}

	public void setReflections(Reflections reflections) {
		this.reflections = reflections;
		 
	}

	public Set<Class<?>> getJaxbbeans() {
		return jaxbbeans;
	}

	public void setJaxbbeans(Set<Class<?>> jaxbbeans) {
		this.jaxbbeans = jaxbbeans;
	}

	public Set<Class<?>> getModuli() {
		return moduli;
	}

	public void setModuli(Set<Class<?>> moduli) {
		this.moduli = moduli;
	}
	
	public Map<String, Set<Class<?>>> getCustomClasses() {
		return customClasses;
	}
 
	public Map<String, Class<?>> getModuleMap() {
		return moduleMap;
	}
 
	public Map<String, Class<?>> getServiceMap() {
		return serviceMap;
	}

	  

	public void addClasses(String key,Set<Class<?>> classes){
		if(customClasses.containsKey(key)){
			log.warn("change key  "+key +" content will be replaced");
		}
		customClasses.put(key, classes);
		log.info("key "+key+" registered in Registry");
	}
	
	public Set<Class<?>> getClasses(String key){
		if(!customClasses.containsKey(key)){
			log.warn("key "+key+" not present in Registry");
		}
		return customClasses.get(key);
	}
	
	public Class<?> getService(String name){
		return getServiceMap().get(name);
	}
	
}
