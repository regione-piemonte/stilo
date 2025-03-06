/**
 * ProdEffZooCatAnimali1.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooCatAnimali1  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idCa1;

    private com.hyperborea.sira.ws.ProdEffZooCatAnimali1Grp prodEffZooCatAnimali1Grp;

    public ProdEffZooCatAnimali1() {
    }

    public ProdEffZooCatAnimali1(
           java.lang.String descrizione,
           java.lang.Integer idCa1,
           com.hyperborea.sira.ws.ProdEffZooCatAnimali1Grp prodEffZooCatAnimali1Grp) {
        this.descrizione = descrizione;
        this.idCa1 = idCa1;
        this.prodEffZooCatAnimali1Grp = prodEffZooCatAnimali1Grp;
    }


    /**
     * Gets the descrizione value for this ProdEffZooCatAnimali1.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ProdEffZooCatAnimali1.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idCa1 value for this ProdEffZooCatAnimali1.
     * 
     * @return idCa1
     */
    public java.lang.Integer getIdCa1() {
        return idCa1;
    }


    /**
     * Sets the idCa1 value for this ProdEffZooCatAnimali1.
     * 
     * @param idCa1
     */
    public void setIdCa1(java.lang.Integer idCa1) {
        this.idCa1 = idCa1;
    }


    /**
     * Gets the prodEffZooCatAnimali1Grp value for this ProdEffZooCatAnimali1.
     * 
     * @return prodEffZooCatAnimali1Grp
     */
    public com.hyperborea.sira.ws.ProdEffZooCatAnimali1Grp getProdEffZooCatAnimali1Grp() {
        return prodEffZooCatAnimali1Grp;
    }


    /**
     * Sets the prodEffZooCatAnimali1Grp value for this ProdEffZooCatAnimali1.
     * 
     * @param prodEffZooCatAnimali1Grp
     */
    public void setProdEffZooCatAnimali1Grp(com.hyperborea.sira.ws.ProdEffZooCatAnimali1Grp prodEffZooCatAnimali1Grp) {
        this.prodEffZooCatAnimali1Grp = prodEffZooCatAnimali1Grp;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooCatAnimali1)) return false;
        ProdEffZooCatAnimali1 other = (ProdEffZooCatAnimali1) obj;
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
            ((this.idCa1==null && other.getIdCa1()==null) || 
             (this.idCa1!=null &&
              this.idCa1.equals(other.getIdCa1()))) &&
            ((this.prodEffZooCatAnimali1Grp==null && other.getProdEffZooCatAnimali1Grp()==null) || 
             (this.prodEffZooCatAnimali1Grp!=null &&
              this.prodEffZooCatAnimali1Grp.equals(other.getProdEffZooCatAnimali1Grp())));
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
        if (getIdCa1() != null) {
            _hashCode += getIdCa1().hashCode();
        }
        if (getProdEffZooCatAnimali1Grp() != null) {
            _hashCode += getProdEffZooCatAnimali1Grp().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooCatAnimali1.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooCatAnimali1"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCa1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCa1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("prodEffZooCatAnimali1Grp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "prodEffZooCatAnimali1Grp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooCatAnimali1Grp"));
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
