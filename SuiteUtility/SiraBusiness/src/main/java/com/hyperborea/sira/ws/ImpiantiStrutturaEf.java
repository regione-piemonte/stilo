/**
 * ImpiantiStrutturaEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ImpiantiStrutturaEf  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String certificazione;

    private java.lang.Integer idImpianto;

    private com.hyperborea.sira.ws.VocEfTipologiaImpianto vocEfTipologiaImpianto;

    public ImpiantiStrutturaEf() {
    }

    public ImpiantiStrutturaEf(
           java.lang.String certificazione,
           java.lang.Integer idImpianto,
           com.hyperborea.sira.ws.VocEfTipologiaImpianto vocEfTipologiaImpianto) {
        this.certificazione = certificazione;
        this.idImpianto = idImpianto;
        this.vocEfTipologiaImpianto = vocEfTipologiaImpianto;
    }


    /**
     * Gets the certificazione value for this ImpiantiStrutturaEf.
     * 
     * @return certificazione
     */
    public java.lang.String getCertificazione() {
        return certificazione;
    }


    /**
     * Sets the certificazione value for this ImpiantiStrutturaEf.
     * 
     * @param certificazione
     */
    public void setCertificazione(java.lang.String certificazione) {
        this.certificazione = certificazione;
    }


    /**
     * Gets the idImpianto value for this ImpiantiStrutturaEf.
     * 
     * @return idImpianto
     */
    public java.lang.Integer getIdImpianto() {
        return idImpianto;
    }


    /**
     * Sets the idImpianto value for this ImpiantiStrutturaEf.
     * 
     * @param idImpianto
     */
    public void setIdImpianto(java.lang.Integer idImpianto) {
        this.idImpianto = idImpianto;
    }


    /**
     * Gets the vocEfTipologiaImpianto value for this ImpiantiStrutturaEf.
     * 
     * @return vocEfTipologiaImpianto
     */
    public com.hyperborea.sira.ws.VocEfTipologiaImpianto getVocEfTipologiaImpianto() {
        return vocEfTipologiaImpianto;
    }


    /**
     * Sets the vocEfTipologiaImpianto value for this ImpiantiStrutturaEf.
     * 
     * @param vocEfTipologiaImpianto
     */
    public void setVocEfTipologiaImpianto(com.hyperborea.sira.ws.VocEfTipologiaImpianto vocEfTipologiaImpianto) {
        this.vocEfTipologiaImpianto = vocEfTipologiaImpianto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ImpiantiStrutturaEf)) return false;
        ImpiantiStrutturaEf other = (ImpiantiStrutturaEf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.certificazione==null && other.getCertificazione()==null) || 
             (this.certificazione!=null &&
              this.certificazione.equals(other.getCertificazione()))) &&
            ((this.idImpianto==null && other.getIdImpianto()==null) || 
             (this.idImpianto!=null &&
              this.idImpianto.equals(other.getIdImpianto()))) &&
            ((this.vocEfTipologiaImpianto==null && other.getVocEfTipologiaImpianto()==null) || 
             (this.vocEfTipologiaImpianto!=null &&
              this.vocEfTipologiaImpianto.equals(other.getVocEfTipologiaImpianto())));
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
        if (getCertificazione() != null) {
            _hashCode += getCertificazione().hashCode();
        }
        if (getIdImpianto() != null) {
            _hashCode += getIdImpianto().hashCode();
        }
        if (getVocEfTipologiaImpianto() != null) {
            _hashCode += getVocEfTipologiaImpianto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ImpiantiStrutturaEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "impiantiStrutturaEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("certificazione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "certificazione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idImpianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idImpianto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocEfTipologiaImpianto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocEfTipologiaImpianto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocEfTipologiaImpianto"));
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
