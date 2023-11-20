/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

public class CollaborationException extends java.lang.Exception 
{
    int iCodice=0;
    String iContext = "";


    /**
     * Creates a new instance of ObjectHandlerException
     * @param message   = messaggio
     */
    public CollaborationException(String message) {
        super(message);
    }

    /**
     *
     * @param innerCode = custom Code
     * @param message   = messaggio
     */
    public CollaborationException(int innerCode, String message) {
        this(message);
        this.iCodice = innerCode;
    }
    
    /**
    *
    * @param innerCode = custom Code
    * @param message   = messaggio
    */
   public CollaborationException(int innerCode, String message, String context) {
       this(message);
       this.iCodice = innerCode;
       this.iContext = context;
   }

    public int getCodice(){
        return this.iCodice;
    }
    
    public String getContext(){
        return this.iContext;
    }

}

