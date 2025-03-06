/**
 * Produzioniie.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Produzioniie  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private int anno;

    private float consint;

    private float conspomp;

    private float eneprod;

    private java.lang.Integer idprod;

    private int semestre;

    private com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche;

    public Produzioniie() {
    }

    public Produzioniie(
           int anno,
           float consint,
           float conspomp,
           float eneprod,
           java.lang.Integer idprod,
           int semestre,
           com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche) {
        this.anno = anno;
        this.consint = consint;
        this.conspomp = conspomp;
        this.eneprod = eneprod;
        this.idprod = idprod;
        this.semestre = semestre;
        this.vocFontiEnergetiche = vocFontiEnergetiche;
    }


    /**
     * Gets the anno value for this Produzioniie.
     * 
     * @return anno
     */
    public int getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this Produzioniie.
     * 
     * @param anno
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }


    /**
     * Gets the consint value for this Produzioniie.
     * 
     * @return consint
     */
    public float getConsint() {
        return consint;
    }


    /**
     * Sets the consint value for this Produzioniie.
     * 
     * @param consint
     */
    public void setConsint(float consint) {
        this.consint = consint;
    }


    /**
     * Gets the conspomp value for this Produzioniie.
     * 
     * @return conspomp
     */
    public float getConspomp() {
        return conspomp;
    }


    /**
     * Sets the conspomp value for this Produzioniie.
     * 
     * @param conspomp
     */
    public void setConspomp(float conspomp) {
        this.conspomp = conspomp;
    }


    /**
     * Gets the eneprod value for this Produzioniie.
     * 
     * @return eneprod
     */
    public float getEneprod() {
        return eneprod;
    }


    /**
     * Sets the eneprod value for this Produzioniie.
     * 
     * @param eneprod
     */
    public void setEneprod(float eneprod) {
        this.eneprod = eneprod;
    }


    /**
     * Gets the idprod value for this Produzioniie.
     * 
     * @return idprod
     */
    public java.lang.Integer getIdprod() {
        return idprod;
    }


    /**
     * Sets the idprod value for this Produzioniie.
     * 
     * @param idprod
     */
    public void setIdprod(java.lang.Integer idprod) {
        this.idprod = idprod;
    }


    /**
     * Gets the semestre value for this Produzioniie.
     * 
     * @return semestre
     */
    public int getSemestre() {
        return semestre;
    }


    /**
     * Sets the semestre value for this Produzioniie.
     * 
     * @param semestre
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }


    /**
     * Gets the vocFontiEnergetiche value for this Produzioniie.
     * 
     * @return vocFontiEnergetiche
     */
    public com.hyperborea.sira.ws.VocFontiEnergetiche getVocFontiEnergetiche() {
        return vocFontiEnergetiche;
    }


    /**
     * Sets the vocFontiEnergetiche value for this Produzioniie.
     * 
     * @param vocFontiEnergetiche
     */
    public void setVocFontiEnergetiche(com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche) {
        this.vocFontiEnergetiche = vocFontiEnergetiche;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Produzioniie)) return false;
        Produzioniie other = (Produzioniie) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.anno == other.getAnno() &&
            this.consint == other.getConsint() &&
            this.conspomp == other.getConspomp() &&
            this.eneprod == other.getEneprod() &&
            ((this.idprod==null && other.getIdprod()==null) || 
             (this.idprod!=null &&
              this.idprod.equals(other.getIdprod()))) &&
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
        _hashCode += new Float(getConsint()).hashCode();
        _hashCode += new Float(getConspomp()).hashCode();
        _hashCode += new Float(getEneprod()).hashCode();
        if (getIdprod() != null) {
            _hashCode += getIdprod().hashCode();
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
        new org.apache.axis.description.TypeDesc(Produzioniie.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "produzioniie"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consint");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consint"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("conspomp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "conspomp"));
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
        elemField.setFieldName("idprod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idprod"));
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
