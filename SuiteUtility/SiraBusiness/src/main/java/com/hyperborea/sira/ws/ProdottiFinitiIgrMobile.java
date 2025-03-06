/**
 * ProdottiFinitiIgrMobile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdottiFinitiIgrMobile  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String carattMerceologiche;

    private java.lang.String destinazioneProdotti;

    private java.lang.Integer idProdottiFinitiIgrMobile;

    public ProdottiFinitiIgrMobile() {
    }

    public ProdottiFinitiIgrMobile(
           java.lang.String carattMerceologiche,
           java.lang.String destinazioneProdotti,
           java.lang.Integer idProdottiFinitiIgrMobile) {
        this.carattMerceologiche = carattMerceologiche;
        this.destinazioneProdotti = destinazioneProdotti;
        this.idProdottiFinitiIgrMobile = idProdottiFinitiIgrMobile;
    }


    /**
     * Gets the carattMerceologiche value for this ProdottiFinitiIgrMobile.
     * 
     * @return carattMerceologiche
     */
    public java.lang.String getCarattMerceologiche() {
        return carattMerceologiche;
    }


    /**
     * Sets the carattMerceologiche value for this ProdottiFinitiIgrMobile.
     * 
     * @param carattMerceologiche
     */
    public void setCarattMerceologiche(java.lang.String carattMerceologiche) {
        this.carattMerceologiche = carattMerceologiche;
    }


    /**
     * Gets the destinazioneProdotti value for this ProdottiFinitiIgrMobile.
     * 
     * @return destinazioneProdotti
     */
    public java.lang.String getDestinazioneProdotti() {
        return destinazioneProdotti;
    }


    /**
     * Sets the destinazioneProdotti value for this ProdottiFinitiIgrMobile.
     * 
     * @param destinazioneProdotti
     */
    public void setDestinazioneProdotti(java.lang.String destinazioneProdotti) {
        this.destinazioneProdotti = destinazioneProdotti;
    }


    /**
     * Gets the idProdottiFinitiIgrMobile value for this ProdottiFinitiIgrMobile.
     * 
     * @return idProdottiFinitiIgrMobile
     */
    public java.lang.Integer getIdProdottiFinitiIgrMobile() {
        return idProdottiFinitiIgrMobile;
    }


    /**
     * Sets the idProdottiFinitiIgrMobile value for this ProdottiFinitiIgrMobile.
     * 
     * @param idProdottiFinitiIgrMobile
     */
    public void setIdProdottiFinitiIgrMobile(java.lang.Integer idProdottiFinitiIgrMobile) {
        this.idProdottiFinitiIgrMobile = idProdottiFinitiIgrMobile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdottiFinitiIgrMobile)) return false;
        ProdottiFinitiIgrMobile other = (ProdottiFinitiIgrMobile) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.carattMerceologiche==null && other.getCarattMerceologiche()==null) || 
             (this.carattMerceologiche!=null &&
              this.carattMerceologiche.equals(other.getCarattMerceologiche()))) &&
            ((this.destinazioneProdotti==null && other.getDestinazioneProdotti()==null) || 
             (this.destinazioneProdotti!=null &&
              this.destinazioneProdotti.equals(other.getDestinazioneProdotti()))) &&
            ((this.idProdottiFinitiIgrMobile==null && other.getIdProdottiFinitiIgrMobile()==null) || 
             (this.idProdottiFinitiIgrMobile!=null &&
              this.idProdottiFinitiIgrMobile.equals(other.getIdProdottiFinitiIgrMobile())));
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
        if (getCarattMerceologiche() != null) {
            _hashCode += getCarattMerceologiche().hashCode();
        }
        if (getDestinazioneProdotti() != null) {
            _hashCode += getDestinazioneProdotti().hashCode();
        }
        if (getIdProdottiFinitiIgrMobile() != null) {
            _hashCode += getIdProdottiFinitiIgrMobile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdottiFinitiIgrMobile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodottiFinitiIgrMobile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carattMerceologiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "carattMerceologiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinazioneProdotti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "destinazioneProdotti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idProdottiFinitiIgrMobile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idProdottiFinitiIgrMobile"));
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
