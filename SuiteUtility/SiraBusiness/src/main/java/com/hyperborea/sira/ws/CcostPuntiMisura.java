/**
 * CcostPuntiMisura.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostPuntiMisura  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CcostPuntiControlloOcc ccostPuntiControlloOcc;

    private com.hyperborea.sira.ws.CcostPuntiMonitoraggioCon ccostPuntiMonitoraggioCon;

    private com.hyperborea.sira.ws.CcostPuntiMonitoraggioPer ccostPuntiMonitoraggioPer;

    private java.lang.String codice;

    private java.lang.Integer idCcost;

    public CcostPuntiMisura() {
    }

    public CcostPuntiMisura(
           com.hyperborea.sira.ws.CcostPuntiControlloOcc ccostPuntiControlloOcc,
           com.hyperborea.sira.ws.CcostPuntiMonitoraggioCon ccostPuntiMonitoraggioCon,
           com.hyperborea.sira.ws.CcostPuntiMonitoraggioPer ccostPuntiMonitoraggioPer,
           java.lang.String codice,
           java.lang.Integer idCcost) {
           this.ccostPuntiControlloOcc = ccostPuntiControlloOcc;
           this.ccostPuntiMonitoraggioCon = ccostPuntiMonitoraggioCon;
           this.ccostPuntiMonitoraggioPer = ccostPuntiMonitoraggioPer;
           this.codice = codice;
           this.idCcost = idCcost;
    }


    /**
     * Gets the ccostPuntiControlloOcc value for this CcostPuntiMisura.
     * 
     * @return ccostPuntiControlloOcc
     */
    public com.hyperborea.sira.ws.CcostPuntiControlloOcc getCcostPuntiControlloOcc() {
        return ccostPuntiControlloOcc;
    }


    /**
     * Sets the ccostPuntiControlloOcc value for this CcostPuntiMisura.
     * 
     * @param ccostPuntiControlloOcc
     */
    public void setCcostPuntiControlloOcc(com.hyperborea.sira.ws.CcostPuntiControlloOcc ccostPuntiControlloOcc) {
        this.ccostPuntiControlloOcc = ccostPuntiControlloOcc;
    }


    /**
     * Gets the ccostPuntiMonitoraggioCon value for this CcostPuntiMisura.
     * 
     * @return ccostPuntiMonitoraggioCon
     */
    public com.hyperborea.sira.ws.CcostPuntiMonitoraggioCon getCcostPuntiMonitoraggioCon() {
        return ccostPuntiMonitoraggioCon;
    }


    /**
     * Sets the ccostPuntiMonitoraggioCon value for this CcostPuntiMisura.
     * 
     * @param ccostPuntiMonitoraggioCon
     */
    public void setCcostPuntiMonitoraggioCon(com.hyperborea.sira.ws.CcostPuntiMonitoraggioCon ccostPuntiMonitoraggioCon) {
        this.ccostPuntiMonitoraggioCon = ccostPuntiMonitoraggioCon;
    }


    /**
     * Gets the ccostPuntiMonitoraggioPer value for this CcostPuntiMisura.
     * 
     * @return ccostPuntiMonitoraggioPer
     */
    public com.hyperborea.sira.ws.CcostPuntiMonitoraggioPer getCcostPuntiMonitoraggioPer() {
        return ccostPuntiMonitoraggioPer;
    }


    /**
     * Sets the ccostPuntiMonitoraggioPer value for this CcostPuntiMisura.
     * 
     * @param ccostPuntiMonitoraggioPer
     */
    public void setCcostPuntiMonitoraggioPer(com.hyperborea.sira.ws.CcostPuntiMonitoraggioPer ccostPuntiMonitoraggioPer) {
        this.ccostPuntiMonitoraggioPer = ccostPuntiMonitoraggioPer;
    }


    /**
     * Gets the codice value for this CcostPuntiMisura.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this CcostPuntiMisura.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the idCcost value for this CcostPuntiMisura.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostPuntiMisura.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostPuntiMisura)) return false;
        CcostPuntiMisura other = (CcostPuntiMisura) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccostPuntiControlloOcc==null && other.getCcostPuntiControlloOcc()==null) || 
             (this.ccostPuntiControlloOcc!=null &&
              this.ccostPuntiControlloOcc.equals(other.getCcostPuntiControlloOcc()))) &&
            ((this.ccostPuntiMonitoraggioCon==null && other.getCcostPuntiMonitoraggioCon()==null) || 
             (this.ccostPuntiMonitoraggioCon!=null &&
              this.ccostPuntiMonitoraggioCon.equals(other.getCcostPuntiMonitoraggioCon()))) &&
            ((this.ccostPuntiMonitoraggioPer==null && other.getCcostPuntiMonitoraggioPer()==null) || 
             (this.ccostPuntiMonitoraggioPer!=null &&
              this.ccostPuntiMonitoraggioPer.equals(other.getCcostPuntiMonitoraggioPer()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
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
        if (getCcostPuntiControlloOcc() != null) {
            _hashCode += getCcostPuntiControlloOcc().hashCode();
        }
        if (getCcostPuntiMonitoraggioCon() != null) {
            _hashCode += getCcostPuntiMonitoraggioCon().hashCode();
        }
        if (getCcostPuntiMonitoraggioPer() != null) {
            _hashCode += getCcostPuntiMonitoraggioPer().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostPuntiMisura.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiMisura"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostPuntiControlloOcc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostPuntiControlloOcc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiControlloOcc"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostPuntiMonitoraggioCon");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostPuntiMonitoraggioCon"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiMonitoraggioCon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostPuntiMonitoraggioPer");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccostPuntiMonitoraggioPer"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostPuntiMonitoraggioPer"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
