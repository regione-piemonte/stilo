/**
 * ProdEffZooTipoStab2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTipoStab2  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idTs2;

    private com.hyperborea.sira.ws.ProdEffZooCatAnimali2 prodEffZooCatAnimali2;

    public ProdEffZooTipoStab2() {
    }

    public ProdEffZooTipoStab2(
           java.lang.String descrizione,
           java.lang.Integer idTs2,
           com.hyperborea.sira.ws.ProdEffZooCatAnimali2 prodEffZooCatAnimali2) {
        this.descrizione = descrizione;
        this.idTs2 = idTs2;
        this.prodEffZooCatAnimali2 = prodEffZooCatAnimali2;
    }


    /**
     * Gets the descrizione value for this ProdEffZooTipoStab2.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ProdEffZooTipoStab2.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idTs2 value for this ProdEffZooTipoStab2.
     * 
     * @return idTs2
     */
    public java.lang.Integer getIdTs2() {
        return idTs2;
    }


    /**
     * Sets the idTs2 value for this ProdEffZooTipoStab2.
     * 
     * @param idTs2
     */
    public void setIdTs2(java.lang.Integer idTs2) {
        this.idTs2 = idTs2;
    }


    /**
     * Gets the prodEffZooCatAnimali2 value for this ProdEffZooTipoStab2.
     * 
     * @return prodEffZooCatAnimali2
     */
    public com.hyperborea.sira.ws.ProdEffZooCatAnimali2 getProdEffZooCatAnimali2() {
        return prodEffZooCatAnimali2;
    }


    /**
     * Sets the prodEffZooCatAnimali2 value for this ProdEffZooTipoStab2.
     * 
     * @param prodEffZooCatAnimali2
     */
    public void setProdEffZooCatAnimali2(com.hyperborea.sira.ws.ProdEffZooCatAnimali2 prodEffZooCatAnimali2) {
        this.prodEffZooCatAnimali2 = prodEffZooCatAnimali2;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTipoStab2)) return false;
        ProdEffZooTipoStab2 other = (ProdEffZooTipoStab2) obj;
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
            ((this.idTs2==null && other.getIdTs2()==null) || 
             (this.idTs2!=null &&
              this.idTs2.equals(other.getIdTs2()))) &&
            ((this.prodEffZooCatAnimali2==null && other.getProdEffZooCatAnimali2()==null) || 
             (this.prodEffZooCatAnimali2!=null &&
              this.prodEffZooCatAnimali2.equals(other.getProdEffZooCatAnimali2())));
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
        if (getIdTs2() != null) {
            _hashCode += getIdTs2().hashCode();
        }
        if (getProdEffZooCatAnimali2() != null) {
            _hashCode += getProdEffZooCatAnimali2().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTipoStab2.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTipoStab2"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTs2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTs2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooCatAnimali2");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooCatAnimali2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooCatAnimali2"));
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
