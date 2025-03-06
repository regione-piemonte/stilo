/**
 * NatureDia.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class NatureDia  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.DichiarazioniAmbientali[] dichiarazioniAmbientalis;

    private com.hyperborea.sira.ws.RiferimentiNormativi[] riferimentiNormativis;

    public NatureDia() {
    }

    public NatureDia(
           java.lang.Integer codice,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.DichiarazioniAmbientali[] dichiarazioniAmbientalis,
           com.hyperborea.sira.ws.RiferimentiNormativi[] riferimentiNormativis) {
        this.codice = codice;
        this.descrizione = descrizione;
        this.dichiarazioniAmbientalis = dichiarazioniAmbientalis;
        this.riferimentiNormativis = riferimentiNormativis;
    }


    /**
     * Gets the codice value for this NatureDia.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this NatureDia.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this NatureDia.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this NatureDia.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the dichiarazioniAmbientalis value for this NatureDia.
     * 
     * @return dichiarazioniAmbientalis
     */
    public com.hyperborea.sira.ws.DichiarazioniAmbientali[] getDichiarazioniAmbientalis() {
        return dichiarazioniAmbientalis;
    }


    /**
     * Sets the dichiarazioniAmbientalis value for this NatureDia.
     * 
     * @param dichiarazioniAmbientalis
     */
    public void setDichiarazioniAmbientalis(com.hyperborea.sira.ws.DichiarazioniAmbientali[] dichiarazioniAmbientalis) {
        this.dichiarazioniAmbientalis = dichiarazioniAmbientalis;
    }

    public com.hyperborea.sira.ws.DichiarazioniAmbientali getDichiarazioniAmbientalis(int i) {
        return this.dichiarazioniAmbientalis[i];
    }

    public void setDichiarazioniAmbientalis(int i, com.hyperborea.sira.ws.DichiarazioniAmbientali _value) {
        this.dichiarazioniAmbientalis[i] = _value;
    }


    /**
     * Gets the riferimentiNormativis value for this NatureDia.
     * 
     * @return riferimentiNormativis
     */
    public com.hyperborea.sira.ws.RiferimentiNormativi[] getRiferimentiNormativis() {
        return riferimentiNormativis;
    }


    /**
     * Sets the riferimentiNormativis value for this NatureDia.
     * 
     * @param riferimentiNormativis
     */
    public void setRiferimentiNormativis(com.hyperborea.sira.ws.RiferimentiNormativi[] riferimentiNormativis) {
        this.riferimentiNormativis = riferimentiNormativis;
    }

    public com.hyperborea.sira.ws.RiferimentiNormativi getRiferimentiNormativis(int i) {
        return this.riferimentiNormativis[i];
    }

    public void setRiferimentiNormativis(int i, com.hyperborea.sira.ws.RiferimentiNormativi _value) {
        this.riferimentiNormativis[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof NatureDia)) return false;
        NatureDia other = (NatureDia) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.dichiarazioniAmbientalis==null && other.getDichiarazioniAmbientalis()==null) || 
             (this.dichiarazioniAmbientalis!=null &&
              java.util.Arrays.equals(this.dichiarazioniAmbientalis, other.getDichiarazioniAmbientalis()))) &&
            ((this.riferimentiNormativis==null && other.getRiferimentiNormativis()==null) || 
             (this.riferimentiNormativis!=null &&
              java.util.Arrays.equals(this.riferimentiNormativis, other.getRiferimentiNormativis())));
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
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getDichiarazioniAmbientalis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getDichiarazioniAmbientalis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getDichiarazioniAmbientalis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getRiferimentiNormativis() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRiferimentiNormativis());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRiferimentiNormativis(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(NatureDia.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "natureDia"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dichiarazioniAmbientalis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dichiarazioniAmbientalis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dichiarazioniAmbientali"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentiNormativis");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentiNormativis"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "riferimentiNormativi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
