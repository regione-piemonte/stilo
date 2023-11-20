/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *  @author Antonio Peluso
 */

public class AurigaRestServiceException extends WebApplicationException {

    /**
      * Create a HTTP 500 exception.
     */
     public AurigaRestServiceException() {
         super();
     }

     /**
      * Create a HTTP "status" exception.
      * @param message the String that is the entity of the "status" response.
      * @param status  the type status of HTTP Exception
      */
     public AurigaRestServiceException(String message, Status status) {
         super(Response.status(status).entity(message).type("text/plain").build());
     }

}