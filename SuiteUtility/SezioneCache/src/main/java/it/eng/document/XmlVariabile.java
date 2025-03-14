package it.eng.document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlVariabile {

	public enum TipoVariabile { SEMPLICE, NESTED, LISTA}
	public TipoVariabile tipo();
	public String nome();
	
}
