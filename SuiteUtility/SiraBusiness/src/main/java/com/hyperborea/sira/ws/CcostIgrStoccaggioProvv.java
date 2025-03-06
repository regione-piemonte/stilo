/**
 * CcostIgrStoccaggioProvv.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostIgrStoccaggioProvv  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private java.lang.String sistemiStoccaggio;

    private java.lang.Float superficiePavimentata;

    private java.lang.String trattamentoAcque;

    public CcostIgrStoccaggioProvv() {
    }

    public CcostIgrStoccaggioProvv(
           java.lang.Integer idCcost,
           java.lang.String sistemiStoccaggio,
           java.lang.Float superficiePavimentata,
           java.lang.String trattamentoAcque) {
           this.idCcost = idCcost;
           this.sistemiStoccaggio = sistemiStoccaggio;
           this.superficiePavimentata = superficiePavimentata;
           this.trattamentoAcque = trattamentoAcque;
    }


    /**
     * Gets the idCcost value for this CcostIgrStoccaggioProvv.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostIgrStoccaggioProvv.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the sistemiStoccaggio value for this CcostIgrStoccaggioProvv.
     * 
     * @return sistemiStoccaggio
     */
    public java.lang.String getSistemiStoccaggio() {
        return sistemiStoccaggio;
    }


    /**
     * Sets the sistemiStoccaggio value for this CcostIgrStoccaggioProvv.
     * 
     * @param sistemiStoccaggio
     */
    public void setSistemiStoccaggio(java.lang.String sistemiStoccaggio) {
        this.sistemiStoccaggio = sistemiStoccaggio;
    }


    /**
     * Gets the superficiePavimentata value for this CcostIgrStoccaggioProvv.
     * 
     * @return superficiePavimentata
     */
    public java.lang.Float getSuperficiePavimentata() {
        return superficiePavimentata;
    }


    /**
     * Sets the superficiePavimentata value for this CcostIgrStoccaggioProvv.
     * 
     * @param superficiePavimentata
     */
    public void setSuperficiePavimentata(java.lang.Float superficiePavimentata) {
        this.superficiePavimentata = superficiePavimentata;
    }


    /**
     * Gets the trattamentoAcque value for this CcostIgrStoccaggioProvv.
     * 
     * @return trattamentoAcque
     */
    public java.lang.String getTrattamentoAcque() {
        return trattamentoAcque;
    }


    /**
     * Sets the trattamentoAcque value for this CcostIgrStoccaggioProvv.
     * 
     * @param trattamentoAcque
     */
    public void setTrattamentoAcque(java.lang.String trattamentoAcque) {
        this.trattamentoAcque = trattamentoAcque;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostIgrStoccaggioProvv)) return false;
        CcostIgrStoccaggioProvv other = (CcostIgrStoccaggioProvv) obj;
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
            ((this.sistemiStoccaggio==null && other.getSistemiStoccaggio()==null) || 
             (this.sistemiStoccaggio!=null &&
              this.sistemiStoccaggio.equals(other.getSistemiStoccaggio()))) &&
            ((this.superficiePavimentata==null && other.getSuperficiePavimentata()==null) || 
             (this.superficiePavimentata!=null &&
              this.superficiePavimentata.equals(other.getSuperficiePavimentata()))) &&
            ((this.trattamentoAcque==null && other.getTrattamentoAcque()==null) || 
             (this.trattamentoAcque!=null &&
              this.trattamentoAcque.equals(other.getTrattamentoAcque())));
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
        if (getSistemiStoccaggio() != null) {
            _hashCode += getSistemiStoccaggio().hashCode();
        }
        if (getSuperficiePavimentata() != null) {
            _hashCode += getSuperficiePavimentata().hashCode();
        }
        if (getTrattamentoAcque() != null) {
            _hashCode += getTrattamentoAcque().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostIgrStoccaggioProvv.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostIgrStoccaggioProvv"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sistemiStoccaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sistemiStoccaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficiePavimentata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficiePavimentata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trattamentoAcque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "trattamentoAcque"));
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
