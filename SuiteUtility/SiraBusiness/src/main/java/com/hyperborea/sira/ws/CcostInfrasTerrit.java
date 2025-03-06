/**
 * CcostInfrasTerrit.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostInfrasTerrit  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostInfrDistrRaccAcque ccostInfrDistrRaccAcque;

    private com.hyperborea.sira.ws.CcostInfrasEd ccostInfrasEd;

    private com.hyperborea.sira.ws.CcostInfrasRc ccostInfrasRc;

    private com.hyperborea.sira.ws.CcostInfrasTraspComb ccostInfrasTraspComb;

    private java.lang.Integer idCcost;

    public CcostInfrasTerrit() {
    }

    public CcostInfrasTerrit(
           com.hyperborea.sira.ws.CcostInfrDistrRaccAcque ccostInfrDistrRaccAcque,
           com.hyperborea.sira.ws.CcostInfrasEd ccostInfrasEd,
           com.hyperborea.sira.ws.CcostInfrasRc ccostInfrasRc,
           com.hyperborea.sira.ws.CcostInfrasTraspComb ccostInfrasTraspComb,
           java.lang.Integer idCcost) {
           this.ccostInfrDistrRaccAcque = ccostInfrDistrRaccAcque;
           this.ccostInfrasEd = ccostInfrasEd;
           this.ccostInfrasRc = ccostInfrasRc;
           this.ccostInfrasTraspComb = ccostInfrasTraspComb;
           this.idCcost = idCcost;
    }


    /**
     * Gets the ccostInfrDistrRaccAcque value for this CcostInfrasTerrit.
     * 
     * @return ccostInfrDistrRaccAcque
     */
    public com.hyperborea.sira.ws.CcostInfrDistrRaccAcque getCcostInfrDistrRaccAcque() {
        return ccostInfrDistrRaccAcque;
    }


    /**
     * Sets the ccostInfrDistrRaccAcque value for this CcostInfrasTerrit.
     * 
     * @param ccostInfrDistrRaccAcque
     */
    public void setCcostInfrDistrRaccAcque(com.hyperborea.sira.ws.CcostInfrDistrRaccAcque ccostInfrDistrRaccAcque) {
        this.ccostInfrDistrRaccAcque = ccostInfrDistrRaccAcque;
    }


    /**
     * Gets the ccostInfrasEd value for this CcostInfrasTerrit.
     * 
     * @return ccostInfrasEd
     */
    public com.hyperborea.sira.ws.CcostInfrasEd getCcostInfrasEd() {
        return ccostInfrasEd;
    }


    /**
     * Sets the ccostInfrasEd value for this CcostInfrasTerrit.
     * 
     * @param ccostInfrasEd
     */
    public void setCcostInfrasEd(com.hyperborea.sira.ws.CcostInfrasEd ccostInfrasEd) {
        this.ccostInfrasEd = ccostInfrasEd;
    }


    /**
     * Gets the ccostInfrasRc value for this CcostInfrasTerrit.
     * 
     * @return ccostInfrasRc
     */
    public com.hyperborea.sira.ws.CcostInfrasRc getCcostInfrasRc() {
        return ccostInfrasRc;
    }


    /**
     * Sets the ccostInfrasRc value for this CcostInfrasTerrit.
     * 
     * @param ccostInfrasRc
     */
    public void setCcostInfrasRc(com.hyperborea.sira.ws.CcostInfrasRc ccostInfrasRc) {
        this.ccostInfrasRc = ccostInfrasRc;
    }


    /**
     * Gets the ccostInfrasTraspComb value for this CcostInfrasTerrit.
     * 
     * @return ccostInfrasTraspComb
     */
    public com.hyperborea.sira.ws.CcostInfrasTraspComb getCcostInfrasTraspComb() {
        return ccostInfrasTraspComb;
    }


    /**
     * Sets the ccostInfrasTraspComb value for this CcostInfrasTerrit.
     * 
     * @param ccostInfrasTraspComb
     */
    public void setCcostInfrasTraspComb(com.hyperborea.sira.ws.CcostInfrasTraspComb ccostInfrasTraspComb) {
        this.ccostInfrasTraspComb = ccostInfrasTraspComb;
    }


    /**
     * Gets the idCcost value for this CcostInfrasTerrit.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostInfrasTerrit.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostInfrasTerrit)) return false;
        CcostInfrasTerrit other = (CcostInfrasTerrit) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostInfrDistrRaccAcque==null && other.getCcostInfrDistrRaccAcque()==null) || 
             (this.ccostInfrDistrRaccAcque!=null &&
              this.ccostInfrDistrRaccAcque.equals(other.getCcostInfrDistrRaccAcque()))) &&
            ((this.ccostInfrasEd==null && other.getCcostInfrasEd()==null) || 
             (this.ccostInfrasEd!=null &&
              this.ccostInfrasEd.equals(other.getCcostInfrasEd()))) &&
            ((this.ccostInfrasRc==null && other.getCcostInfrasRc()==null) || 
             (this.ccostInfrasRc!=null &&
              this.ccostInfrasRc.equals(other.getCcostInfrasRc()))) &&
            ((this.ccostInfrasTraspComb==null && other.getCcostInfrasTraspComb()==null) || 
             (this.ccostInfrasTraspComb!=null &&
              this.ccostInfrasTraspComb.equals(other.getCcostInfrasTraspComb()))) &&
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
        if (getCcostInfrDistrRaccAcque() != null) {
            _hashCode += getCcostInfrDistrRaccAcque().hashCode();
        }
        if (getCcostInfrasEd() != null) {
            _hashCode += getCcostInfrasEd().hashCode();
        }
        if (getCcostInfrasRc() != null) {
            _hashCode += getCcostInfrasRc().hashCode();
        }
        if (getCcostInfrasTraspComb() != null) {
            _hashCode += getCcostInfrasTraspComb().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostInfrasTerrit.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasTerrit"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostInfrDistrRaccAcque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostInfrDistrRaccAcque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrDistrRaccAcque"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostInfrasEd");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostInfrasEd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasEd"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostInfrasRc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostInfrasRc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasRc"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostInfrasTraspComb");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostInfrasTraspComb"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostInfrasTraspComb"));
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
