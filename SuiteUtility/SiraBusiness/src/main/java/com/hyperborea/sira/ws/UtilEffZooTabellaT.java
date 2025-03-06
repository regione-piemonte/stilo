/**
 * UtilEffZooTabellaT.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class UtilEffZooTabellaT  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String descrizione;

    private java.lang.Integer idTabellaT;

    private int numeroProgCsa;

    private java.lang.String tipologiaContenitore;

    private float volumeStoccaggio;

    public UtilEffZooTabellaT() {
    }

    public UtilEffZooTabellaT(
           java.lang.String descrizione,
           java.lang.Integer idTabellaT,
           int numeroProgCsa,
           java.lang.String tipologiaContenitore,
           float volumeStoccaggio) {
        this.descrizione = descrizione;
        this.idTabellaT = idTabellaT;
        this.numeroProgCsa = numeroProgCsa;
        this.tipologiaContenitore = tipologiaContenitore;
        this.volumeStoccaggio = volumeStoccaggio;
    }


    /**
     * Gets the descrizione value for this UtilEffZooTabellaT.
     * 
     * @return descrizione
     */
    public java.lang.String getDescrizione() {
        return descrizione;
    }


    /**
     * Sets the descrizione value for this UtilEffZooTabellaT.
     * 
     * @param descrizione
     */
    public void setDescrizione(java.lang.String descrizione) {
        this.descrizione = descrizione;
    }


    /**
     * Gets the idTabellaT value for this UtilEffZooTabellaT.
     * 
     * @return idTabellaT
     */
    public java.lang.Integer getIdTabellaT() {
        return idTabellaT;
    }


    /**
     * Sets the idTabellaT value for this UtilEffZooTabellaT.
     * 
     * @param idTabellaT
     */
    public void setIdTabellaT(java.lang.Integer idTabellaT) {
        this.idTabellaT = idTabellaT;
    }


    /**
     * Gets the numeroProgCsa value for this UtilEffZooTabellaT.
     * 
     * @return numeroProgCsa
     */
    public int getNumeroProgCsa() {
        return numeroProgCsa;
    }


    /**
     * Sets the numeroProgCsa value for this UtilEffZooTabellaT.
     * 
     * @param numeroProgCsa
     */
    public void setNumeroProgCsa(int numeroProgCsa) {
        this.numeroProgCsa = numeroProgCsa;
    }


    /**
     * Gets the tipologiaContenitore value for this UtilEffZooTabellaT.
     * 
     * @return tipologiaContenitore
     */
    public java.lang.String getTipologiaContenitore() {
        return tipologiaContenitore;
    }


    /**
     * Sets the tipologiaContenitore value for this UtilEffZooTabellaT.
     * 
     * @param tipologiaContenitore
     */
    public void setTipologiaContenitore(java.lang.String tipologiaContenitore) {
        this.tipologiaContenitore = tipologiaContenitore;
    }


    /**
     * Gets the volumeStoccaggio value for this UtilEffZooTabellaT.
     * 
     * @return volumeStoccaggio
     */
    public float getVolumeStoccaggio() {
        return volumeStoccaggio;
    }


    /**
     * Sets the volumeStoccaggio value for this UtilEffZooTabellaT.
     * 
     * @param volumeStoccaggio
     */
    public void setVolumeStoccaggio(float volumeStoccaggio) {
        this.volumeStoccaggio = volumeStoccaggio;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof UtilEffZooTabellaT)) return false;
        UtilEffZooTabellaT other = (UtilEffZooTabellaT) obj;
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
            ((this.idTabellaT==null && other.getIdTabellaT()==null) || 
             (this.idTabellaT!=null &&
              this.idTabellaT.equals(other.getIdTabellaT()))) &&
            this.numeroProgCsa == other.getNumeroProgCsa() &&
            ((this.tipologiaContenitore==null && other.getTipologiaContenitore()==null) || 
             (this.tipologiaContenitore!=null &&
              this.tipologiaContenitore.equals(other.getTipologiaContenitore()))) &&
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
        if (getIdTabellaT() != null) {
            _hashCode += getIdTabellaT().hashCode();
        }
        _hashCode += getNumeroProgCsa();
        if (getTipologiaContenitore() != null) {
            _hashCode += getTipologiaContenitore().hashCode();
        }
        _hashCode += new Float(getVolumeStoccaggio()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(UtilEffZooTabellaT.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "utilEffZooTabellaT"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descrizione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "descrizione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTabellaT");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTabellaT"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numeroProgCsa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numeroProgCsa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologiaContenitore");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologiaContenitore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
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
