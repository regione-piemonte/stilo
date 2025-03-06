/**
 * TipiTrasmettitori.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TipiTrasmettitori  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String codCostruttore;

    private java.lang.String codModello;

    private java.lang.Integer idTipoTrasmettitore;

    private java.lang.Float trasmpotenzamaxW;

    public TipiTrasmettitori() {
    }

    public TipiTrasmettitori(
           java.lang.String codCostruttore,
           java.lang.String codModello,
           java.lang.Integer idTipoTrasmettitore,
           java.lang.Float trasmpotenzamaxW) {
        this.codCostruttore = codCostruttore;
        this.codModello = codModello;
        this.idTipoTrasmettitore = idTipoTrasmettitore;
        this.trasmpotenzamaxW = trasmpotenzamaxW;
    }


    /**
     * Gets the codCostruttore value for this TipiTrasmettitori.
     * 
     * @return codCostruttore
     */
    public java.lang.String getCodCostruttore() {
        return codCostruttore;
    }


    /**
     * Sets the codCostruttore value for this TipiTrasmettitori.
     * 
     * @param codCostruttore
     */
    public void setCodCostruttore(java.lang.String codCostruttore) {
        this.codCostruttore = codCostruttore;
    }


    /**
     * Gets the codModello value for this TipiTrasmettitori.
     * 
     * @return codModello
     */
    public java.lang.String getCodModello() {
        return codModello;
    }


    /**
     * Sets the codModello value for this TipiTrasmettitori.
     * 
     * @param codModello
     */
    public void setCodModello(java.lang.String codModello) {
        this.codModello = codModello;
    }


    /**
     * Gets the idTipoTrasmettitore value for this TipiTrasmettitori.
     * 
     * @return idTipoTrasmettitore
     */
    public java.lang.Integer getIdTipoTrasmettitore() {
        return idTipoTrasmettitore;
    }


    /**
     * Sets the idTipoTrasmettitore value for this TipiTrasmettitori.
     * 
     * @param idTipoTrasmettitore
     */
    public void setIdTipoTrasmettitore(java.lang.Integer idTipoTrasmettitore) {
        this.idTipoTrasmettitore = idTipoTrasmettitore;
    }


    /**
     * Gets the trasmpotenzamaxW value for this TipiTrasmettitori.
     * 
     * @return trasmpotenzamaxW
     */
    public java.lang.Float getTrasmpotenzamaxW() {
        return trasmpotenzamaxW;
    }


    /**
     * Sets the trasmpotenzamaxW value for this TipiTrasmettitori.
     * 
     * @param trasmpotenzamaxW
     */
    public void setTrasmpotenzamaxW(java.lang.Float trasmpotenzamaxW) {
        this.trasmpotenzamaxW = trasmpotenzamaxW;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TipiTrasmettitori)) return false;
        TipiTrasmettitori other = (TipiTrasmettitori) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codCostruttore==null && other.getCodCostruttore()==null) || 
             (this.codCostruttore!=null &&
              this.codCostruttore.equals(other.getCodCostruttore()))) &&
            ((this.codModello==null && other.getCodModello()==null) || 
             (this.codModello!=null &&
              this.codModello.equals(other.getCodModello()))) &&
            ((this.idTipoTrasmettitore==null && other.getIdTipoTrasmettitore()==null) || 
             (this.idTipoTrasmettitore!=null &&
              this.idTipoTrasmettitore.equals(other.getIdTipoTrasmettitore()))) &&
            ((this.trasmpotenzamaxW==null && other.getTrasmpotenzamaxW()==null) || 
             (this.trasmpotenzamaxW!=null &&
              this.trasmpotenzamaxW.equals(other.getTrasmpotenzamaxW())));
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
        if (getCodCostruttore() != null) {
            _hashCode += getCodCostruttore().hashCode();
        }
        if (getCodModello() != null) {
            _hashCode += getCodModello().hashCode();
        }
        if (getIdTipoTrasmettitore() != null) {
            _hashCode += getIdTipoTrasmettitore().hashCode();
        }
        if (getTrasmpotenzamaxW() != null) {
            _hashCode += getTrasmpotenzamaxW().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TipiTrasmettitori.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTrasmettitori"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codCostruttore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codCostruttore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codModello");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codModello"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoTrasmettitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoTrasmettitore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trasmpotenzamaxW");
        elemField.setXmlName(new javax.xml.namespace.QName("", "trasmpotenzamaxW"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
