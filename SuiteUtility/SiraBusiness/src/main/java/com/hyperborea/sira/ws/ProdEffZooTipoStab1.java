/**
 * ProdEffZooTipoStab1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTipoStab1  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idTs1;

    private com.hyperborea.sira.ws.ProdEffZooCatAnimali1 prodEffZooCatAnimali1;

    public ProdEffZooTipoStab1() {
    }

    public ProdEffZooTipoStab1(
           java.lang.String descrizione,
           java.lang.Integer idTs1,
           com.hyperborea.sira.ws.ProdEffZooCatAnimali1 prodEffZooCatAnimali1) {
        this.descrizione = descrizione;
        this.idTs1 = idTs1;
        this.prodEffZooCatAnimali1 = prodEffZooCatAnimali1;
    }


    /**
     * Gets the descrizione value for this ProdEffZooTipoStab1.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ProdEffZooTipoStab1.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idTs1 value for this ProdEffZooTipoStab1.
     * 
     * @return idTs1
     */
    public java.lang.Integer getIdTs1() {
        return idTs1;
    }


    /**
     * Sets the idTs1 value for this ProdEffZooTipoStab1.
     * 
     * @param idTs1
     */
    public void setIdTs1(java.lang.Integer idTs1) {
        this.idTs1 = idTs1;
    }


    /**
     * Gets the prodEffZooCatAnimali1 value for this ProdEffZooTipoStab1.
     * 
     * @return prodEffZooCatAnimali1
     */
    public com.hyperborea.sira.ws.ProdEffZooCatAnimali1 getProdEffZooCatAnimali1() {
        return prodEffZooCatAnimali1;
    }


    /**
     * Sets the prodEffZooCatAnimali1 value for this ProdEffZooTipoStab1.
     * 
     * @param prodEffZooCatAnimali1
     */
    public void setProdEffZooCatAnimali1(com.hyperborea.sira.ws.ProdEffZooCatAnimali1 prodEffZooCatAnimali1) {
        this.prodEffZooCatAnimali1 = prodEffZooCatAnimali1;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTipoStab1)) return false;
        ProdEffZooTipoStab1 other = (ProdEffZooTipoStab1) obj;
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
            ((this.idTs1==null && other.getIdTs1()==null) || 
             (this.idTs1!=null &&
              this.idTs1.equals(other.getIdTs1()))) &&
            ((this.prodEffZooCatAnimali1==null && other.getProdEffZooCatAnimali1()==null) || 
             (this.prodEffZooCatAnimali1!=null &&
              this.prodEffZooCatAnimali1.equals(other.getProdEffZooCatAnimali1())));
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
        if (getIdTs1() != null) {
            _hashCode += getIdTs1().hashCode();
        }
        if (getProdEffZooCatAnimali1() != null) {
            _hashCode += getProdEffZooCatAnimali1().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTipoStab1.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTipoStab1"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTs1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTs1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooCatAnimali1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooCatAnimali1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooCatAnimali1"));
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
