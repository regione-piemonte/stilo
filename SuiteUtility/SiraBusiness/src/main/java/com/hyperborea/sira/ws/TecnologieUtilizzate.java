/**
 * TecnologieUtilizzate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class TecnologieUtilizzate  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer concentrazioneResidue;

    private java.lang.Integer idTecnologia;

    private java.lang.String matrice;

    private java.lang.String note;

    private java.lang.Float supAreaSvincolata;

    private java.lang.String tecnologia;

    private java.lang.String tipoTecnica;

    private java.lang.Float volumeEffettivo;

    private java.lang.Float volumeProgetto;

    public TecnologieUtilizzate() {
    }

    public TecnologieUtilizzate(
           java.lang.Integer concentrazioneResidue,
           java.lang.Integer idTecnologia,
           java.lang.String matrice,
           java.lang.String note,
           java.lang.Float supAreaSvincolata,
           java.lang.String tecnologia,
           java.lang.String tipoTecnica,
           java.lang.Float volumeEffettivo,
           java.lang.Float volumeProgetto) {
        this.concentrazioneResidue = concentrazioneResidue;
        this.idTecnologia = idTecnologia;
        this.matrice = matrice;
        this.note = note;
        this.supAreaSvincolata = supAreaSvincolata;
        this.tecnologia = tecnologia;
        this.tipoTecnica = tipoTecnica;
        this.volumeEffettivo = volumeEffettivo;
        this.volumeProgetto = volumeProgetto;
    }


    /**
     * Gets the concentrazioneResidue value for this TecnologieUtilizzate.
     * 
     * @return concentrazioneResidue
     */
    public java.lang.Integer getConcentrazioneResidue() {
        return concentrazioneResidue;
    }


    /**
     * Sets the concentrazioneResidue value for this TecnologieUtilizzate.
     * 
     * @param concentrazioneResidue
     */
    public void setConcentrazioneResidue(java.lang.Integer concentrazioneResidue) {
        this.concentrazioneResidue = concentrazioneResidue;
    }


    /**
     * Gets the idTecnologia value for this TecnologieUtilizzate.
     * 
     * @return idTecnologia
     */
    public java.lang.Integer getIdTecnologia() {
        return idTecnologia;
    }


    /**
     * Sets the idTecnologia value for this TecnologieUtilizzate.
     * 
     * @param idTecnologia
     */
    public void setIdTecnologia(java.lang.Integer idTecnologia) {
        this.idTecnologia = idTecnologia;
    }


    /**
     * Gets the matrice value for this TecnologieUtilizzate.
     * 
     * @return matrice
     */
    public java.lang.String getMatrice() {
        return matrice;
    }


    /**
     * Sets the matrice value for this TecnologieUtilizzate.
     * 
     * @param matrice
     */
    public void setMatrice(java.lang.String matrice) {
        this.matrice = matrice;
    }


    /**
     * Gets the note value for this TecnologieUtilizzate.
     * 
     * @return note
     */
    public java.lang.String getNote() {
        return note;
    }


    /**
     * Sets the note value for this TecnologieUtilizzate.
     * 
     * @param note
     */
    public void setNote(java.lang.String note) {
        this.note = note;
    }


    /**
     * Gets the supAreaSvincolata value for this TecnologieUtilizzate.
     * 
     * @return supAreaSvincolata
     */
    public java.lang.Float getSupAreaSvincolata() {
        return supAreaSvincolata;
    }


    /**
     * Sets the supAreaSvincolata value for this TecnologieUtilizzate.
     * 
     * @param supAreaSvincolata
     */
    public void setSupAreaSvincolata(java.lang.Float supAreaSvincolata) {
        this.supAreaSvincolata = supAreaSvincolata;
    }


    /**
     * Gets the tecnologia value for this TecnologieUtilizzate.
     * 
     * @return tecnologia
     */
    public java.lang.String getTecnologia() {
        return tecnologia;
    }


    /**
     * Sets the tecnologia value for this TecnologieUtilizzate.
     * 
     * @param tecnologia
     */
    public void setTecnologia(java.lang.String tecnologia) {
        this.tecnologia = tecnologia;
    }


    /**
     * Gets the tipoTecnica value for this TecnologieUtilizzate.
     * 
     * @return tipoTecnica
     */
    public java.lang.String getTipoTecnica() {
        return tipoTecnica;
    }


    /**
     * Sets the tipoTecnica value for this TecnologieUtilizzate.
     * 
     * @param tipoTecnica
     */
    public void setTipoTecnica(java.lang.String tipoTecnica) {
        this.tipoTecnica = tipoTecnica;
    }


    /**
     * Gets the volumeEffettivo value for this TecnologieUtilizzate.
     * 
     * @return volumeEffettivo
     */
    public java.lang.Float getVolumeEffettivo() {
        return volumeEffettivo;
    }


    /**
     * Sets the volumeEffettivo value for this TecnologieUtilizzate.
     * 
     * @param volumeEffettivo
     */
    public void setVolumeEffettivo(java.lang.Float volumeEffettivo) {
        this.volumeEffettivo = volumeEffettivo;
    }


    /**
     * Gets the volumeProgetto value for this TecnologieUtilizzate.
     * 
     * @return volumeProgetto
     */
    public java.lang.Float getVolumeProgetto() {
        return volumeProgetto;
    }


    /**
     * Sets the volumeProgetto value for this TecnologieUtilizzate.
     * 
     * @param volumeProgetto
     */
    public void setVolumeProgetto(java.lang.Float volumeProgetto) {
        this.volumeProgetto = volumeProgetto;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TecnologieUtilizzate)) return false;
        TecnologieUtilizzate other = (TecnologieUtilizzate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.concentrazioneResidue==null && other.getConcentrazioneResidue()==null) || 
             (this.concentrazioneResidue!=null &&
              this.concentrazioneResidue.equals(other.getConcentrazioneResidue()))) &&
            ((this.idTecnologia==null && other.getIdTecnologia()==null) || 
             (this.idTecnologia!=null &&
              this.idTecnologia.equals(other.getIdTecnologia()))) &&
            ((this.matrice==null && other.getMatrice()==null) || 
             (this.matrice!=null &&
              this.matrice.equals(other.getMatrice()))) &&
            ((this.note==null && other.getNote()==null) || 
             (this.note!=null &&
              this.note.equals(other.getNote()))) &&
            ((this.supAreaSvincolata==null && other.getSupAreaSvincolata()==null) || 
             (this.supAreaSvincolata!=null &&
              this.supAreaSvincolata.equals(other.getSupAreaSvincolata()))) &&
            ((this.tecnologia==null && other.getTecnologia()==null) || 
             (this.tecnologia!=null &&
              this.tecnologia.equals(other.getTecnologia()))) &&
            ((this.tipoTecnica==null && other.getTipoTecnica()==null) || 
             (this.tipoTecnica!=null &&
              this.tipoTecnica.equals(other.getTipoTecnica()))) &&
            ((this.volumeEffettivo==null && other.getVolumeEffettivo()==null) || 
             (this.volumeEffettivo!=null &&
              this.volumeEffettivo.equals(other.getVolumeEffettivo()))) &&
            ((this.volumeProgetto==null && other.getVolumeProgetto()==null) || 
             (this.volumeProgetto!=null &&
              this.volumeProgetto.equals(other.getVolumeProgetto())));
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
        if (getConcentrazioneResidue() != null) {
            _hashCode += getConcentrazioneResidue().hashCode();
        }
        if (getIdTecnologia() != null) {
            _hashCode += getIdTecnologia().hashCode();
        }
        if (getMatrice() != null) {
            _hashCode += getMatrice().hashCode();
        }
        if (getNote() != null) {
            _hashCode += getNote().hashCode();
        }
        if (getSupAreaSvincolata() != null) {
            _hashCode += getSupAreaSvincolata().hashCode();
        }
        if (getTecnologia() != null) {
            _hashCode += getTecnologia().hashCode();
        }
        if (getTipoTecnica() != null) {
            _hashCode += getTipoTecnica().hashCode();
        }
        if (getVolumeEffettivo() != null) {
            _hashCode += getVolumeEffettivo().hashCode();
        }
        if (getVolumeProgetto() != null) {
            _hashCode += getVolumeProgetto().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TecnologieUtilizzate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tecnologieUtilizzate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("concentrazioneResidue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "concentrazioneResidue"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTecnologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTecnologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "matrice"));
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
        elemField.setFieldName("supAreaSvincolata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supAreaSvincolata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tecnologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tecnologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipoTecnica");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipoTecnica"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeEffettivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeEffettivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeProgetto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeProgetto"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
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
