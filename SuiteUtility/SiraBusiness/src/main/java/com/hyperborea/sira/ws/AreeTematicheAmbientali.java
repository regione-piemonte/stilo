/**
 * AreeTematicheAmbientali.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AreeTematicheAmbientali  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private int attivo;

    private int blocco;

    private java.lang.Integer codice;

    private java.lang.String descrizione;

    private com.hyperborea.sira.ws.RaggruppamentiAta[] raggruppamentiAtas;

    public AreeTematicheAmbientali() {
    }

    public AreeTematicheAmbientali(
           int attivo,
           int blocco,
           java.lang.Integer codice,
           java.lang.String descrizione,
           com.hyperborea.sira.ws.RaggruppamentiAta[] raggruppamentiAtas) {
        this.attivo = attivo;
        this.blocco = blocco;
        this.codice = codice;
        this.descrizione = descrizione;
        this.raggruppamentiAtas = raggruppamentiAtas;
    }


    /**
     * Gets the attivo value for this AreeTematicheAmbientali.
     * 
     * @return attivo
     */
    public int getAttivo() {
        return attivo;
    }


    /**
     * Sets the attivo value for this AreeTematicheAmbientali.
     * 
     * @param attivo
     */
    public void setAttivo(int attivo) {
        this.attivo = attivo;
    }


    /**
     * Gets the blocco value for this AreeTematicheAmbientali.
     * 
     * @return blocco
     */
    public int getBlocco() {
        return blocco;
    }


    /**
     * Sets the blocco value for this AreeTematicheAmbientali.
     * 
     * @param blocco
     */
    public void setBlocco(int blocco) {
        this.blocco = blocco;
    }


    /**
     * Gets the codice value for this AreeTematicheAmbientali.
     * 
     * @return codice
     */
    public java.lang.Integer getCodice() {
        return codice;
    }


    /**
     * Sets the codice value for this AreeTematicheAmbientali.
     * 
     * @param codice
     */
    public void setCodice(java.lang.Integer codice) {
        this.codice = codice;
    }


    /**
     * Gets the descrizione value for this AreeTematicheAmbientali.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this AreeTematicheAmbientali.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the raggruppamentiAtas value for this AreeTematicheAmbientali.
     * 
     * @return raggruppamentiAtas
     */
    public com.hyperborea.sira.ws.RaggruppamentiAta[] getRaggruppamentiAtas() {
        return raggruppamentiAtas;
    }


    /**
     * Sets the raggruppamentiAtas value for this AreeTematicheAmbientali.
     * 
     * @param raggruppamentiAtas
     */
    public void setRaggruppamentiAtas(com.hyperborea.sira.ws.RaggruppamentiAta[] raggruppamentiAtas) {
        this.raggruppamentiAtas = raggruppamentiAtas;
    }

    public com.hyperborea.sira.ws.RaggruppamentiAta getRaggruppamentiAtas(int i) {
        return this.raggruppamentiAtas[i];
    }

    public void setRaggruppamentiAtas(int i, com.hyperborea.sira.ws.RaggruppamentiAta _value) {
        this.raggruppamentiAtas[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AreeTematicheAmbientali)) return false;
        AreeTematicheAmbientali other = (AreeTematicheAmbientali) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            this.attivo == other.getAttivo() &&
            this.blocco == other.getBlocco() &&
            ((this.codice==null && other.getCodice()==null) || 
             (this.codice!=null &&
              this.codice.equals(other.getCodice()))) &&
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.raggruppamentiAtas==null && other.getRaggruppamentiAtas()==null) || 
             (this.raggruppamentiAtas!=null &&
              java.util.Arrays.equals(this.raggruppamentiAtas, other.getRaggruppamentiAtas())));
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
        _hashCode += getAttivo();
        _hashCode += getBlocco();
        if (getCodice() != null) {
            _hashCode += getCodice().hashCode();
        }
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getRaggruppamentiAtas() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRaggruppamentiAtas());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRaggruppamentiAtas(), i);
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
        new org.apache.axis.description.TypeDesc(AreeTematicheAmbientali.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "areeTematicheAmbientali"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "attivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("blocco");
        elemField.setXmlName(new javax.xml.namespace.QName("", "blocco"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
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
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("raggruppamentiAtas");
        elemField.setXmlName(new javax.xml.namespace.QName("", "raggruppamentiAtas"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "raggruppamentiAta"));
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
