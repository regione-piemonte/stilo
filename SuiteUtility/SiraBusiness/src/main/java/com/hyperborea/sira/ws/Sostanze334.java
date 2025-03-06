/**
 * Sostanze334.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Sostanze334  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idSostanza334;

    private java.lang.String nomeChimico;

    private java.lang.String nomeCommerciale;

    public Sostanze334() {
    }

    public Sostanze334(
           java.lang.Integer idSostanza334,
           java.lang.String nomeChimico,
           java.lang.String nomeCommerciale) {
        this.idSostanza334 = idSostanza334;
        this.nomeChimico = nomeChimico;
        this.nomeCommerciale = nomeCommerciale;
    }


    /**
     * Gets the idSostanza334 value for this Sostanze334.
     * 
     * @return idSostanza334
     */
    public java.lang.Integer getIdSostanza334() {
        return idSostanza334;
    }


    /**
     * Sets the idSostanza334 value for this Sostanze334.
     * 
     * @param idSostanza334
     */
    public void setIdSostanza334(java.lang.Integer idSostanza334) {
        this.idSostanza334 = idSostanza334;
    }


    /**
     * Gets the nomeChimico value for this Sostanze334.
     * 
     * @return nomeChimico
     */
    public java.lang.String getNomeChimico() {
        return nomeChimico;
    }


    /**
     * Sets the nomeChimico value for this Sostanze334.
     * 
     * @param nomeChimico
     */
    public void setNomeChimico(java.lang.String nomeChimico) {
        this.nomeChimico = nomeChimico;
    }


    /**
     * Gets the nomeCommerciale value for this Sostanze334.
     * 
     * @return nomeCommerciale
     */
    public java.lang.String getNomeCommerciale() {
        return nomeCommerciale;
    }


    /**
     * Sets the nomeCommerciale value for this Sostanze334.
     * 
     * @param nomeCommerciale
     */
    public void setNomeCommerciale(java.lang.String nomeCommerciale) {
        this.nomeCommerciale = nomeCommerciale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Sostanze334)) return false;
        Sostanze334 other = (Sostanze334) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idSostanza334==null && other.getIdSostanza334()==null) || 
             (this.idSostanza334!=null &&
              this.idSostanza334.equals(other.getIdSostanza334()))) &&
            ((this.nomeChimico==null && other.getNomeChimico()==null) || 
             (this.nomeChimico!=null &&
              this.nomeChimico.equals(other.getNomeChimico()))) &&
            ((this.nomeCommerciale==null && other.getNomeCommerciale()==null) || 
             (this.nomeCommerciale!=null &&
              this.nomeCommerciale.equals(other.getNomeCommerciale())));
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
        if (getIdSostanza334() != null) {
            _hashCode += getIdSostanza334().hashCode();
        }
        if (getNomeChimico() != null) {
            _hashCode += getNomeChimico().hashCode();
        }
        if (getNomeCommerciale() != null) {
            _hashCode += getNomeCommerciale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Sostanze334.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sostanze334"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idSostanza334");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idSostanza334"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeChimico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeChimico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nomeCommerciale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nomeCommerciale"));
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
