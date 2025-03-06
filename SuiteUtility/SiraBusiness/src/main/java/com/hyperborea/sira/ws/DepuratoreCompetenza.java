/**
 * DepuratoreCompetenza.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DepuratoreCompetenza  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ComuniItalia comuneSituazioneAttuale;

    private java.lang.Integer idDepCompetenza;

    private java.lang.String zona;

    public DepuratoreCompetenza() {
    }

    public DepuratoreCompetenza(
           com.hyperborea.sira.ws.ComuniItalia comuneSituazioneAttuale,
           java.lang.Integer idDepCompetenza,
           java.lang.String zona) {
        this.comuneSituazioneAttuale = comuneSituazioneAttuale;
        this.idDepCompetenza = idDepCompetenza;
        this.zona = zona;
    }


    /**
     * Gets the comuneSituazioneAttuale value for this DepuratoreCompetenza.
     * 
     * @return comuneSituazioneAttuale
     */
    public com.hyperborea.sira.ws.ComuniItalia getComuneSituazioneAttuale() {
        return comuneSituazioneAttuale;
    }


    /**
     * Sets the comuneSituazioneAttuale value for this DepuratoreCompetenza.
     * 
     * @param comuneSituazioneAttuale
     */
    public void setComuneSituazioneAttuale(com.hyperborea.sira.ws.ComuniItalia comuneSituazioneAttuale) {
        this.comuneSituazioneAttuale = comuneSituazioneAttuale;
    }


    /**
     * Gets the idDepCompetenza value for this DepuratoreCompetenza.
     * 
     * @return idDepCompetenza
     */
    public java.lang.Integer getIdDepCompetenza() {
        return idDepCompetenza;
    }


    /**
     * Sets the idDepCompetenza value for this DepuratoreCompetenza.
     * 
     * @param idDepCompetenza
     */
    public void setIdDepCompetenza(java.lang.Integer idDepCompetenza) {
        this.idDepCompetenza = idDepCompetenza;
    }


    /**
     * Gets the zona value for this DepuratoreCompetenza.
     * 
     * @return zona
     */
    public java.lang.String getZona() {
        return zona;
    }


    /**
     * Sets the zona value for this DepuratoreCompetenza.
     * 
     * @param zona
     */
    public void setZona(java.lang.String zona) {
        this.zona = zona;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DepuratoreCompetenza)) return false;
        DepuratoreCompetenza other = (DepuratoreCompetenza) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.comuneSituazioneAttuale==null && other.getComuneSituazioneAttuale()==null) || 
             (this.comuneSituazioneAttuale!=null &&
              this.comuneSituazioneAttuale.equals(other.getComuneSituazioneAttuale()))) &&
            ((this.idDepCompetenza==null && other.getIdDepCompetenza()==null) || 
             (this.idDepCompetenza!=null &&
              this.idDepCompetenza.equals(other.getIdDepCompetenza()))) &&
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
        if (getComuneSituazioneAttuale() != null) {
            _hashCode += getComuneSituazioneAttuale().hashCode();
        }
        if (getIdDepCompetenza() != null) {
            _hashCode += getIdDepCompetenza().hashCode();
        }
        if (getZona() != null) {
            _hashCode += getZona().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DepuratoreCompetenza.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "depuratoreCompetenza"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("comuneSituazioneAttuale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "comuneSituazioneAttuale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "comuniItalia"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDepCompetenza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDepCompetenza"));
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
