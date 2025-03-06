/**
 * Opdl152PuntodmId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Opdl152PuntodmId  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String codOpDl152;

    private java.lang.Integer codPdm;

    private java.lang.String codSubattivita;

    public Opdl152PuntodmId() {
    }

    public Opdl152PuntodmId(
           java.lang.String codOpDl152,
           java.lang.Integer codPdm,
           java.lang.String codSubattivita) {
        this.codOpDl152 = codOpDl152;
        this.codPdm = codPdm;
        this.codSubattivita = codSubattivita;
    }


    /**
     * Gets the codOpDl152 value for this Opdl152PuntodmId.
     * 
     * @return codOpDl152
     */
    public java.lang.String getCodOpDl152() {
        return codOpDl152;
    }


    /**
     * Sets the codOpDl152 value for this Opdl152PuntodmId.
     * 
     * @param codOpDl152
     */
    public void setCodOpDl152(java.lang.String codOpDl152) {
        this.codOpDl152 = codOpDl152;
    }


    /**
     * Gets the codPdm value for this Opdl152PuntodmId.
     * 
     * @return codPdm
     */
    public java.lang.Integer getCodPdm() {
        return codPdm;
    }


    /**
     * Sets the codPdm value for this Opdl152PuntodmId.
     * 
     * @param codPdm
     */
    public void setCodPdm(java.lang.Integer codPdm) {
        this.codPdm = codPdm;
    }


    /**
     * Gets the codSubattivita value for this Opdl152PuntodmId.
     * 
     * @return codSubattivita
     */
    public java.lang.String getCodSubattivita() {
        return codSubattivita;
    }


    /**
     * Sets the codSubattivita value for this Opdl152PuntodmId.
     * 
     * @param codSubattivita
     */
    public void setCodSubattivita(java.lang.String codSubattivita) {
        this.codSubattivita = codSubattivita;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Opdl152PuntodmId)) return false;
        Opdl152PuntodmId other = (Opdl152PuntodmId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codOpDl152==null && other.getCodOpDl152()==null) || 
             (this.codOpDl152!=null &&
              this.codOpDl152.equals(other.getCodOpDl152()))) &&
            ((this.codPdm==null && other.getCodPdm()==null) || 
             (this.codPdm!=null &&
              this.codPdm.equals(other.getCodPdm()))) &&
            ((this.codSubattivita==null && other.getCodSubattivita()==null) || 
             (this.codSubattivita!=null &&
              this.codSubattivita.equals(other.getCodSubattivita())));
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
        if (getCodOpDl152() != null) {
            _hashCode += getCodOpDl152().hashCode();
        }
        if (getCodPdm() != null) {
            _hashCode += getCodPdm().hashCode();
        }
        if (getCodSubattivita() != null) {
            _hashCode += getCodSubattivita().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Opdl152PuntodmId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "opdl152PuntodmId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codOpDl152");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codOpDl152"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codPdm");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codPdm"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codSubattivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codSubattivita"));
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
