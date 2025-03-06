/**
 * PuntiSospensione.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class PuntiSospensione  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer cavoGuardia;

    private java.lang.Integer idPuntoSosp;

    private java.lang.Integer NProgressivo;

    private com.hyperborea.sira.ws.TipiTestaSostegno tipiTestaSostegno;

    private java.lang.Float XCavo;

    private java.lang.Float YCavo;

    public PuntiSospensione() {
    }

    public PuntiSospensione(
           java.lang.Integer cavoGuardia,
           java.lang.Integer idPuntoSosp,
           java.lang.Integer NProgressivo,
           com.hyperborea.sira.ws.TipiTestaSostegno tipiTestaSostegno,
           java.lang.Float XCavo,
           java.lang.Float YCavo) {
        this.cavoGuardia = cavoGuardia;
        this.idPuntoSosp = idPuntoSosp;
        this.NProgressivo = NProgressivo;
        this.tipiTestaSostegno = tipiTestaSostegno;
        this.XCavo = XCavo;
        this.YCavo = YCavo;
    }


    /**
     * Gets the cavoGuardia value for this PuntiSospensione.
     * 
     * @return cavoGuardia
     */
    public java.lang.Integer getCavoGuardia() {
        return cavoGuardia;
    }


    /**
     * Sets the cavoGuardia value for this PuntiSospensione.
     * 
     * @param cavoGuardia
     */
    public void setCavoGuardia(java.lang.Integer cavoGuardia) {
        this.cavoGuardia = cavoGuardia;
    }


    /**
     * Gets the idPuntoSosp value for this PuntiSospensione.
     * 
     * @return idPuntoSosp
     */
    public java.lang.Integer getIdPuntoSosp() {
        return idPuntoSosp;
    }


    /**
     * Sets the idPuntoSosp value for this PuntiSospensione.
     * 
     * @param idPuntoSosp
     */
    public void setIdPuntoSosp(java.lang.Integer idPuntoSosp) {
        this.idPuntoSosp = idPuntoSosp;
    }


    /**
     * Gets the NProgressivo value for this PuntiSospensione.
     * 
     * @return NProgressivo
     */
    public java.lang.Integer getNProgressivo() {
        return NProgressivo;
    }


    /**
     * Sets the NProgressivo value for this PuntiSospensione.
     * 
     * @param NProgressivo
     */
    public void setNProgressivo(java.lang.Integer NProgressivo) {
        this.NProgressivo = NProgressivo;
    }


    /**
     * Gets the tipiTestaSostegno value for this PuntiSospensione.
     * 
     * @return tipiTestaSostegno
     */
    public com.hyperborea.sira.ws.TipiTestaSostegno getTipiTestaSostegno() {
        return tipiTestaSostegno;
    }


    /**
     * Sets the tipiTestaSostegno value for this PuntiSospensione.
     * 
     * @param tipiTestaSostegno
     */
    public void setTipiTestaSostegno(com.hyperborea.sira.ws.TipiTestaSostegno tipiTestaSostegno) {
        this.tipiTestaSostegno = tipiTestaSostegno;
    }


    /**
     * Gets the XCavo value for this PuntiSospensione.
     * 
     * @return XCavo
     */
    public java.lang.Float getXCavo() {
        return XCavo;
    }


    /**
     * Sets the XCavo value for this PuntiSospensione.
     * 
     * @param XCavo
     */
    public void setXCavo(java.lang.Float XCavo) {
        this.XCavo = XCavo;
    }


    /**
     * Gets the YCavo value for this PuntiSospensione.
     * 
     * @return YCavo
     */
    public java.lang.Float getYCavo() {
        return YCavo;
    }


    /**
     * Sets the YCavo value for this PuntiSospensione.
     * 
     * @param YCavo
     */
    public void setYCavo(java.lang.Float YCavo) {
        this.YCavo = YCavo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PuntiSospensione)) return false;
        PuntiSospensione other = (PuntiSospensione) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.cavoGuardia==null && other.getCavoGuardia()==null) || 
             (this.cavoGuardia!=null &&
              this.cavoGuardia.equals(other.getCavoGuardia()))) &&
            ((this.idPuntoSosp==null && other.getIdPuntoSosp()==null) || 
             (this.idPuntoSosp!=null &&
              this.idPuntoSosp.equals(other.getIdPuntoSosp()))) &&
            ((this.NProgressivo==null && other.getNProgressivo()==null) || 
             (this.NProgressivo!=null &&
              this.NProgressivo.equals(other.getNProgressivo()))) &&
            ((this.tipiTestaSostegno==null && other.getTipiTestaSostegno()==null) || 
             (this.tipiTestaSostegno!=null &&
              this.tipiTestaSostegno.equals(other.getTipiTestaSostegno()))) &&
            ((this.XCavo==null && other.getXCavo()==null) || 
             (this.XCavo!=null &&
              this.XCavo.equals(other.getXCavo()))) &&
            ((this.YCavo==null && other.getYCavo()==null) || 
             (this.YCavo!=null &&
              this.YCavo.equals(other.getYCavo())));
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
        if (getCavoGuardia() != null) {
            _hashCode += getCavoGuardia().hashCode();
        }
        if (getIdPuntoSosp() != null) {
            _hashCode += getIdPuntoSosp().hashCode();
        }
        if (getNProgressivo() != null) {
            _hashCode += getNProgressivo().hashCode();
        }
        if (getTipiTestaSostegno() != null) {
            _hashCode += getTipiTestaSostegno().hashCode();
        }
        if (getXCavo() != null) {
            _hashCode += getXCavo().hashCode();
        }
        if (getYCavo() != null) {
            _hashCode += getYCavo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PuntiSospensione.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "puntiSospensione"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cavoGuardia");
        elemField.setXmlName(new javax.xml.namespace.QName("", "cavoGuardia"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idPuntoSosp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idPuntoSosp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("NProgressivo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NProgressivo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipiTestaSostegno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipiTestaSostegno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "tipiTestaSostegno"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("XCavo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "XCavo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("YCavo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "YCavo"));
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
