/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine.
// Generato il: 2018.06.06 alle 03:56:55 PM CEST
//

package it.eng.auriga.repository2.jaxws.jaxbBean.operazioniaurigalottidoc;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Classe Java per OperationType.
 * 
 * <p>
 * Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="OperationType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="FIRMA_AUTOMATICA"/>
 *     &lt;enumeration value="ARCHIVIAZIONE_DOC"/>
 *     &lt;enumeration value="INVIO_PEC"/>
 *     &lt;enumeration value="INVIO_PEO"/>
 *     &lt;enumeration value="ARCHIVIAZIONE_EMAIL"/>
 *     &lt;enumeration value="RITORNO_RICEVUTE_NOTIFICHE"/>
 *     &lt;enumeration value="AVANZAMENTO_TASK"/>
 *     &lt;enumeration value="INVIO_SUAP"/>  
 *     &lt;enumeration value="ELABORA_FATTURA"/>  
 *     &lt;enumeration value="ESTRAI_DOC_ORIGINALE"/>    
 *     &lt;enumeration value="INVIA_FATTURA"/>  
 *     &lt;enumeration value="AGGIORNA_METADATI_DOC"/>    
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "OperationType")
@XmlEnum
public enum OperationType {

	FIRMA_AUTOMATICA, ARCHIVIAZIONE_DOC, INVIO_PEC, INVIO_PEO, ARCHIVIAZIONE_EMAIL, RITORNO_RICEVUTE_NOTIFICHE, AVANZAMENTO_TASK, INVIO_SUAP, ELABORA_FATTURA, ESTRAI_DOC_ORIGINALE, INVIA_FATTURA, AGGIORNA_METADATI_DOC;

	public String value() {
		return name();
	}

	public static OperationType fromValue(String v) {
		return valueOf(v);
	}

}
