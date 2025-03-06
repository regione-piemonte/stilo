/**
 * MisureAgentifisici.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class MisureAgentifisici  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.Attivita attivita;

    private com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst;

    private java.lang.Integer idMisura;

    private com.hyperborea.sira.ws.Misure misure;

    public MisureAgentifisici() {
    }

    public MisureAgentifisici(
           com.hyperborea.sira.ws.Attivita attivita,
           com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst,
           java.lang.Integer idMisura,
           com.hyperborea.sira.ws.Misure misure) {
        this.attivita = attivita;
        this.caratterizzazioniOst = caratterizzazioniOst;
        this.idMisura = idMisura;
        this.misure = misure;
    }


    /**
     * Gets the attivita value for this MisureAgentifisici.
     * 
     * @return attivita
     */
    public com.hyperborea.sira.ws.Attivita getAttivita() {
        return attivita;
    }


    /**
     * Sets the attivita value for this MisureAgentifisici.
     * 
     * @param attivita
     */
    public void setAttivita(com.hyperborea.sira.ws.Attivita attivita) {
        this.attivita = attivita;
    }


    /**
     * Gets the caratterizzazioniOst value for this MisureAgentifisici.
     * 
     * @return caratterizzazioniOst
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOst() {
        return caratterizzazioniOst;
    }


    /**
     * Sets the caratterizzazioniOst value for this MisureAgentifisici.
     * 
     * @param caratterizzazioniOst
     */
    public void setCaratterizzazioniOst(com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst) {
        this.caratterizzazioniOst = caratterizzazioniOst;
    }


    /**
     * Gets the idMisura value for this MisureAgentifisici.
     * 
     * @return idMisura
     */
    public java.lang.Integer getIdMisura() {
        return idMisura;
    }


    /**
     * Sets the idMisura value for this MisureAgentifisici.
     * 
     * @param idMisura
     */
    public void setIdMisura(java.lang.Integer idMisura) {
        this.idMisura = idMisura;
    }


    /**
     * Gets the misure value for this MisureAgentifisici.
     * 
     * @return misure
     */
    public com.hyperborea.sira.ws.Misure getMisure() {
        return misure;
    }


    /**
     * Sets the misure value for this MisureAgentifisici.
     * 
     * @param misure
     */
    public void setMisure(com.hyperborea.sira.ws.Misure misure) {
        this.misure = misure;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MisureAgentifisici)) return false;
        MisureAgentifisici other = (MisureAgentifisici) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attivita==null && other.getAttivita()==null) || 
             (this.attivita!=null &&
              this.attivita.equals(other.getAttivita()))) &&
            ((this.caratterizzazioniOst==null && other.getCaratterizzazioniOst()==null) || 
             (this.caratterizzazioniOst!=null &&
              this.caratterizzazioniOst.equals(other.getCaratterizzazioniOst()))) &&
            ((this.idMisura==null && other.getIdMisura()==null) || 
             (this.idMisura!=null &&
              this.idMisura.equals(other.getIdMisura()))) &&
            ((this.misure==null && other.getMisure()==null) || 
             (this.misure!=null &&
              this.misure.equals(other.getMisure())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getAttivita() != null) {
            _hashCode += getAttivita().hashCode();
        }
        if (getCaratterizzazioniOst() != null) {
            _hashCode += getCaratterizzazioniOst().hashCode();
        }
        if (getIdMisura() != null) {
            _hashCode += getIdMisura().hashCode();
        }
        if (getMisure() != null) {
            _hashCode += getMisure().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MisureAgentifisici.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misureAgentifisici"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attivita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idMisura");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idMisura"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("misure");
        elemField.setXmlName(new javax.xml.namespace.QName("", "misure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "misure"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
