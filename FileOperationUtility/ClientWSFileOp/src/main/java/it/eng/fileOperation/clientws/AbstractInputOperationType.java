/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.fileOperation.clientws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per AbstractInputOperationType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="AbstractInputOperationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="breakOnError" type="{http://www.w3.org/2001/XMLSchema}boolean" form="qualified"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractInputOperationType", namespace = "it.eng.fileoperation.ws.base", propOrder = {
    "breakOnError"
})
@XmlSeeAlso({
    InputTimbroType.class,
    InputCopiaConformeType.class,
    InputPreparaFirmaPadesType.class,
    InputCompletaFirmaPadesType.class,
    InputFormatRecognitionType.class,
    InputConversionType.class,
    InputUnpackMultipartType.class,
    InputFileCompressType.class,
    InputCodeDetectorType.class,
    InputUnpackType.class,
    InputSigVerifyType.class,
    InputDigestType.class,
    InputRapportoVerificaType.class
})
public class AbstractInputOperationType {

    @XmlElement(defaultValue = "false")
    protected boolean breakOnError;

    /**
     * Recupera il valore della proprietà breakOnError.
     * 
     */
    public boolean isBreakOnError() {
        return breakOnError;
    }

    /**
     * Imposta il valore della proprietà breakOnError.
     * 
     */
    public void setBreakOnError(boolean value) {
        this.breakOnError = value;
    }

}
