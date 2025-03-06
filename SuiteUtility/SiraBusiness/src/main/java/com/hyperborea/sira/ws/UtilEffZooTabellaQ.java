/**
 * UtilEffZooTabellaQ.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UtilEffZooTabellaQ  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Float distanza;

    private java.lang.Integer idTabellaQ;

    private int numPercorso;

    public UtilEffZooTabellaQ() {
    }

    public UtilEffZooTabellaQ(
           java.lang.Float distanza,
           java.lang.Integer idTabellaQ,
           int numPercorso) {
        this.distanza = distanza;
        this.idTabellaQ = idTabellaQ;
        this.numPercorso = numPercorso;
    }


    /**
     * Gets the distanza value for this UtilEffZooTabellaQ.
     * 
     * @return distanza
     */
    public java.lang.Float getDistanza() {
        return distanza;
    }


    /**
     * Sets the distanza value for this UtilEffZooTabellaQ.
     * 
     * @param distanza
     */
    public void setDistanza(java.lang.Float distanza) {
        this.distanza = distanza;
    }


    /**
     * Gets the idTabellaQ value for this UtilEffZooTabellaQ.
     * 
     * @return idTabellaQ
     */
    public java.lang.Integer getIdTabellaQ() {
        return idTabellaQ;
    }


    /**
     * Sets the idTabellaQ value for this UtilEffZooTabellaQ.
     * 
     * @param idTabellaQ
     */
    public void setIdTabellaQ(java.lang.Integer idTabellaQ) {
        this.idTabellaQ = idTabellaQ;
    }


    /**
     * Gets the numPercorso value for this UtilEffZooTabellaQ.
     * 
     * @return numPercorso
     */
    public int getNumPercorso() {
        return numPercorso;
    }


    /**
     * Sets the numPercorso value for this UtilEffZooTabellaQ.
     * 
     * @param numPercorso
     */
    public void setNumPercorso(int numPercorso) {
        this.numPercorso = numPercorso;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtilEffZooTabellaQ)) return false;
        UtilEffZooTabellaQ other = (UtilEffZooTabellaQ) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.distanza==null && other.getDistanza()==null) || 
             (this.distanza!=null &&
              this.distanza.equals(other.getDistanza()))) &&
            ((this.idTabellaQ==null && other.getIdTabellaQ()==null) || 
             (this.idTabellaQ!=null &&
              this.idTabellaQ.equals(other.getIdTabellaQ()))) &&
            this.numPercorso == other.getNumPercorso();
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
        if (getDistanza() != null) {
            _hashCode += getDistanza().hashCode();
        }
        if (getIdTabellaQ() != null) {
            _hashCode += getIdTabellaQ().hashCode();
        }
        _hashCode += getNumPercorso();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtilEffZooTabellaQ.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaQ"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distanza");
        elemField.setXmlName(new javax.xml.namespace.QName("", "distanza"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaQ");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaQ"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numPercorso");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numPercorso"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
