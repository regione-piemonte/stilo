/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation che permette l'esposizione di una classe di metodi come servizio per i moduli di IRIS
 * 
 * @author michele
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {

	/**
	 * Nome del servizio da esporre
	 * @return
	 */
	public String name();
	
}
