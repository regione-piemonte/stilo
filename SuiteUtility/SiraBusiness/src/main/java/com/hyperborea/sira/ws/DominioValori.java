/**
 * DominioValori.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class DominioValori  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private com.hyperborea.sira.ws.ClassiValori[] classiValoris;

    private java.lang.String descrizione;

    private java.lang.String formaDominio;

    private java.lang.Integer idDominio;

    private com.hyperborea.sira.ws.IntervalloValori[] intervalloValoris;

    private java.lang.Float valore;

    private com.hyperborea.sira.ws.WsRiferimentiNormativiRef wsRiferimentiNormativiRef;

    public DominioValori() {
    }

    public DominioValori(
           com.hyperborea.sira.ws.ClassiValori[] classiValoris,
           java.lang.String descrizione,
           java.lang.String formaDominio,
           java.lang.Integer idDominio,
           com.hyperborea.sira.ws.IntervalloValori[] intervalloValoris,
           java.lang.Float valore,
           com.hyperborea.sira.ws.WsRiferimentiNormativiRef wsRiferimentiNormativiRef) {
        this.classiValoris = classiValoris;
        this.descrizione = descrizione;
        this.formaDominio = formaDominio;
        this.idDominio = idDominio;
        this.intervalloValoris = intervalloValoris;
        this.valore = valore;
        this.wsRiferimentiNormativiRef = wsRiferimentiNormativiRef;
    }


    /**
     * Gets the classiValoris value for this DominioValori.
     * 
     * @return classiValoris
     */
    public com.hyperborea.sira.ws.ClassiValori[] getClassiValoris() {
        return classiValoris;
    }


    /**
     * Sets the classiValoris value for this DominioValori.
     * 
     * @param classiValoris
     */
    public void setClassiValoris(com.hyperborea.sira.ws.ClassiValori[] classiValoris) {
        this.classiValoris = classiValoris;
    }

    public com.hyperborea.sira.ws.ClassiValori getClassiValoris(int i) {
        return this.classiValoris[i];
    }

    public void setClassiValoris(int i, com.hyperborea.sira.ws.ClassiValori _value) {
        this.classiValoris[i] = _value;
    }


    /**
     * Gets the descrizione value for this DominioValori.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this DominioValori.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the formaDominio value for this DominioValori.
     * 
     * @return formaDominio
     */
    public java.lang.String getFormaDominio() {
        return formaDominio;
    }


    /**
     * Sets the formaDominio value for this DominioValori.
     * 
     * @param formaDominio
     */
    public void setFormaDominio(java.lang.String formaDominio) {
        this.formaDominio = formaDominio;
    }


    /**
     * Gets the idDominio value for this DominioValori.
     * 
     * @return idDominio
     */
    public java.lang.Integer getIdDominio() {
        return idDominio;
    }


    /**
     * Sets the idDominio value for this DominioValori.
     * 
     * @param idDominio
     */
    public void setIdDominio(java.lang.Integer idDominio) {
        this.idDominio = idDominio;
    }


    /**
     * Gets the intervalloValoris value for this DominioValori.
     * 
     * @return intervalloValoris
     */
    public com.hyperborea.sira.ws.IntervalloValori[] getIntervalloValoris() {
        return intervalloValoris;
    }


    /**
     * Sets the intervalloValoris value for this DominioValori.
     * 
     * @param intervalloValoris
     */
    public void setIntervalloValoris(com.hyperborea.sira.ws.IntervalloValori[] intervalloValoris) {
        this.intervalloValoris = intervalloValoris;
    }

    public com.hyperborea.sira.ws.IntervalloValori getIntervalloValoris(int i) {
        return this.intervalloValoris[i];
    }

    public void setIntervalloValoris(int i, com.hyperborea.sira.ws.IntervalloValori _value) {
        this.intervalloValoris[i] = _value;
    }


    /**
     * Gets the valore value for this DominioValori.
     * 
     * @return valore
     */
    public java.lang.Float getValore() {
        return valore;
    }


    /**
     * Sets the valore value for this DominioValori.
     * 
     * @param valore
     */
    public void setValore(java.lang.Float valore) {
        this.valore = valore;
    }


    /**
     * Gets the wsRiferimentiNormativiRef value for this DominioValori.
     * 
     * @return wsRiferimentiNormativiRef
     */
    public com.hyperborea.sira.ws.WsRiferimentiNormativiRef getWsRiferimentiNormativiRef() {
        return wsRiferimentiNormativiRef;
    }


    /**
     * Sets the wsRiferimentiNormativiRef value for this DominioValori.
     * 
     * @param wsRiferimentiNormativiRef
     */
    public void setWsRiferimentiNormativiRef(com.hyperborea.sira.ws.WsRiferimentiNormativiRef wsRiferimentiNormativiRef) {
        this.wsRiferimentiNormativiRef = wsRiferimentiNormativiRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DominioValori)) return false;
        DominioValori other = (DominioValori) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.classiValoris==null && other.getClassiValoris()==null) || 
             (this.classiValoris!=null &&
              java.util.Arrays.equals(this.classiValoris, other.getClassiValoris()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.formaDominio==null && other.getFormaDominio()==null) || 
             (this.formaDominio!=null &&
              this.formaDominio.equals(other.getFormaDominio()))) &&
            ((this.idDominio==null && other.getIdDominio()==null) || 
             (this.idDominio!=null &&
              this.idDominio.equals(other.getIdDominio()))) &&
            ((this.intervalloValoris==null && other.getIntervalloValoris()==null) || 
             (this.intervalloValoris!=null &&
              java.util.Arrays.equals(this.intervalloValoris, other.getIntervalloValoris()))) &&
            ((this.valore==null && other.getValore()==null) || 
             (this.valore!=null &&
              this.valore.equals(other.getValore()))) &&
            ((this.wsRiferimentiNormativiRef==null && other.getWsRiferimentiNormativiRef()==null) || 
             (this.wsRiferimentiNormativiRef!=null &&
              this.wsRiferimentiNormativiRef.equals(other.getWsRiferimentiNormativiRef())));
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
        if (getClassiValoris() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getClassiValoris());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getClassiValoris(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getFormaDominio() != null) {
            _hashCode += getFormaDominio().hashCode();
        }
        if (getIdDominio() != null) {
            _hashCode += getIdDominio().hashCode();
        }
        if (getIntervalloValoris() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getIntervalloValoris());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getIntervalloValoris(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getValore() != null) {
            _hashCode += getValore().hashCode();
        }
        if (getWsRiferimentiNormativiRef() != null) {
            _hashCode += getWsRiferimentiNormativiRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DominioValori.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "dominioValori"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("classiValoris");
        elemField.setXmlName(new javax.xml.namespace.QName("", "classiValoris"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "classiValori"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("formaDominio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "formaDominio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDominio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDominio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("intervalloValoris");
        elemField.setXmlName(new javax.xml.namespace.QName("", "intervalloValoris"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "intervalloValori"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "valore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsRiferimentiNormativiRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsRiferimentiNormativiRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsRiferimentiNormativiRef"));
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
