/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
 * VersionHandlerException.java
 *
 * Created on 3 febbraio 2005, 17.32
 */

package it.eng.auriga.repository2.generic;

/**
 *
 * @author  Administrator
 */
public class VersionHandlerException extends java.lang.Exception {
    int iCodice=0;


    /**
     * Creates a new instance of ObjectHandlerException
     * @param message   = messaggio
     */
    public VersionHandlerException(String message) {
        super(message);
    }

    /**
     *
     * @param innerCode = custom Code
     * @param message   = messaggio
     */
    public VersionHandlerException(int innerCode, String message) {
        super(message);
        this.iCodice = innerCode;
    }

    public int getCodice(){
        return this.iCodice;
    }

}

