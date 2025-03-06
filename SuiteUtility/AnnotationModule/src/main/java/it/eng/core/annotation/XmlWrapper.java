package it.eng.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation che definisce un wrapper per la classe indicata nell'attributo forClass.
 * @see {@link WrapperManager} 
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlWrapper {
	
	String forClass();
 
	
}
