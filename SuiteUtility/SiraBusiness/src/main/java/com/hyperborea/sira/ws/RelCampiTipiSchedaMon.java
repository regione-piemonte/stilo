/**
 * RelCampiTipiSchedaMon.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RelCampiTipiSchedaMon  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.RelCampiTipiSchedaMonPK id;

    private java.lang.String obbligatorio;

    public RelCampiTipiSchedaMon() {
    }

    public RelCampiTipiSchedaMon(
           com.hyperborea.sira.ws.RelCampiTipiSchedaMonPK id,
           java.lang.String obbligatorio) {
        this.id = id;
        this.obbligatorio = obbligatorio;
    }


    /**
     * Gets the id value for this RelCampiTipiSchedaMon.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.RelCampiTipiSchedaMonPK getId() {
        return id;
    }


    /**
     * Sets the id value for this RelCampiTipiSchedaMon.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.RelCampiTipiSchedaMonPK id) {
        this.id = id;
    }


    /**
     * Gets the obbligatorio value for this RelCampiTipiSchedaMon.
     * 
     * @return obbligatorio
     */
    public java.lang.String getObbligatorio() {
        return obbligatorio;
    }


    /**
     * Sets the obbligatorio value for this RelCampiTipiSchedaMon.
     * 
     * @param obbligatorio
     */
    public void setObbligatorio(java.lang.String obbligatorio) {
        this.obbligatorio = obbligatorio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RelCampiTipiSchedaMon)) return false;
        RelCampiTipiSchedaMon other = (RelCampiTipiSchedaMon) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.obbligatorio==null && other.getObbligatorio()==null) || 
             (this.obbligatorio!=null &&
              this.obbligatorio.equals(other.getObbligatorio())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getObbligatorio() != null) {
            _hashCode += getObbligatorio().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RelCampiTipiSchedaMon.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relCampiTipiSchedaMon"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relCampiTipiSchedaMonPK"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("obbligatorio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "obbligatorio"));
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
