/**
 * ProdEffZooTabellaB.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class ProdEffZooTabellaB  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idTabellaB;

    private int numeroProgCse;

    private float superficieStoccaggio;

    private com.hyperborea.sira.ws.UtilEffZooTabellaQ[] utilEffZooTabellaQs;

    private com.hyperborea.sira.ws.VocTipologiaContenitore vocTipologiaContenitore;

    private float volumeStoccaggio;

    public ProdEffZooTabellaB() {
    }

    public ProdEffZooTabellaB(
           java.lang.String descrizione,
           java.lang.Integer idTabellaB,
           int numeroProgCse,
           float superficieStoccaggio,
           com.hyperborea.sira.ws.UtilEffZooTabellaQ[] utilEffZooTabellaQs,
           com.hyperborea.sira.ws.VocTipologiaContenitore vocTipologiaContenitore,
           float volumeStoccaggio) {
        this.descrizione = descrizione;
        this.idTabellaB = idTabellaB;
        this.numeroProgCse = numeroProgCse;
        this.superficieStoccaggio = superficieStoccaggio;
        this.utilEffZooTabellaQs = utilEffZooTabellaQs;
        this.vocTipologiaContenitore = vocTipologiaContenitore;
        this.volumeStoccaggio = volumeStoccaggio;
    }


    /**
     * Gets the descrizione value for this ProdEffZooTabellaB.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this ProdEffZooTabellaB.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idTabellaB value for this ProdEffZooTabellaB.
     * 
     * @return idTabellaB
     */
    public java.lang.Integer getIdTabellaB() {
        return idTabellaB;
    }


    /**
     * Sets the idTabellaB value for this ProdEffZooTabellaB.
     * 
     * @param idTabellaB
     */
    public void setIdTabellaB(java.lang.Integer idTabellaB) {
        this.idTabellaB = idTabellaB;
    }


    /**
     * Gets the numeroProgCse value for this ProdEffZooTabellaB.
     * 
     * @return numeroProgCse
     */
    public int getNumeroProgCse() {
        return numeroProgCse;
    }


    /**
     * Sets the numeroProgCse value for this ProdEffZooTabellaB.
     * 
     * @param numeroProgCse
     */
    public void setNumeroProgCse(int numeroProgCse) {
        this.numeroProgCse = numeroProgCse;
    }


    /**
     * Gets the superficieStoccaggio value for this ProdEffZooTabellaB.
     * 
     * @return superficieStoccaggio
     */
    public float getSuperficieStoccaggio() {
        return superficieStoccaggio;
    }


    /**
     * Sets the superficieStoccaggio value for this ProdEffZooTabellaB.
     * 
     * @param superficieStoccaggio
     */
    public void setSuperficieStoccaggio(float superficieStoccaggio) {
        this.superficieStoccaggio = superficieStoccaggio;
    }


    /**
     * Gets the utilEffZooTabellaQs value for this ProdEffZooTabellaB.
     * 
     * @return utilEffZooTabellaQs
     */
    public com.hyperborea.sira.ws.UtilEffZooTabellaQ[] getUtilEffZooTabellaQs() {
        return utilEffZooTabellaQs;
    }


    /**
     * Sets the utilEffZooTabellaQs value for this ProdEffZooTabellaB.
     * 
     * @param utilEffZooTabellaQs
     */
    public void setUtilEffZooTabellaQs(com.hyperborea.sira.ws.UtilEffZooTabellaQ[] utilEffZooTabellaQs) {
        this.utilEffZooTabellaQs = utilEffZooTabellaQs;
    }

    public com.hyperborea.sira.ws.UtilEffZooTabellaQ getUtilEffZooTabellaQs(int i) {
        return this.utilEffZooTabellaQs[i];
    }

    public void setUtilEffZooTabellaQs(int i, com.hyperborea.sira.ws.UtilEffZooTabellaQ _value) {
        this.utilEffZooTabellaQs[i] = _value;
    }


    /**
     * Gets the vocTipologiaContenitore value for this ProdEffZooTabellaB.
     * 
     * @return vocTipologiaContenitore
     */
    public com.hyperborea.sira.ws.VocTipologiaContenitore getVocTipologiaContenitore() {
        return vocTipologiaContenitore;
    }


    /**
     * Sets the vocTipologiaContenitore value for this ProdEffZooTabellaB.
     * 
     * @param vocTipologiaContenitore
     */
    public void setVocTipologiaContenitore(com.hyperborea.sira.ws.VocTipologiaContenitore vocTipologiaContenitore) {
        this.vocTipologiaContenitore = vocTipologiaContenitore;
    }


    /**
     * Gets the volumeStoccaggio value for this ProdEffZooTabellaB.
     * 
     * @return volumeStoccaggio
     */
    public float getVolumeStoccaggio() {
        return volumeStoccaggio;
    }


    /**
     * Sets the volumeStoccaggio value for this ProdEffZooTabellaB.
     * 
     * @param volumeStoccaggio
     */
    public void setVolumeStoccaggio(float volumeStoccaggio) {
        this.volumeStoccaggio = volumeStoccaggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProdEffZooTabellaB)) return false;
        ProdEffZooTabellaB other = (ProdEffZooTabellaB) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.descrizione==null && other.getDescrizione()==null) || 
             (this.descrizione!=null &&
              this.descrizione.equals(other.getDescrizione()))) &&
            ((this.idTabellaB==null && other.getIdTabellaB()==null) || 
             (this.idTabellaB!=null &&
              this.idTabellaB.equals(other.getIdTabellaB()))) &&
            this.numeroProgCse == other.getNumeroProgCse() &&
            this.superficieStoccaggio == other.getSuperficieStoccaggio() &&
            ((this.utilEffZooTabellaQs==null && other.getUtilEffZooTabellaQs()==null) || 
             (this.utilEffZooTabellaQs!=null &&
              java.util.Arrays.equals(this.utilEffZooTabellaQs, other.getUtilEffZooTabellaQs()))) &&
            ((this.vocTipologiaContenitore==null && other.getVocTipologiaContenitore()==null) || 
             (this.vocTipologiaContenitore!=null &&
              this.vocTipologiaContenitore.equals(other.getVocTipologiaContenitore()))) &&
            this.volumeStoccaggio == other.getVolumeStoccaggio();
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
        if (getDescrizione() != null) {
            _hashCode += getDescrizione().hashCode();
        }
        if (getIdTabellaB() != null) {
            _hashCode += getIdTabellaB().hashCode();
        }
        _hashCode += getNumeroProgCse();
        _hashCode += new Float(getSuperficieStoccaggio()).hashCode();
        if (getUtilEffZooTabellaQs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getUtilEffZooTabellaQs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getUtilEffZooTabellaQs(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getVocTipologiaContenitore() != null) {
            _hashCode += getVocTipologiaContenitore().hashCode();
        }
        _hashCode += new Float(getVolumeStoccaggio()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProdEffZooTabellaB.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "prodEffZooTabellaB"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaB");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaB"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProgCse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProgCse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("superficieStoccaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "superficieStoccaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("utilEffZooTabellaQs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "utilEffZooTabellaQs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaQ"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vocTipologiaContenitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "vocTipologiaContenitore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vocTipologiaContenitore"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeStoccaggio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeStoccaggio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
