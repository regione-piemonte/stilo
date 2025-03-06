/**
 * Consumi.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class Consumi  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private int anno;

    private float consumo;

    private java.lang.Integer idprodcons;

    private float pci;

    private int semestre;

    private float tenzolfo;

    private com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche;

    public Consumi() {
    }

    public Consumi(
           int anno,
           float consumo,
           java.lang.Integer idprodcons,
           float pci,
           int semestre,
           float tenzolfo,
           com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche) {
        this.anno = anno;
        this.consumo = consumo;
        this.idprodcons = idprodcons;
        this.pci = pci;
        this.semestre = semestre;
        this.tenzolfo = tenzolfo;
        this.vocFontiEnergetiche = vocFontiEnergetiche;
    }


    /**
     * Gets the anno value for this Consumi.
     * 
     * @return anno
     */
    public int getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this Consumi.
     * 
     * @param anno
     */
    public void setAnno(int anno) {
        this.anno = anno;
    }


    /**
     * Gets the consumo value for this Consumi.
     * 
     * @return consumo
     */
    public float getConsumo() {
        return consumo;
    }


    /**
     * Sets the consumo value for this Consumi.
     * 
     * @param consumo
     */
    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }


    /**
     * Gets the idprodcons value for this Consumi.
     * 
     * @return idprodcons
     */
    public java.lang.Integer getIdprodcons() {
        return idprodcons;
    }


    /**
     * Sets the idprodcons value for this Consumi.
     * 
     * @param idprodcons
     */
    public void setIdprodcons(java.lang.Integer idprodcons) {
        this.idprodcons = idprodcons;
    }


    /**
     * Gets the pci value for this Consumi.
     * 
     * @return pci
     */
    public float getPci() {
        return pci;
    }


    /**
     * Sets the pci value for this Consumi.
     * 
     * @param pci
     */
    public void setPci(float pci) {
        this.pci = pci;
    }


    /**
     * Gets the semestre value for this Consumi.
     * 
     * @return semestre
     */
    public int getSemestre() {
        return semestre;
    }


    /**
     * Sets the semestre value for this Consumi.
     * 
     * @param semestre
     */
    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }


    /**
     * Gets the tenzolfo value for this Consumi.
     * 
     * @return tenzolfo
     */
    public float getTenzolfo() {
        return tenzolfo;
    }


    /**
     * Sets the tenzolfo value for this Consumi.
     * 
     * @param tenzolfo
     */
    public void setTenzolfo(float tenzolfo) {
        this.tenzolfo = tenzolfo;
    }


    /**
     * Gets the vocFontiEnergetiche value for this Consumi.
     * 
     * @return vocFontiEnergetiche
     */
    public com.hyperborea.sira.ws.VocFontiEnergetiche getVocFontiEnergetiche() {
        return vocFontiEnergetiche;
    }


    /**
     * Sets the vocFontiEnergetiche value for this Consumi.
     * 
     * @param vocFontiEnergetiche
     */
    public void setVocFontiEnergetiche(com.hyperborea.sira.ws.VocFontiEnergetiche vocFontiEnergetiche) {
        this.vocFontiEnergetiche = vocFontiEnergetiche;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Consumi)) return false;
        Consumi other = (Consumi) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.anno == other.getAnno() &&
            this.consumo == other.getConsumo() &&
            ((this.idprodcons==null && other.getIdprodcons()==null) || 
             (this.idprodcons!=null &&
              this.idprodcons.equals(other.getIdprodcons()))) &&
            this.pci == other.getPci() &&
            this.semestre == other.getSemestre() &&
            this.tenzolfo == other.getTenzolfo() &&
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
        _hashCode += new Float(getConsumo()).hashCode();
        if (getIdprodcons() != null) {
            _hashCode += getIdprodcons().hashCode();
        }
        _hashCode += new Float(getPci()).hashCode();
        _hashCode += getSemestre();
        _hashCode += new Float(getTenzolfo()).hashCode();
        if (getVocFontiEnergetiche() != null) {
            _hashCode += getVocFontiEnergetiche().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Consumi.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "consumi"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("consumo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "consumo"));
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
        elemField.setFieldName("pci");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pci"));
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
        elemField.setFieldName("tenzolfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tenzolfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
