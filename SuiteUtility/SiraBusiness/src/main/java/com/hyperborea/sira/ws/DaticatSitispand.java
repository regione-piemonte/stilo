/**
 * DaticatSitispand.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DaticatSitispand  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anniSpand;

    private java.lang.Float qtaAcque;

    private java.lang.Float qtaSanse;

    private java.lang.Integer spandPrevisto;

    private java.lang.Float supUtilizzata;

    private com.hyperborea.sira.ws.VocFrDaticatSitispTitPoss vocFrDaticatSitispTitPoss;

    private java.lang.Integer wsRefDatiCatastaliId;

    public DaticatSitispand() {
    }

    public DaticatSitispand(
           java.lang.Integer anniSpand,
           java.lang.Float qtaAcque,
           java.lang.Float qtaSanse,
           java.lang.Integer spandPrevisto,
           java.lang.Float supUtilizzata,
           com.hyperborea.sira.ws.VocFrDaticatSitispTitPoss vocFrDaticatSitispTitPoss,
           java.lang.Integer wsRefDatiCatastaliId) {
        this.anniSpand = anniSpand;
        this.qtaAcque = qtaAcque;
        this.qtaSanse = qtaSanse;
        this.spandPrevisto = spandPrevisto;
        this.supUtilizzata = supUtilizzata;
        this.vocFrDaticatSitispTitPoss = vocFrDaticatSitispTitPoss;
        this.wsRefDatiCatastaliId = wsRefDatiCatastaliId;
    }


    /**
     * Gets the anniSpand value for this DaticatSitispand.
     * 
     * @return anniSpand
     */
    public java.lang.Integer getAnniSpand() {
        return anniSpand;
    }


    /**
     * Sets the anniSpand value for this DaticatSitispand.
     * 
     * @param anniSpand
     */
    public void setAnniSpand(java.lang.Integer anniSpand) {
        this.anniSpand = anniSpand;
    }


    /**
     * Gets the qtaAcque value for this DaticatSitispand.
     * 
     * @return qtaAcque
     */
    public java.lang.Float getQtaAcque() {
        return qtaAcque;
    }


    /**
     * Sets the qtaAcque value for this DaticatSitispand.
     * 
     * @param qtaAcque
     */
    public void setQtaAcque(java.lang.Float qtaAcque) {
        this.qtaAcque = qtaAcque;
    }


    /**
     * Gets the qtaSanse value for this DaticatSitispand.
     * 
     * @return qtaSanse
     */
    public java.lang.Float getQtaSanse() {
        return qtaSanse;
    }


    /**
     * Sets the qtaSanse value for this DaticatSitispand.
     * 
     * @param qtaSanse
     */
    public void setQtaSanse(java.lang.Float qtaSanse) {
        this.qtaSanse = qtaSanse;
    }


    /**
     * Gets the spandPrevisto value for this DaticatSitispand.
     * 
     * @return spandPrevisto
     */
    public java.lang.Integer getSpandPrevisto() {
        return spandPrevisto;
    }


    /**
     * Sets the spandPrevisto value for this DaticatSitispand.
     * 
     * @param spandPrevisto
     */
    public void setSpandPrevisto(java.lang.Integer spandPrevisto) {
        this.spandPrevisto = spandPrevisto;
    }


    /**
     * Gets the supUtilizzata value for this DaticatSitispand.
     * 
     * @return supUtilizzata
     */
    public java.lang.Float getSupUtilizzata() {
        return supUtilizzata;
    }


    /**
     * Sets the supUtilizzata value for this DaticatSitispand.
     * 
     * @param supUtilizzata
     */
    public void setSupUtilizzata(java.lang.Float supUtilizzata) {
        this.supUtilizzata = supUtilizzata;
    }


    /**
     * Gets the vocFrDaticatSitispTitPoss value for this DaticatSitispand.
     * 
     * @return vocFrDaticatSitispTitPoss
     */
    public com.hyperborea.sira.ws.VocFrDaticatSitispTitPoss getVocFrDaticatSitispTitPoss() {
        return vocFrDaticatSitispTitPoss;
    }


    /**
     * Sets the vocFrDaticatSitispTitPoss value for this DaticatSitispand.
     * 
     * @param vocFrDaticatSitispTitPoss
     */
    public void setVocFrDaticatSitispTitPoss(com.hyperborea.sira.ws.VocFrDaticatSitispTitPoss vocFrDaticatSitispTitPoss) {
        this.vocFrDaticatSitispTitPoss = vocFrDaticatSitispTitPoss;
    }


    /**
     * Gets the wsRefDatiCatastaliId value for this DaticatSitispand.
     * 
     * @return wsRefDatiCatastaliId
     */
    public java.lang.Integer getWsRefDatiCatastaliId() {
        return wsRefDatiCatastaliId;
    }


    /**
     * Sets the wsRefDatiCatastaliId value for this DaticatSitispand.
     * 
     * @param wsRefDatiCatastaliId
     */
    public void setWsRefDatiCatastaliId(java.lang.Integer wsRefDatiCatastaliId) {
        this.wsRefDatiCatastaliId = wsRefDatiCatastaliId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DaticatSitispand)) return false;
        DaticatSitispand other = (DaticatSitispand) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.anniSpand==null && other.getAnniSpand()==null) || 
             (this.anniSpand!=null &&
              this.anniSpand.equals(other.getAnniSpand()))) &&
            ((this.qtaAcque==null && other.getQtaAcque()==null) || 
             (this.qtaAcque!=null &&
              this.qtaAcque.equals(other.getQtaAcque()))) &&
            ((this.qtaSanse==null && other.getQtaSanse()==null) || 
             (this.qtaSanse!=null &&
              this.qtaSanse.equals(other.getQtaSanse()))) &&
            ((this.spandPrevisto==null && other.getSpandPrevisto()==null) || 
             (this.spandPrevisto!=null &&
              this.spandPrevisto.equals(other.getSpandPrevisto()))) &&
            ((this.supUtilizzata==null && other.getSupUtilizzata()==null) || 
             (this.supUtilizzata!=null &&
              this.supUtilizzata.equals(other.getSupUtilizzata()))) &&
            ((this.vocFrDaticatSitispTitPoss==null && other.getVocFrDaticatSitispTitPoss()==null) || 
             (this.vocFrDaticatSitispTitPoss!=null &&
              this.vocFrDaticatSitispTitPoss.equals(other.getVocFrDaticatSitispTitPoss()))) &&
            ((this.wsRefDatiCatastaliId==null && other.getWsRefDatiCatastaliId()==null) || 
             (this.wsRefDatiCatastaliId!=null &&
              this.wsRefDatiCatastaliId.equals(other.getWsRefDatiCatastaliId())));
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
        if (getAnniSpand() != null) {
            _hashCode += getAnniSpand().hashCode();
        }
        if (getQtaAcque() != null) {
            _hashCode += getQtaAcque().hashCode();
        }
        if (getQtaSanse() != null) {
            _hashCode += getQtaSanse().hashCode();
        }
        if (getSpandPrevisto() != null) {
            _hashCode += getSpandPrevisto().hashCode();
        }
        if (getSupUtilizzata() != null) {
            _hashCode += getSupUtilizzata().hashCode();
        }
        if (getVocFrDaticatSitispTitPoss() != null) {
            _hashCode += getVocFrDaticatSitispTitPoss().hashCode();
        }
        if (getWsRefDatiCatastaliId() != null) {
            _hashCode += getWsRefDatiCatastaliId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DaticatSitispand.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "daticatSitispand"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anniSpand");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anniSpand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qtaAcque");
        elemField.setXmlName(new javax.xml.namespace.QName("", "qtaAcque"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("qtaSanse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "qtaSanse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("spandPrevisto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "spandPrevisto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supUtilizzata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supUtilizzata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocFrDaticatSitispTitPoss");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocFrDaticatSitispTitPoss"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocFrDaticatSitispTitPoss"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsRefDatiCatastaliId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsRefDatiCatastaliId"));
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
