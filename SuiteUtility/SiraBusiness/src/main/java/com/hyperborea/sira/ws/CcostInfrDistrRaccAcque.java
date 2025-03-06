/**
 * CcostInfrDistrRaccAcque.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostInfrDistrRaccAcque  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostCollettoreFognario ccostCollettoreFognario;

    private com.hyperborea.sira.ws.CcostCondottaAcque ccostCondottaAcque;

    private com.hyperborea.sira.ws.CcostElementoAcquedottistico ccostElementoAcquedottistico;

    private java.lang.Integer idCcost;

    public CcostInfrDistrRaccAcque() {
    }

    public CcostInfrDistrRaccAcque(
           com.hyperborea.sira.ws.CcostCollettoreFognario ccostCollettoreFognario,
           com.hyperborea.sira.ws.CcostCondottaAcque ccostCondottaAcque,
           com.hyperborea.sira.ws.CcostElementoAcquedottistico ccostElementoAcquedottistico,
           java.lang.Integer idCcost) {
           this.ccostCollettoreFognario = ccostCollettoreFognario;
           this.ccostCondottaAcque = ccostCondottaAcque;
           this.ccostElementoAcquedottistico = ccostElementoAcquedottistico;
           this.idCcost = idCcost;
    }


    /**
     * Gets the ccostCollettoreFognario value for this CcostInfrDistrRaccAcque.
     * 
     * @return ccostCollettoreFognario
     */
    public com.hyperborea.sira.ws.CcostCollettoreFognario getCcostCollettoreFognario() {
        return ccostCollettoreFognario;
    }


    /**
     * Sets the ccostCollettoreFognario value for this CcostInfrDistrRaccAcque.
     * 
     * @param ccostCollettoreFognario
     */
    public void setCcostCollettoreFognario(com.hyperborea.sira.ws.CcostCollettoreFognario ccostCollettoreFognario) {
        this.ccostCollettoreFognario = ccostCollettoreFognario;
    }


    /**
     * Gets the ccostCondottaAcque value for this CcostInfrDistrRaccAcque.
     * 
     * @return ccostCondottaAcque
     */
    public com.hyperborea.sira.ws.CcostCondottaAcque getCcostCondottaAcque() {
        return ccostCondottaAcque;
    }


    /**
     * Sets the ccostCondottaAcque value for this CcostInfrDistrRaccAcque.
     * 
     * @param ccostCondottaAcque
     */
    public void setCcostCondottaAcque(com.hyperborea.sira.ws.CcostCondottaAcque ccostCondottaAcque) {
        this.ccostCondottaAcque = ccostCondottaAcque;
    }


    /**
     * Gets the ccostElementoAcquedottistico value for this CcostInfrDistrRaccAcque.
     * 
     * @return ccostElementoAcquedottistico
     */
    public com.hyperborea.sira.ws.CcostElementoAcquedottistico getCcostElementoAcquedottistico() {
        return ccostElementoAcquedottistico;
    }


    /**
     * Sets the ccostElementoAcquedottistico value for this CcostInfrDistrRaccAcque.
     * 
     * @param ccostElementoAcquedottistico
     */
    public void setCcostElementoAcquedottistico(com.hyperborea.sira.ws.CcostElementoAcquedottistico ccostElementoAcquedottistico) {
        this.ccostElementoAcquedottistico = ccostElementoAcquedottistico;
    }


    /**
     * Gets the idCcost value for this CcostInfrDistrRaccAcque.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostInfrDistrRaccAcque.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostInfrDistrRaccAcque)) return false;
        CcostInfrDistrRaccAcque other = (CcostInfrDistrRaccAcque) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostCollettoreFognario==null && other.getCcostCollettoreFognario()==null) || 
             (this.ccostCollettoreFognario!=null &&
              this.ccostCollettoreFognario.equals(other.getCcostCollettoreFognario()))) &&
            ((this.ccostCondottaAcque==null && other.getCcostCondottaAcque()==null) || 
             (this.ccostCondottaAcque!=null &&
              this.ccostCondottaAcque.equals(other.getCcostCondottaAcque()))) &&
            ((this.ccostElementoAcquedottistico==null && other.getCcostElementoAcquedottistico()==null) || 
             (this.ccostElementoAcquedottistico!=null &&
              this.ccostElementoAcquedottistico.equals(other.getCcostElementoAcquedottistico()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost())));
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
        if (getCcostCollettoreFognario() != null) {
            _hashCode += getCcostCollettoreFognario().hashCode();
        }
        if (getCcostCondottaAcque() != null) {
            _hashCode += getCcostCondottaAcque().hashCode();
        }
        if (getCcostElementoAcquedottistico() != null) {
            _hashCode += getCcostElementoAcquedottistico().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostInfrDistrRaccAcque.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrDistrRaccAcque"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostCollettoreFognario");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostCollettoreFognario"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCollettoreFognario"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostCondottaAcque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostCondottaAcque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCondottaAcque"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostElementoAcquedottistico");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostElementoAcquedottistico"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostElementoAcquedottistico"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
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
