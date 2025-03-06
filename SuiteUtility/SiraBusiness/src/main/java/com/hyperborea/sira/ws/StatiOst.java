/**
 * StatiOst.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class StatiOst  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.util.Calendar dataFine;

    private com.hyperborea.sira.ws.StatiOstId id;

    private java.lang.String idUtente;

    private java.lang.String note;

    private com.hyperborea.sira.ws.TipologieFontiDati tipologieFontiDati;

    private com.hyperborea.sira.ws.WsFonteRef wsFonteRef;

    public StatiOst() {
    }

    public StatiOst(
           java.util.Calendar dataFine,
           com.hyperborea.sira.ws.StatiOstId id,
           java.lang.String idUtente,
           java.lang.String note,
           com.hyperborea.sira.ws.TipologieFontiDati tipologieFontiDati,
           com.hyperborea.sira.ws.WsFonteRef wsFonteRef) {
        this.dataFine = dataFine;
        this.id = id;
        this.idUtente = idUtente;
        this.note = note;
        this.tipologieFontiDati = tipologieFontiDati;
        this.wsFonteRef = wsFonteRef;
    }


    /**
     * Gets the dataFine value for this StatiOst.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this StatiOst.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the id value for this StatiOst.
     * 
     * @return id
     */
    public com.hyperborea.sira.ws.StatiOstId getId() {
        return id;
    }


    /**
     * Sets the id value for this StatiOst.
     * 
     * @param id
     */
    public void setId(com.hyperborea.sira.ws.StatiOstId id) {
        this.id = id;
    }


    /**
     * Gets the idUtente value for this StatiOst.
     * 
     * @return idUtente
     */
    public java.lang.String getIdUtente() {
        return idUtente;
    }


    /**
     * Sets the idUtente value for this StatiOst.
     * 
     * @param idUtente
     */
    public void setIdUtente(java.lang.String idUtente) {
        this.idUtente = idUtente;
    }


    /**
     * Gets the note value for this StatiOst.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this StatiOst.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the tipologieFontiDati value for this StatiOst.
     * 
     * @return tipologieFontiDati
     */
    public com.hyperborea.sira.ws.TipologieFontiDati getTipologieFontiDati() {
        return tipologieFontiDati;
    }


    /**
     * Sets the tipologieFontiDati value for this StatiOst.
     * 
     * @param tipologieFontiDati
     */
    public void setTipologieFontiDati(com.hyperborea.sira.ws.TipologieFontiDati tipologieFontiDati) {
        this.tipologieFontiDati = tipologieFontiDati;
    }


    /**
     * Gets the wsFonteRef value for this StatiOst.
     * 
     * @return wsFonteRef
     */
    public com.hyperborea.sira.ws.WsFonteRef getWsFonteRef() {
        return wsFonteRef;
    }


    /**
     * Sets the wsFonteRef value for this StatiOst.
     * 
     * @param wsFonteRef
     */
    public void setWsFonteRef(com.hyperborea.sira.ws.WsFonteRef wsFonteRef) {
        this.wsFonteRef = wsFonteRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof StatiOst)) return false;
        StatiOst other = (StatiOst) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.idUtente==null && other.getIdUtente()==null) || 
             (this.idUtente!=null &&
              this.idUtente.equals(other.getIdUtente()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.tipologieFontiDati==null && other.getTipologieFontiDati()==null) || 
             (this.tipologieFontiDati!=null &&
              this.tipologieFontiDati.equals(other.getTipologieFontiDati()))) &&
            ((this.wsFonteRef==null && other.getWsFonteRef()==null) || 
             (this.wsFonteRef!=null &&
              this.wsFonteRef.equals(other.getWsFonteRef())));
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
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIdUtente() != null) {
            _hashCode += getIdUtente().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getTipologieFontiDati() != null) {
            _hashCode += getTipologieFontiDati().hashCode();
        }
        if (getWsFonteRef() != null) {
            _hashCode += getWsFonteRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(StatiOst.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOst"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "statiOstId"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idUtente");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idUtente"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("note");
        elemField.setXmlName(new javax.xml.namespace.QName("", "note"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologieFontiDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologieFontiDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipologieFontiDati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsFonteRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsFonteRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFonteRef"));
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
