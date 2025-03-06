/**
 * ConsProd.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ConsProd  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private int anno;

    private float enecons;

    private float eneprod;

    private java.lang.Integer idprodcons;

    private int semestre;

    private com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche;

    public ConsProd() {
    }

    public ConsProd(
           int anno,
           float enecons,
           float eneprod,
           java.lang.Integer idprodcons,
           int semestre,
           com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche) {
        this.anno = anno;
        this.enecons = enecons;
        this.eneprod = eneprod;
        this.idprodcons = idprodcons;
        this.semestre = semestre;
        this.vocFontiEnergetiche = vocFontiEnergetiche;
    }


    /**
     * Gets the anno value for this ConsProd.
     * 
     * @return anno
     */
    public int getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this ConsProd.
     * 
     * @param anno
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }


    /**
     * Gets the enecons value for this ConsProd.
     * 
     * @return enecons
     */
    public float getEnecons() {
        return enecons;
    }


    /**
     * Sets the enecons value for this ConsProd.
     * 
     * @param enecons
     */
    public void setEnecons(float enecons) {
        this.enecons = enecons;
    }


    /**
     * Gets the eneprod value for this ConsProd.
     * 
     * @return eneprod
     */
    public float getEneprod() {
        return eneprod;
    }


    /**
     * Sets the eneprod value for this ConsProd.
     * 
     * @param eneprod
     */
    public void setEneprod(float eneprod) {
        this.eneprod = eneprod;
    }


    /**
     * Gets the idprodcons value for this ConsProd.
     * 
     * @return idprodcons
     */
    public java.lang.Integer getIdprodcons() {
        return idprodcons;
    }


    /**
     * Sets the idprodcons value for this ConsProd.
     * 
     * @param idprodcons
     */
    public void setIdprodcons(java.lang.Integer idprodcons) {
        this.idprodcons = idprodcons;
    }


    /**
     * Gets the semestre value for this ConsProd.
     * 
     * @return semestre
     */
    public int getSemestre() {
        return semestre;
    }


    /**
     * Sets the semestre value for this ConsProd.
     * 
     * @param semestre
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }


    /**
     * Gets the vocFontiEnergetiche value for this ConsProd.
     * 
     * @return vocFontiEnergetiche
     */
    public com.hyperborea.sira.ws.VocFontiEnergetiche getVocFontiEnergetiche() {
        return vocFontiEnergetiche;
    }


    /**
     * Sets the vocFontiEnergetiche value for this ConsProd.
     * 
     * @param vocFontiEnergetiche
     */
    public void setVocFontiEnergetiche(com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche) {
        this.vocFontiEnergetiche = vocFontiEnergetiche;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ConsProd)) return false;
        ConsProd other = (ConsProd) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.anno == other.getAnno() &&
            this.enecons == other.getEnecons() &&
            this.eneprod == other.getEneprod() &&
            ((this.idprodcons==null && other.getIdprodcons()==null) || 
             (this.idprodcons!=null &&
              this.idprodcons.equals(other.getIdprodcons()))) &&
            this.semestre == other.getSemestre() &&
            ((this.vocFontiEnergetiche==null && other.getVocFontiEnergetiche()==null) || 
             (this.vocFontiEnergetiche!=null &&
              this.vocFontiEnergetiche.equals(other.getVocFontiEnergetiche())));
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
        _hashCode += getAnno();
        _hashCode += new Float(getEnecons()).hashCode();
        _hashCode += new Float(getEneprod()).hashCode();
        if (getIdprodcons() != null) {
            _hashCode += getIdprodcons().hashCode();
        }
        _hashCode += getSemestre();
        if (getVocFontiEnergetiche() != null) {
            _hashCode += getVocFontiEnergetiche().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ConsProd.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consProd"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("enecons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "enecons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eneprod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "eneprod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idprodcons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idprodcons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("semestre");
        elemField.setXmlName(new javax.xml.namespace.QName("", "semestre"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocFontiEnergetiche");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocFontiEnergetiche"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFontiEnergetiche"));
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
