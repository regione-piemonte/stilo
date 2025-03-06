/**
 * CcostSitiSpand.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostSitiSpand  implements java.io.Serializable {
    private com.hyperborea.sira.ws.DaticatSitispand[] daticatSitispands;

    private java.util.Calendar fineSpandPrev;

    private java.lang.Integer idCcost;

    private java.util.Calendar inizioSpandPrev;

    public CcostSitiSpand() {
    }

    public CcostSitiSpand(
           com.hyperborea.sira.ws.DaticatSitispand[] daticatSitispands,
           java.util.Calendar fineSpandPrev,
           java.lang.Integer idCcost,
           java.util.Calendar inizioSpandPrev) {
           this.daticatSitispands = daticatSitispands;
           this.fineSpandPrev = fineSpandPrev;
           this.idCcost = idCcost;
           this.inizioSpandPrev = inizioSpandPrev;
    }


    /**
     * Gets the daticatSitispands value for this CcostSitiSpand.
     * 
     * @return daticatSitispands
     */
    public com.hyperborea.sira.ws.DaticatSitispand[] getDaticatSitispands() {
        return daticatSitispands;
    }


    /**
     * Sets the daticatSitispands value for this CcostSitiSpand.
     * 
     * @param daticatSitispands
     */
    public void setDaticatSitispands(com.hyperborea.sira.ws.DaticatSitispand[] daticatSitispands) {
        this.daticatSitispands = daticatSitispands;
    }

    public com.hyperborea.sira.ws.DaticatSitispand getDaticatSitispands(int i) {
        return this.daticatSitispands[i];
    }

    public void setDaticatSitispands(int i, com.hyperborea.sira.ws.DaticatSitispand _value) {
        this.daticatSitispands[i] = _value;
    }


    /**
     * Gets the fineSpandPrev value for this CcostSitiSpand.
     * 
     * @return fineSpandPrev
     */
    public java.util.Calendar getFineSpandPrev() {
        return fineSpandPrev;
    }


    /**
     * Sets the fineSpandPrev value for this CcostSitiSpand.
     * 
     * @param fineSpandPrev
     */
    public void setFineSpandPrev(java.util.Calendar fineSpandPrev) {
        this.fineSpandPrev = fineSpandPrev;
    }


    /**
     * Gets the idCcost value for this CcostSitiSpand.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostSitiSpand.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the inizioSpandPrev value for this CcostSitiSpand.
     * 
     * @return inizioSpandPrev
     */
    public java.util.Calendar getInizioSpandPrev() {
        return inizioSpandPrev;
    }


    /**
     * Sets the inizioSpandPrev value for this CcostSitiSpand.
     * 
     * @param inizioSpandPrev
     */
    public void setInizioSpandPrev(java.util.Calendar inizioSpandPrev) {
        this.inizioSpandPrev = inizioSpandPrev;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostSitiSpand)) return false;
        CcostSitiSpand other = (CcostSitiSpand) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.daticatSitispands==null && other.getDaticatSitispands()==null) || 
             (this.daticatSitispands!=null &&
              java.util.Arrays.equals(this.daticatSitispands, other.getDaticatSitispands()))) &&
            ((this.fineSpandPrev==null && other.getFineSpandPrev()==null) || 
             (this.fineSpandPrev!=null &&
              this.fineSpandPrev.equals(other.getFineSpandPrev()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.inizioSpandPrev==null && other.getInizioSpandPrev()==null) || 
             (this.inizioSpandPrev!=null &&
              this.inizioSpandPrev.equals(other.getInizioSpandPrev())));
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
        if (getDaticatSitispands() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDaticatSitispands());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDaticatSitispands(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getFineSpandPrev() != null) {
            _hashCode += getFineSpandPrev().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getInizioSpandPrev() != null) {
            _hashCode += getInizioSpandPrev().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostSitiSpand.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostSitiSpand"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("daticatSitispands");
        elemField.setXmlName(new javax.xml.namespace.QName("", "daticatSitispands"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "daticatSitispand"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fineSpandPrev");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fineSpandPrev"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inizioSpandPrev");
        elemField.setXmlName(new javax.xml.namespace.QName("", "inizioSpandPrev"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
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
