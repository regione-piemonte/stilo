/**
 * CcostUnitaTecnica.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostUnitaTecnica  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private com.hyperborea.sira.ws.VocModalita modalitaDetenzione;

    private com.hyperborea.sira.ws.VocTipologia tipologia;

    public CcostUnitaTecnica() {
    }

    public CcostUnitaTecnica(
           java.lang.Integer idCcost,
           com.hyperborea.sira.ws.VocModalita modalitaDetenzione,
           com.hyperborea.sira.ws.VocTipologia tipologia) {
           this.idCcost = idCcost;
           this.modalitaDetenzione = modalitaDetenzione;
           this.tipologia = tipologia;
    }


    /**
     * Gets the idCcost value for this CcostUnitaTecnica.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostUnitaTecnica.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the modalitaDetenzione value for this CcostUnitaTecnica.
     * 
     * @return modalitaDetenzione
     */
    public com.hyperborea.sira.ws.VocModalita getModalitaDetenzione() {
        return modalitaDetenzione;
    }


    /**
     * Sets the modalitaDetenzione value for this CcostUnitaTecnica.
     * 
     * @param modalitaDetenzione
     */
    public void setModalitaDetenzione(com.hyperborea.sira.ws.VocModalita modalitaDetenzione) {
        this.modalitaDetenzione = modalitaDetenzione;
    }


    /**
     * Gets the tipologia value for this CcostUnitaTecnica.
     * 
     * @return tipologia
     */
    public com.hyperborea.sira.ws.VocTipologia getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this CcostUnitaTecnica.
     * 
     * @param tipologia
     */
    public void setTipologia(com.hyperborea.sira.ws.VocTipologia tipologia) {
        this.tipologia = tipologia;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostUnitaTecnica)) return false;
        CcostUnitaTecnica other = (CcostUnitaTecnica) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.modalitaDetenzione==null && other.getModalitaDetenzione()==null) || 
             (this.modalitaDetenzione!=null &&
              this.modalitaDetenzione.equals(other.getModalitaDetenzione()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getModalitaDetenzione() != null) {
            _hashCode += getModalitaDetenzione().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostUnitaTecnica.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostUnitaTecnica"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modalitaDetenzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modalitaDetenzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocModalita"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologia"));
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
