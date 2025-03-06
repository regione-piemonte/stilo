package it.eng.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation che permette l'esposizione di una classe Dao generica
 * 
 * @author stefano
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenericDaoService {


	public String bean();
	
	public String entity();

	
}
