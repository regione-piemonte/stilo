/**
 * CcostCampateEd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostCampateEd  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CampateEdCaviInterrati campateEdCaviInterrati;

    private com.hyperborea.sira.ws.CcostInfrasEd ccostInfrasEd;

    private java.lang.Integer idCcost;

    private java.lang.Float paramCatenaria;

    private java.lang.Float paramFune;

    private int progrTratta;

    public CcostCampateEd() {
    }

    public CcostCampateEd(
           com.hyperborea.sira.ws.CampateEdCaviInterrati campateEdCaviInterrati,
           com.hyperborea.sira.ws.CcostInfrasEd ccostInfrasEd,
           java.lang.Integer idCcost,
           java.lang.Float paramCatenaria,
           java.lang.Float paramFune,
           int progrTratta) {
           this.campateEdCaviInterrati = campateEdCaviInterrati;
           this.ccostInfrasEd = ccostInfrasEd;
           this.idCcost = idCcost;
           this.paramCatenaria = paramCatenaria;
           this.paramFune = paramFune;
           this.progrTratta = progrTratta;
    }


    /**
     * Gets the campateEdCaviInterrati value for this CcostCampateEd.
     * 
     * @return campateEdCaviInterrati
     */
    public com.hyperborea.sira.ws.CampateEdCaviInterrati getCampateEdCaviInterrati() {
        return campateEdCaviInterrati;
    }


    /**
     * Sets the campateEdCaviInterrati value for this CcostCampateEd.
     * 
     * @param campateEdCaviInterrati
     */
    public void setCampateEdCaviInterrati(com.hyperborea.sira.ws.CampateEdCaviInterrati campateEdCaviInterrati) {
        this.campateEdCaviInterrati = campateEdCaviInterrati;
    }


    /**
     * Gets the ccostInfrasEd value for this CcostCampateEd.
     * 
     * @return ccostInfrasEd
     */
    public com.hyperborea.sira.ws.CcostInfrasEd getCcostInfrasEd() {
        return ccostInfrasEd;
    }


    /**
     * Sets the ccostInfrasEd value for this CcostCampateEd.
     * 
     * @param ccostInfrasEd
     */
    public void setCcostInfrasEd(com.hyperborea.sira.ws.CcostInfrasEd ccostInfrasEd) {
        this.ccostInfrasEd = ccostInfrasEd;
    }


    /**
     * Gets the idCcost value for this CcostCampateEd.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostCampateEd.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the paramCatenaria value for this CcostCampateEd.
     * 
     * @return paramCatenaria
     */
    public java.lang.Float getParamCatenaria() {
        return paramCatenaria;
    }


    /**
     * Sets the paramCatenaria value for this CcostCampateEd.
     * 
     * @param paramCatenaria
     */
    public void setParamCatenaria(java.lang.Float paramCatenaria) {
        this.paramCatenaria = paramCatenaria;
    }


    /**
     * Gets the paramFune value for this CcostCampateEd.
     * 
     * @return paramFune
     */
    public java.lang.Float getParamFune() {
        return paramFune;
    }


    /**
     * Sets the paramFune value for this CcostCampateEd.
     * 
     * @param paramFune
     */
    public void setParamFune(java.lang.Float paramFune) {
        this.paramFune = paramFune;
    }


    /**
     * Gets the progrTratta value for this CcostCampateEd.
     * 
     * @return progrTratta
     */
    public int getProgrTratta() {
        return progrTratta;
    }


    /**
     * Sets the progrTratta value for this CcostCampateEd.
     * 
     * @param progrTratta
     */
    public void setProgrTratta(int progrTratta) {
        this.progrTratta = progrTratta;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostCampateEd)) return false;
        CcostCampateEd other = (CcostCampateEd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.campateEdCaviInterrati==null && other.getCampateEdCaviInterrati()==null) || 
             (this.campateEdCaviInterrati!=null &&
              this.campateEdCaviInterrati.equals(other.getCampateEdCaviInterrati()))) &&
            ((this.ccostInfrasEd==null && other.getCcostInfrasEd()==null) || 
             (this.ccostInfrasEd!=null &&
              this.ccostInfrasEd.equals(other.getCcostInfrasEd()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.paramCatenaria==null && other.getParamCatenaria()==null) || 
             (this.paramCatenaria!=null &&
              this.paramCatenaria.equals(other.getParamCatenaria()))) &&
            ((this.paramFune==null && other.getParamFune()==null) || 
             (this.paramFune!=null &&
              this.paramFune.equals(other.getParamFune()))) &&
            this.progrTratta == other.getProgrTratta();
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
        if (getCampateEdCaviInterrati() != null) {
            _hashCode += getCampateEdCaviInterrati().hashCode();
        }
        if (getCcostInfrasEd() != null) {
            _hashCode += getCcostInfrasEd().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getParamCatenaria() != null) {
            _hashCode += getParamCatenaria().hashCode();
        }
        if (getParamFune() != null) {
            _hashCode += getParamFune().hashCode();
        }
        _hashCode += getProgrTratta();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostCampateEd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostCampateEd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campateEdCaviInterrati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "campateEdCaviInterrati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campateEdCaviInterrati"));
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
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paramCatenaria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paramCatenaria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paramFune");
        elemField.setXmlName(new javax.xml.namespace.QName("", "paramFune"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("progrTratta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "progrTratta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
