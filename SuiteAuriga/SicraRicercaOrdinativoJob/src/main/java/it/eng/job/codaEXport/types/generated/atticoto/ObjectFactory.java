/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.eng.job.codaEXport.types.generated.atticoto package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.eng.job.codaEXport.types.generated.atticoto
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Atto }
     * 
     */
    public Atto createAtto() {
        return new Atto();
    }

    /**
     * Create an instance of {@link Testo }
     * 
     */
    public Testo createTesto() {
        return new Testo();
    }

    /**
     * Create an instance of {@link Allegato }
     * 
     */
    public Allegato createAllegato() {
        return new Allegato();
    }

}
