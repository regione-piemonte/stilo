/**
 * DepuratoreCompetenzaPrra.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DepuratoreCompetenzaPrra  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ComuniItalia comunePianificazione;

    private java.lang.Integer idDepCompetenzaPrra;

    private java.lang.String zona;

    public DepuratoreCompetenzaPrra() {
    }

    public DepuratoreCompetenzaPrra(
           com.hyperborea.sira.ws.ComuniItalia comunePianificazione,
           java.lang.Integer idDepCompetenzaPrra,
           java.lang.String zona) {
        this.comunePianificazione = comunePianificazione;
        this.idDepCompetenzaPrra = idDepCompetenzaPrra;
        this.zona = zona;
    }


    /**
     * Gets the comunePianificazione value for this DepuratoreCompetenzaPrra.
     * 
     * @return comunePianificazione
     */
    public com.hyperborea.sira.ws.ComuniItalia getComunePianificazione() {
        return comunePianificazione;
    }


    /**
     * Sets the comunePianificazione value for this DepuratoreCompetenzaPrra.
     * 
     * @param comunePianificazione
     */
    public void setComunePianificazione(com.hyperborea.sira.ws.ComuniItalia comunePianificazione) {
        this.comunePianificazione = comunePianificazione;
    }


    /**
     * Gets the idDepCompetenzaPrra value for this DepuratoreCompetenzaPrra.
     * 
     * @return idDepCompetenzaPrra
     */
    public java.lang.Integer getIdDepCompetenzaPrra() {
        return idDepCompetenzaPrra;
    }


    /**
     * Sets the idDepCompetenzaPrra value for this DepuratoreCompetenzaPrra.
     * 
     * @param idDepCompetenzaPrra
     */
    public void setIdDepCompetenzaPrra(java.lang.Integer idDepCompetenzaPrra) {
        this.idDepCompetenzaPrra = idDepCompetenzaPrra;
    }


    /**
     * Gets the zona value for this DepuratoreCompetenzaPrra.
     * 
     * @return zona
     */
    public java.lang.String getZona() {
        return zona;
    }


    /**
     * Sets the zona value for this DepuratoreCompetenzaPrra.
     * 
     * @param zona
     */
    public void setZona(java.lang.String zona) {
        this.zona = zona;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepuratoreCompetenzaPrra)) return false;
        DepuratoreCompetenzaPrra other = (DepuratoreCompetenzaPrra) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.comunePianificazione==null && other.getComunePianificazione()==null) || 
             (this.comunePianificazione!=null &&
              this.comunePianificazione.equals(other.getComunePianificazione()))) &&
            ((this.idDepCompetenzaPrra==null && other.getIdDepCompetenzaPrra()==null) || 
             (this.idDepCompetenzaPrra!=null &&
              this.idDepCompetenzaPrra.equals(other.getIdDepCompetenzaPrra()))) &&
            ((this.zona==null && other.getZona()==null) || 
             (this.zona!=null &&
              this.zona.equals(other.getZona())));
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
        if (getComunePianificazione() != null) {
            _hashCode += getComunePianificazione().hashCode();
        }
        if (getIdDepCompetenzaPrra() != null) {
            _hashCode += getIdDepCompetenzaPrra().hashCode();
        }
        if (getZona() != null) {
            _hashCode += getZona().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepuratoreCompetenzaPrra.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreCompetenzaPrra"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comunePianificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comunePianificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItalia"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDepCompetenzaPrra");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDepCompetenzaPrra"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("zona");
        elemField.setXmlName(new javax.xml.namespace.QName("", "zona"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
