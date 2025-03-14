
package it.doqui.acta.acaris.management;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.3.2
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "acarisFault", targetNamespace = "common.acaris.acta.doqui.it")
public class AcarisException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private AcarisFaultType faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public AcarisException(String message, AcarisFaultType faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public AcarisException(String message, AcarisFaultType faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: it.doqui.acta.acaris.management.AcarisFaultType
     */
    public AcarisFaultType getFaultInfo() {
        return faultInfo;
    }

}
