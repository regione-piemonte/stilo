/**
 * WsNewRostRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsNewRostRequest  implements java.io.Serializable {
    private java.util.Calendar dataFine;

    private java.util.Calendar dataInizio;

    private java.lang.Integer idTipologieRost;

    private java.lang.String note;

    private com.hyperborea.sira.ws.RelazioniOst relazioniOst;

    private com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef;

    private com.hyperborea.sira.ws.WsOstRef wsOstRefChild;

    private com.hyperborea.sira.ws.WsOstRef wsOstRefParent;

    public WsNewRostRequest() {
    }

    public WsNewRostRequest(
           java.util.Calendar dataFine,
           java.util.Calendar dataInizio,
           java.lang.Integer idTipologieRost,
           java.lang.String note,
           com.hyperborea.sira.ws.RelazioniOst relazioniOst,
           com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef,
           com.hyperborea.sira.ws.WsOstRef wsOstRefChild,
           com.hyperborea.sira.ws.WsOstRef wsOstRefParent) {
           this.dataFine = dataFine;
           this.dataInizio = dataInizio;
           this.idTipologieRost = idTipologieRost;
           this.note = note;
           this.relazioniOst = relazioniOst;
           this.wsFontiDatiRef = wsFontiDatiRef;
           this.wsOstRefChild = wsOstRefChild;
           this.wsOstRefParent = wsOstRefParent;
    }


    /**
     * Gets the dataFine value for this WsNewRostRequest.
     * 
     * @return dataFine
     */
    public java.util.Calendar getDataFine() {
        return dataFine;
    }


    /**
     * Sets the dataFine value for this WsNewRostRequest.
     * 
     * @param dataFine
     */
    public void setDataFine(java.util.Calendar dataFine) {
        this.dataFine = dataFine;
    }


    /**
     * Gets the dataInizio value for this WsNewRostRequest.
     * 
     * @return dataInizio
     */
    public java.util.Calendar getDataInizio() {
        return dataInizio;
    }


    /**
     * Sets the dataInizio value for this WsNewRostRequest.
     * 
     * @param dataInizio
     */
    public void setDataInizio(java.util.Calendar dataInizio) {
        this.dataInizio = dataInizio;
    }


    /**
     * Gets the idTipologieRost value for this WsNewRostRequest.
     * 
     * @return idTipologieRost
     */
    public java.lang.Integer getIdTipologieRost() {
        return idTipologieRost;
    }


    /**
     * Sets the idTipologieRost value for this WsNewRostRequest.
     * 
     * @param idTipologieRost
     */
    public void setIdTipologieRost(java.lang.Integer idTipologieRost) {
        this.idTipologieRost = idTipologieRost;
    }


    /**
     * Gets the note value for this WsNewRostRequest.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this WsNewRostRequest.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the relazioniOst value for this WsNewRostRequest.
     * 
     * @return relazioniOst
     */
    public com.hyperborea.sira.ws.RelazioniOst getRelazioniOst() {
        return relazioniOst;
    }


    /**
     * Sets the relazioniOst value for this WsNewRostRequest.
     * 
     * @param relazioniOst
     */
    public void setRelazioniOst(com.hyperborea.sira.ws.RelazioniOst relazioniOst) {
        this.relazioniOst = relazioniOst;
    }


    /**
     * Gets the wsFontiDatiRef value for this WsNewRostRequest.
     * 
     * @return wsFontiDatiRef
     */
    public com.hyperborea.sira.ws.WsFontiDatiRef getWsFontiDatiRef() {
        return wsFontiDatiRef;
    }


    /**
     * Sets the wsFontiDatiRef value for this WsNewRostRequest.
     * 
     * @param wsFontiDatiRef
     */
    public void setWsFontiDatiRef(com.hyperborea.sira.ws.WsFontiDatiRef wsFontiDatiRef) {
        this.wsFontiDatiRef = wsFontiDatiRef;
    }


    /**
     * Gets the wsOstRefChild value for this WsNewRostRequest.
     * 
     * @return wsOstRefChild
     */
    public com.hyperborea.sira.ws.WsOstRef getWsOstRefChild() {
        return wsOstRefChild;
    }


    /**
     * Sets the wsOstRefChild value for this WsNewRostRequest.
     * 
     * @param wsOstRefChild
     */
    public void setWsOstRefChild(com.hyperborea.sira.ws.WsOstRef wsOstRefChild) {
        this.wsOstRefChild = wsOstRefChild;
    }


    /**
     * Gets the wsOstRefParent value for this WsNewRostRequest.
     * 
     * @return wsOstRefParent
     */
    public com.hyperborea.sira.ws.WsOstRef getWsOstRefParent() {
        return wsOstRefParent;
    }


    /**
     * Sets the wsOstRefParent value for this WsNewRostRequest.
     * 
     * @param wsOstRefParent
     */
    public void setWsOstRefParent(com.hyperborea.sira.ws.WsOstRef wsOstRefParent) {
        this.wsOstRefParent = wsOstRefParent;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsNewRostRequest)) return false;
        WsNewRostRequest other = (WsNewRostRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataFine==null && other.getDataFine()==null) || 
             (this.dataFine!=null &&
              this.dataFine.equals(other.getDataFine()))) &&
            ((this.dataInizio==null && other.getDataInizio()==null) || 
             (this.dataInizio!=null &&
              this.dataInizio.equals(other.getDataInizio()))) &&
            ((this.idTipologieRost==null && other.getIdTipologieRost()==null) || 
             (this.idTipologieRost!=null &&
              this.idTipologieRost.equals(other.getIdTipologieRost()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.relazioniOst==null && other.getRelazioniOst()==null) || 
             (this.relazioniOst!=null &&
              this.relazioniOst.equals(other.getRelazioniOst()))) &&
            ((this.wsFontiDatiRef==null && other.getWsFontiDatiRef()==null) || 
             (this.wsFontiDatiRef!=null &&
              this.wsFontiDatiRef.equals(other.getWsFontiDatiRef()))) &&
            ((this.wsOstRefChild==null && other.getWsOstRefChild()==null) || 
             (this.wsOstRefChild!=null &&
              this.wsOstRefChild.equals(other.getWsOstRefChild()))) &&
            ((this.wsOstRefParent==null && other.getWsOstRefParent()==null) || 
             (this.wsOstRefParent!=null &&
              this.wsOstRefParent.equals(other.getWsOstRefParent())));
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
        if (getDataFine() != null) {
            _hashCode += getDataFine().hashCode();
        }
        if (getDataInizio() != null) {
            _hashCode += getDataInizio().hashCode();
        }
        if (getIdTipologieRost() != null) {
            _hashCode += getIdTipologieRost().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getRelazioniOst() != null) {
            _hashCode += getRelazioniOst().hashCode();
        }
        if (getWsFontiDatiRef() != null) {
            _hashCode += getWsFontiDatiRef().hashCode();
        }
        if (getWsOstRefChild() != null) {
            _hashCode += getWsOstRefChild().hashCode();
        }
        if (getWsOstRefParent() != null) {
            _hashCode += getWsOstRefParent().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsNewRostRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsNewRostRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipologieRost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipologieRost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
        elemField.setFieldName("relazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "relazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "relazioniOst"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsFontiDatiRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsFontiDatiRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsFontiDatiRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsOstRefChild");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsOstRefChild"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsOstRefParent");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsOstRefParent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsOstRef"));
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
