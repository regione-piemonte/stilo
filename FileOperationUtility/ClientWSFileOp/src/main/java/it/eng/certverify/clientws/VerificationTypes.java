/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
package it.eng.certverify.clientws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VerificationTypes.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VerificationTypes">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="CERTIFICATE_EXPIRATION"/>
 *     &lt;enumeration value="CRL_VERIFY"/>
 *     &lt;enumeration value="CA_VERIFY"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VerificationTypes")
@XmlEnum
public enum VerificationTypes {

    CERTIFICATE_EXPIRATION,
    CRL_VERIFY,
    CA_VERIFY;

    public String value() {
        return name();
    }

    public static VerificationTypes fromValue(String v) {
        return valueOf(v);
    }

}
