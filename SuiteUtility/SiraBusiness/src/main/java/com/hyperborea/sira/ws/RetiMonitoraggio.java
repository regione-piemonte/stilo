/**
 * RetiMonitoraggio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class RetiMonitoraggio  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.String finalita;

    private java.lang.Integer idRete;

    private java.lang.String note;

    private java.lang.String oggetto;

    private com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi;

    private com.hyperborea.sira.ws.TemiAmbientali temiAmbientali;

    public RetiMonitoraggio() {
    }

    public RetiMonitoraggio(
           java.lang.String finalita,
           java.lang.Integer idRete,
           java.lang.String note,
           java.lang.String oggetto,
           com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi,
           com.hyperborea.sira.ws.TemiAmbientali temiAmbientali) {
        this.finalita = finalita;
        this.idRete = idRete;
        this.note = note;
        this.oggetto = oggetto;
        this.riferimentiNormativi = riferimentiNormativi;
        this.temiAmbientali = temiAmbientali;
    }


    /**
     * Gets the finalita value for this RetiMonitoraggio.
     * 
     * @return finalita
     */
    public java.lang.String getFinalita() {
        return finalita;
    }


    /**
     * Sets the finalita value for this RetiMonitoraggio.
     * 
     * @param finalita
     */
    public void setFinalita(java.lang.String finalita) {
        this.finalita = finalita;
    }


    /**
     * Gets the idRete value for this RetiMonitoraggio.
     * 
     * @return idRete
     */
    public java.lang.Integer getIdRete() {
        return idRete;
    }


    /**
     * Sets the idRete value for this RetiMonitoraggio.
     * 
     * @param idRete
     */
    public void setIdRete(java.lang.Integer idRete) {
        this.idRete = idRete;
    }


    /**
     * Gets the note value for this RetiMonitoraggio.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this RetiMonitoraggio.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the oggetto value for this RetiMonitoraggio.
     * 
     * @return oggetto
     */
    public java.lang.String getOggetto() {
        return oggetto;
    }


    /**
     * Sets the oggetto value for this RetiMonitoraggio.
     * 
     * @param oggetto
     */
    public void setOggetto(java.lang.String oggetto) {
        this.oggetto = oggetto;
    }


    /**
     * Gets the riferimentiNormativi value for this RetiMonitoraggio.
     * 
     * @return riferimentiNormativi
     */
    public com.hyperborea.sira.ws.RiferimentiNormativi getRiferimentiNormativi() {
        return riferimentiNormativi;
    }


    /**
     * Sets the riferimentiNormativi value for this RetiMonitoraggio.
     * 
     * @param riferimentiNormativi
     */
    public void setRiferimentiNormativi(com.hyperborea.sira.ws.RiferimentiNormativi riferimentiNormativi) {
        this.riferimentiNormativi = riferimentiNormativi;
    }


    /**
     * Gets the temiAmbientali value for this RetiMonitoraggio.
     * 
     * @return temiAmbientali
     */
    public com.hyperborea.sira.ws.TemiAmbientali getTemiAmbientali() {
        return temiAmbientali;
    }


    /**
     * Sets the temiAmbientali value for this RetiMonitoraggio.
     * 
     * @param temiAmbientali
     */
    public void setTemiAmbientali(com.hyperborea.sira.ws.TemiAmbientali temiAmbientali) {
        this.temiAmbientali = temiAmbientali;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RetiMonitoraggio)) return false;
        RetiMonitoraggio other = (RetiMonitoraggio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.finalita==null && other.getFinalita()==null) || 
             (this.finalita!=null &&
              this.finalita.equals(other.getFinalita()))) &&
            ((this.idRete==null && other.getIdRete()==null) || 
             (this.idRete!=null &&
              this.idRete.equals(other.getIdRete()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.oggetto==null && other.getOggetto()==null) || 
             (this.oggetto!=null &&
              this.oggetto.equals(other.getOggetto()))) &&
            ((this.riferimentiNormativi==null && other.getRiferimentiNormativi()==null) || 
             (this.riferimentiNormativi!=null &&
              this.riferimentiNormativi.equals(other.getRiferimentiNormativi()))) &&
            ((this.temiAmbientali==null && other.getTemiAmbientali()==null) || 
             (this.temiAmbientali!=null &&
              this.temiAmbientali.equals(other.getTemiAmbientali())));
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
        if (getFinalita() != null) {
            _hashCode += getFinalita().hashCode();
        }
        if (getIdRete() != null) {
            _hashCode += getIdRete().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getOggetto() != null) {
            _hashCode += getOggetto().hashCode();
        }
        if (getRiferimentiNormativi() != null) {
            _hashCode += getRiferimentiNormativi().hashCode();
        }
        if (getTemiAmbientali() != null) {
            _hashCode += getTemiAmbientali().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RetiMonitoraggio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "retiMonitoraggio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("finalita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "finalita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idRete");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idRete"));
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
        elemField.setFieldName("oggetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "oggetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("riferimentiNormativi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "riferimentiNormativi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "riferimentiNormativi"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("temiAmbientali");
        elemField.setXmlName(new javax.xml.namespace.QName("", "temiAmbientali"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "temiAmbientali"));
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
