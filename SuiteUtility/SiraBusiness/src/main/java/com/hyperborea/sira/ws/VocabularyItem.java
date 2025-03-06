/**
 * VocabularyItem.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class VocabularyItem  implements java.io.Serializable {
    private java.lang.Integer attivo;

    private java.lang.String codice;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.VocabularyItemExtra[] extras;

    private java.lang.Integer id;

    private java.lang.Integer ordine;

    public VocabularyItem() {
    }

    public VocabularyItem(
           java.lang.Integer attivo,
           java.lang.String codice,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.VocabularyItemExtra[] extras,
           java.lang.Integer id,
           java.lang.Integer ordine) {
           this.attivo = attivo;
           this.codice = codice;
           this.descrizione = descrizione;
           this.extras = extras;
           this.id = id;
           this.ordine = ordine;
    }


    /**
     * Gets the attivo value for this VocabularyItem.
     * 
     * @return attivo
     */
    public java.lang.Integer getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this VocabularyItem.
     * 
     * @param attivo
     */
    public void setAttivo(java.lang.Integer attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the codice value for this VocabularyItem.
     * 
     * @return codice
     */
    public java.lang.String getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this VocabularyItem.
     * 
     * @param codice
     */
    public void setCodice(java.lang.String codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this VocabularyItem.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this VocabularyItem.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the extras value for this VocabularyItem.
     * 
     * @return extras
     */
    public com.hyperborea.sira.ws.VocabularyItemExtra[] getExtras() {
        return extras;
    }


    /**
     * Sets the extras value for this VocabularyItem.
     * 
     * @param extras
     */
    public void setExtras(com.hyperborea.sira.ws.VocabularyItemExtra[] extras) {
        this.extras = extras;
    }

    public com.hyperborea.sira.ws.VocabularyItemExtra getExtras(int i) {
        return this.extras[i];
    }

    public void setExtras(int i, com.hyperborea.sira.ws.VocabularyItemExtra _value) {
        this.extras[i] = _value;
    }


    /**
     * Gets the id value for this VocabularyItem.
     * 
     * @return id
     */
    public java.lang.Integer getId() {
        return id;
    }


    /**
     * Sets the id value for this VocabularyItem.
     * 
     * @param id
     */
    public void setId(java.lang.Integer id) {
        this.id = id;
    }


    /**
     * Gets the ordine value for this VocabularyItem.
     * 
     * @return ordine
     */
    public java.lang.Integer getOrdine() {
        return ordine;
    }


    /**
     * Sets the ordine value for this VocabularyItem.
     * 
     * @param ordine
     */
    public void setOrdine(java.lang.Integer ordine) {
        this.ordine = ordine;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VocabularyItem)) return false;
        VocabularyItem other = (VocabularyItem) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attivo==null && other.getAttivo()==null) || 
             (this.attivo!=null &&
              this.attivo.equals(other.getAttivo()))) &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.extras==null && other.getExtras()==null) || 
             (this.extras!=null &&
              java.util.Arrays.equals(this.extras, other.getExtras()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.ordine==null && other.getOrdine()==null) || 
             (this.ordine!=null &&
              this.ordine.equals(other.getOrdine())));
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
        if (getAttivo() != null) {
            _hashCode += getAttivo().hashCode();
        }
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getExtras() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExtras());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExtras(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getOrdine() != null) {
            _hashCode += getOrdine().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VocabularyItem.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyItem"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extras");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extras"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocabularyItemExtra"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ordine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ordine"));
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
