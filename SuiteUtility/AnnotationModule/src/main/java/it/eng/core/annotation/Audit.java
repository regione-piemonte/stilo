package it.eng.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation che identifica una prop cheil sistem di audit deve tracciare
 * 
 */
@Retention(value=RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Audit {
	
	/**
	 * Nome con cui fare l'audit della proprietà
	 * @return
	 */
	public String propName();

	public boolean target() default false;
	
}
