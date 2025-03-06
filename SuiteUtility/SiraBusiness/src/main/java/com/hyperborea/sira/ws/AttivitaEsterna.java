/**
 * AttivitaEsterna.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AttivitaEsterna  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar data;

    private java.lang.String descrizione;

    private java.lang.Integer idAttivita;

    private com.hyperborea.sira.ws.WsFonteRef wsFonteRef;

    public AttivitaEsterna() {
    }

    public AttivitaEsterna(
           java.util.Calendar data,
           java.lang.String descrizione,
           java.lang.Integer idAttivita,
           com.hyperborea.sira.ws.WsFonteRef wsFonteRef) {
        this.data = data;
        this.descrizione = descrizione;
        this.idAttivita = idAttivita;
        this.wsFonteRef = wsFonteRef;
    }


    /**
     * Gets the data value for this AttivitaEsterna.
     * 
     * @return data
     */
    public java.util.Calendar getData() {
        return data;
    }


    /**
     * Sets the data value for this AttivitaEsterna.
     * 
     * @param data
     */
    public void setData(java.util.Calendar data) {
        this.data = data;
    }


    /**
     * Gets the descrizione value for this AttivitaEsterna.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this AttivitaEsterna.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idAttivita value for this AttivitaEsterna.
     * 
     * @return idAttivita
     */
    public java.lang.Integer getIdAttivita() {
        return idAttivita;
    }


    /**
     * Sets the idAttivita value for this AttivitaEsterna.
     * 
     * @param idAttivita
     */
    public void setIdAttivita(java.lang.Integer idAttivita) {
        this.idAttivita = idAttivita;
    }


    /**
     * Gets the wsFonteRef value for this AttivitaEsterna.
     * 
     * @return wsFonteRef
     */
    public com.hyperborea.sira.ws.WsFonteRef getWsFonteRef() {
        return wsFonteRef;
    }


    /**
     * Sets the wsFonteRef value for this AttivitaEsterna.
     * 
     * @param wsFonteRef
     */
    public void setWsFonteRef(com.hyperborea.sira.ws.WsFonteRef wsFonteRef) {
        this.wsFonteRef = wsFonteRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttivitaEsterna)) return false;
        AttivitaEsterna other = (AttivitaEsterna) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.data==null && other.getData()==null) || 
             (this.data!=null &&
              this.data.equals(other.getData()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idAttivita==null && other.getIdAttivita()==null) || 
             (this.idAttivita!=null &&
              this.idAttivita.equals(other.getIdAttivita()))) &&
            ((this.wsFonteRef==null && other.getWsFonteRef()==null) || 
             (this.wsFonteRef!=null &&
              this.wsFonteRef.equals(other.getWsFonteRef())));
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
        if (getData() != null) {
            _hashCode += getData().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdAttivita() != null) {
            _hashCode += getIdAttivita().hashCode();
        }
        if (getWsFonteRef() != null) {
            _hashCode += getWsFonteRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttivitaEsterna.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "attivitaEsterna"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("", "data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idAttivita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idAttivita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsFonteRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsFonteRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFonteRef"));
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
