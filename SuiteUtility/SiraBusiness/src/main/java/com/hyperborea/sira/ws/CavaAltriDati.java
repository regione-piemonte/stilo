/**
 * CavaAltriDati.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CavaAltriDati  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer anno;

    private java.lang.Integer idCavaAltriDati;

    private java.lang.Float supOccupata;

    private java.lang.Float supRipristino;

    private java.lang.Float volumeOccupato;

    public CavaAltriDati() {
    }

    public CavaAltriDati(
           java.lang.Integer anno,
           java.lang.Integer idCavaAltriDati,
           java.lang.Float supOccupata,
           java.lang.Float supRipristino,
           java.lang.Float volumeOccupato) {
        this.anno = anno;
        this.idCavaAltriDati = idCavaAltriDati;
        this.supOccupata = supOccupata;
        this.supRipristino = supRipristino;
        this.volumeOccupato = volumeOccupato;
    }


    /**
     * Gets the anno value for this CavaAltriDati.
     * 
     * @return anno
     */
    public java.lang.Integer getAnno() {
        return anno;
    }


    /**
     * Sets the anno value for this CavaAltriDati.
     * 
     * @param anno
     */
    public void setAnno(java.lang.Integer anno) {
        this.anno = anno;
    }


    /**
     * Gets the idCavaAltriDati value for this CavaAltriDati.
     * 
     * @return idCavaAltriDati
     */
    public java.lang.Integer getIdCavaAltriDati() {
        return idCavaAltriDati;
    }


    /**
     * Sets the idCavaAltriDati value for this CavaAltriDati.
     * 
     * @param idCavaAltriDati
     */
    public void setIdCavaAltriDati(java.lang.Integer idCavaAltriDati) {
        this.idCavaAltriDati = idCavaAltriDati;
    }


    /**
     * Gets the supOccupata value for this CavaAltriDati.
     * 
     * @return supOccupata
     */
    public java.lang.Float getSupOccupata() {
        return supOccupata;
    }


    /**
     * Sets the supOccupata value for this CavaAltriDati.
     * 
     * @param supOccupata
     */
    public void setSupOccupata(java.lang.Float supOccupata) {
        this.supOccupata = supOccupata;
    }


    /**
     * Gets the supRipristino value for this CavaAltriDati.
     * 
     * @return supRipristino
     */
    public java.lang.Float getSupRipristino() {
        return supRipristino;
    }


    /**
     * Sets the supRipristino value for this CavaAltriDati.
     * 
     * @param supRipristino
     */
    public void setSupRipristino(java.lang.Float supRipristino) {
        this.supRipristino = supRipristino;
    }


    /**
     * Gets the volumeOccupato value for this CavaAltriDati.
     * 
     * @return volumeOccupato
     */
    public java.lang.Float getVolumeOccupato() {
        return volumeOccupato;
    }


    /**
     * Sets the volumeOccupato value for this CavaAltriDati.
     * 
     * @param volumeOccupato
     */
    public void setVolumeOccupato(java.lang.Float volumeOccupato) {
        this.volumeOccupato = volumeOccupato;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CavaAltriDati)) return false;
        CavaAltriDati other = (CavaAltriDati) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.anno==null && other.getAnno()==null) || 
             (this.anno!=null &&
              this.anno.equals(other.getAnno()))) &&
            ((this.idCavaAltriDati==null && other.getIdCavaAltriDati()==null) || 
             (this.idCavaAltriDati!=null &&
              this.idCavaAltriDati.equals(other.getIdCavaAltriDati()))) &&
            ((this.supOccupata==null && other.getSupOccupata()==null) || 
             (this.supOccupata!=null &&
              this.supOccupata.equals(other.getSupOccupata()))) &&
            ((this.supRipristino==null && other.getSupRipristino()==null) || 
             (this.supRipristino!=null &&
              this.supRipristino.equals(other.getSupRipristino()))) &&
            ((this.volumeOccupato==null && other.getVolumeOccupato()==null) || 
             (this.volumeOccupato!=null &&
              this.volumeOccupato.equals(other.getVolumeOccupato())));
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
        if (getAnno() != null) {
            _hashCode += getAnno().hashCode();
        }
        if (getIdCavaAltriDati() != null) {
            _hashCode += getIdCavaAltriDati().hashCode();
        }
        if (getSupOccupata() != null) {
            _hashCode += getSupOccupata().hashCode();
        }
        if (getSupRipristino() != null) {
            _hashCode += getSupRipristino().hashCode();
        }
        if (getVolumeOccupato() != null) {
            _hashCode += getVolumeOccupato().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CavaAltriDati.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "cavaAltriDati"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "anno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCavaAltriDati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCavaAltriDati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supOccupata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supOccupata"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("supRipristino");
        elemField.setXmlName(new javax.xml.namespace.QName("", "supRipristino"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("volumeOccupato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "volumeOccupato"));
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
