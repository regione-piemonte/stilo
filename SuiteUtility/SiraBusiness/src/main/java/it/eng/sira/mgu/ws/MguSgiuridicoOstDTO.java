/**
 * MguSgiuridicoOstDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.sira.mgu.ws;

public class MguSgiuridicoOstDTO  implements java.io.Serializable {
    private java.lang.Long id;

    private java.lang.Long idDominio;

    private java.lang.Long idOst;

    private java.lang.String tipologia;

    private java.lang.String titolo;

    public MguSgiuridicoOstDTO() {
    }

    public MguSgiuridicoOstDTO(
           java.lang.Long id,
           java.lang.Long idDominio,
           java.lang.Long idOst,
           java.lang.String tipologia,
           java.lang.String titolo) {
           this.id = id;
           this.idDominio = idDominio;
           this.idOst = idOst;
           this.tipologia = tipologia;
           this.titolo = titolo;
    }


    /**
     * Gets the id value for this MguSgiuridicoOstDTO.
     * 
     * @return id
     */
    public java.lang.Long getId() {
        return id;
    }


    /**
     * Sets the id value for this MguSgiuridicoOstDTO.
     * 
     * @param id
     */
    public void setId(java.lang.Long id) {
        this.id = id;
    }


    /**
     * Gets the idDominio value for this MguSgiuridicoOstDTO.
     * 
     * @return idDominio
     */
    public java.lang.Long getIdDominio() {
        return idDominio;
    }


    /**
     * Sets the idDominio value for this MguSgiuridicoOstDTO.
     * 
     * @param idDominio
     */
    public void setIdDominio(java.lang.Long idDominio) {
        this.idDominio = idDominio;
    }


    /**
     * Gets the idOst value for this MguSgiuridicoOstDTO.
     * 
     * @return idOst
     */
    public java.lang.Long getIdOst() {
        return idOst;
    }


    /**
     * Sets the idOst value for this MguSgiuridicoOstDTO.
     * 
     * @param idOst
     */
    public void setIdOst(java.lang.Long idOst) {
        this.idOst = idOst;
    }


    /**
     * Gets the tipologia value for this MguSgiuridicoOstDTO.
     * 
     * @return tipologia
     */
    public java.lang.String getTipologia() {
        return tipologia;
    }


    /**
     * Sets the tipologia value for this MguSgiuridicoOstDTO.
     * 
     * @param tipologia
     */
    public void setTipologia(java.lang.String tipologia) {
        this.tipologia = tipologia;
    }


    /**
     * Gets the titolo value for this MguSgiuridicoOstDTO.
     * 
     * @return titolo
     */
    public java.lang.String getTitolo() {
        return titolo;
    }


    /**
     * Sets the titolo value for this MguSgiuridicoOstDTO.
     * 
     * @param titolo
     */
    public void setTitolo(java.lang.String titolo) {
        this.titolo = titolo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MguSgiuridicoOstDTO)) return false;
        MguSgiuridicoOstDTO other = (MguSgiuridicoOstDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.idDominio==null && other.getIdDominio()==null) || 
             (this.idDominio!=null &&
              this.idDominio.equals(other.getIdDominio()))) &&
            ((this.idOst==null && other.getIdOst()==null) || 
             (this.idOst!=null &&
              this.idOst.equals(other.getIdOst()))) &&
            ((this.tipologia==null && other.getTipologia()==null) || 
             (this.tipologia!=null &&
              this.tipologia.equals(other.getTipologia()))) &&
            ((this.titolo==null && other.getTitolo()==null) || 
             (this.titolo!=null &&
              this.titolo.equals(other.getTitolo())));
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
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIdDominio() != null) {
            _hashCode += getIdDominio().hashCode();
        }
        if (getIdOst() != null) {
            _hashCode += getIdOst().hashCode();
        }
        if (getTipologia() != null) {
            _hashCode += getTipologia().hashCode();
        }
        if (getTitolo() != null) {
            _hashCode += getTitolo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MguSgiuridicoOstDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguSgiuridicoOstDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idDominio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idDominio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipologia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipologia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titolo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "titolo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
