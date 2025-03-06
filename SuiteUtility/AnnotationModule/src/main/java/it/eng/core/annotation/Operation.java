package it.eng.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation che permette di esporre i metodi della classe annotata con Service 
 * 
 * @author michele
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Operation {

	/**
	 * Nome del metodo da esporre
	 * @return
	 */
	public String name();
	
}
