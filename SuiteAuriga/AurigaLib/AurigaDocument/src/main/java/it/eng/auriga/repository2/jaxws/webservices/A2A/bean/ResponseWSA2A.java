/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Modalita">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="ok"/>
 *               &lt;pattern value="msg"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IncrementoDal" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ok",
    "msg"
})
@XmlRootElement(name = "ResponseWSA2A")
public class ResponseWSA2A {
	
	@XmlElement(name = "ok", required = true)
    protected boolean ok;
	@XmlElement(name = "msg")
    protected String msg;
	
	 /**
     * Gets the value of the ok property.
     * 
     * @return
     *     possible object is
     *     {@link boolean }
     *     
     */
	public boolean isOk() {
		return ok;
	}
	
	/**
     * Sets the value of the ok property.
     * 
     * @param value
     *     allowed object is
     *     {@link boolean }
     *     
     */
	public void setOk(boolean ok) {
		this.ok = ok;
	}
	
	 /**
     * Gets the value of the msg property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
	public String getMsg() {
		return msg;
	}
	
	/**
     * Sets the value of the msg property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
