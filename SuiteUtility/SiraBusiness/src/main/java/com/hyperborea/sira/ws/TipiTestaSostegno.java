/**
 * TipiTestaSostegno.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipiTestaSostegno  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idTipoTestaSost;

    private java.lang.Integer nnCavi;

    private java.lang.Integer nnTerne;

    public TipiTestaSostegno() {
    }

    public TipiTestaSostegno(
           java.lang.String descrizione,
           java.lang.Integer idTipoTestaSost,
           java.lang.Integer nnCavi,
           java.lang.Integer nnTerne) {
        this.descrizione = descrizione;
        this.idTipoTestaSost = idTipoTestaSost;
        this.nnCavi = nnCavi;
        this.nnTerne = nnTerne;
    }


    /**
     * Gets the descrizione value for this TipiTestaSostegno.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this TipiTestaSostegno.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idTipoTestaSost value for this TipiTestaSostegno.
     * 
     * @return idTipoTestaSost
     */
    public java.lang.Integer getIdTipoTestaSost() {
        return idTipoTestaSost;
    }


    /**
     * Sets the idTipoTestaSost value for this TipiTestaSostegno.
     * 
     * @param idTipoTestaSost
     */
    public void setIdTipoTestaSost(java.lang.Integer idTipoTestaSost) {
        this.idTipoTestaSost = idTipoTestaSost;
    }


    /**
     * Gets the nnCavi value for this TipiTestaSostegno.
     * 
     * @return nnCavi
     */
    public java.lang.Integer getNnCavi() {
        return nnCavi;
    }


    /**
     * Sets the nnCavi value for this TipiTestaSostegno.
     * 
     * @param nnCavi
     */
    public void setNnCavi(java.lang.Integer nnCavi) {
        this.nnCavi = nnCavi;
    }


    /**
     * Gets the nnTerne value for this TipiTestaSostegno.
     * 
     * @return nnTerne
     */
    public java.lang.Integer getNnTerne() {
        return nnTerne;
    }


    /**
     * Sets the nnTerne value for this TipiTestaSostegno.
     * 
     * @param nnTerne
     */
    public void setNnTerne(java.lang.Integer nnTerne) {
        this.nnTerne = nnTerne;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipiTestaSostegno)) return false;
        TipiTestaSostegno other = (TipiTestaSostegno) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idTipoTestaSost==null && other.getIdTipoTestaSost()==null) || 
             (this.idTipoTestaSost!=null &&
              this.idTipoTestaSost.equals(other.getIdTipoTestaSost()))) &&
            ((this.nnCavi==null && other.getNnCavi()==null) || 
             (this.nnCavi!=null &&
              this.nnCavi.equals(other.getNnCavi()))) &&
            ((this.nnTerne==null && other.getNnTerne()==null) || 
             (this.nnTerne!=null &&
              this.nnTerne.equals(other.getNnTerne())));
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdTipoTestaSost() != null) {
            _hashCode += getIdTipoTestaSost().hashCode();
        }
        if (getNnCavi() != null) {
            _hashCode += getNnCavi().hashCode();
        }
        if (getNnTerne() != null) {
            _hashCode += getNnTerne().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipiTestaSostegno.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTestaSostegno"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoTestaSost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoTestaSost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nnCavi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nnCavi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nnTerne");
        elemField.setXmlName(new javax.xml.namespace.QName("", "nnTerne"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
