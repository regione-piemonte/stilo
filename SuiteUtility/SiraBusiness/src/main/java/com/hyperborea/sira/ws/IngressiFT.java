/**
 * IngressiFT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class IngressiFT  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private int anno;

    private float consint;

    private java.lang.Integer idingressi;

    private float qta;

    private int semestre;

    private com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche;

    public IngressiFT() {
    }

    public IngressiFT(
           int anno,
           float consint,
           java.lang.Integer idingressi,
           float qta,
           int semestre,
           com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche) {
        this.anno = anno;
        this.consint = consint;
        this.idingressi = idingressi;
        this.qta = qta;
        this.semestre = semestre;
        this.vocFontiEnergetiche = vocFontiEnergetiche;
    }


    /**
     * Gets the anno value for this IngressiFT.
     * 
     * @return anno
     */
    public int getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this IngressiFT.
     * 
     * @param anno
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }


    /**
     * Gets the consint value for this IngressiFT.
     * 
     * @return consint
     */
    public float getConsint() {
        return consint;
    }


    /**
     * Sets the consint value for this IngressiFT.
     * 
     * @param consint
     */
    public void setConsint(float consint) {
        this.consint = consint;
    }


    /**
     * Gets the idingressi value for this IngressiFT.
     * 
     * @return idingressi
     */
    public java.lang.Integer getIdingressi() {
        return idingressi;
    }


    /**
     * Sets the idingressi value for this IngressiFT.
     * 
     * @param idingressi
     */
    public void setIdingressi(java.lang.Integer idingressi) {
        this.idingressi = idingressi;
    }


    /**
     * Gets the qta value for this IngressiFT.
     * 
     * @return qta
     */
    public float getQta() {
        return qta;
    }


    /**
     * Sets the qta value for this IngressiFT.
     * 
     * @param qta
     */
    public void setQta(float qta) {
        this.qta = qta;
    }


    /**
     * Gets the semestre value for this IngressiFT.
     * 
     * @return semestre
     */
    public int getSemestre() {
        return semestre;
    }


    /**
     * Sets the semestre value for this IngressiFT.
     * 
     * @param semestre
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }


    /**
     * Gets the vocFontiEnergetiche value for this IngressiFT.
     * 
     * @return vocFontiEnergetiche
     */
    public com.hyperborea.sira.ws.VocFontiEnergetiche getVocFontiEnergetiche() {
        return vocFontiEnergetiche;
    }


    /**
     * Sets the vocFontiEnergetiche value for this IngressiFT.
     * 
     * @param vocFontiEnergetiche
     */
    public void setVocFontiEnergetiche(com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche) {
        this.vocFontiEnergetiche = vocFontiEnergetiche;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof IngressiFT)) return false;
        IngressiFT other = (IngressiFT) obj;
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
            ((this.idingressi==null && other.getIdingressi()==null) || 
             (this.idingressi!=null &&
              this.idingressi.equals(other.getIdingressi()))) &&
            this.qta == other.getQta() &&
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
        if (getIdingressi() != null) {
            _hashCode += getIdingressi().hashCode();
        }
        _hashCode += new Float(getQta()).hashCode();
        _hashCode += getSemestre();
        if (getVocFontiEnergetiche() != null) {
            _hashCode += getVocFontiEnergetiche().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(IngressiFT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ingressiFT"));
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
        elemField.setFieldName("idingressi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idingressi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qta");
        elemField.setXmlName(new javax.xml.namespace.QName("", "qta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
