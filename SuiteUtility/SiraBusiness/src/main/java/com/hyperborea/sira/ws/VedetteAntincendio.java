/**
 * VedetteAntincendio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class VedetteAntincendio  extends com.hyperborea.sira.ws.ReferencedBean  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private java.lang.Integer numAddetti;

    private java.lang.String periodo;

    private java.lang.String turno;

    public VedetteAntincendio() {
    }

    public VedetteAntincendio(
           java.lang.Integer idCcost,
           java.lang.Integer numAddetti,
           java.lang.String periodo,
           java.lang.String turno) {
        this.idCcost = idCcost;
        this.numAddetti = numAddetti;
        this.periodo = periodo;
        this.turno = turno;
    }


    /**
     * Gets the idCcost value for this VedetteAntincendio.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this VedetteAntincendio.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the numAddetti value for this VedetteAntincendio.
     * 
     * @return numAddetti
     */
    public java.lang.Integer getNumAddetti() {
        return numAddetti;
    }


    /**
     * Sets the numAddetti value for this VedetteAntincendio.
     * 
     * @param numAddetti
     */
    public void setNumAddetti(java.lang.Integer numAddetti) {
        this.numAddetti = numAddetti;
    }


    /**
     * Gets the periodo value for this VedetteAntincendio.
     * 
     * @return periodo
     */
    public java.lang.String getPeriodo() {
        return periodo;
    }


    /**
     * Sets the periodo value for this VedetteAntincendio.
     * 
     * @param periodo
     */
    public void setPeriodo(java.lang.String periodo) {
        this.periodo = periodo;
    }


    /**
     * Gets the turno value for this VedetteAntincendio.
     * 
     * @return turno
     */
    public java.lang.String getTurno() {
        return turno;
    }


    /**
     * Sets the turno value for this VedetteAntincendio.
     * 
     * @param turno
     */
    public void setTurno(java.lang.String turno) {
        this.turno = turno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof VedetteAntincendio)) return false;
        VedetteAntincendio other = (VedetteAntincendio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.numAddetti==null && other.getNumAddetti()==null) || 
             (this.numAddetti!=null &&
              this.numAddetti.equals(other.getNumAddetti()))) &&
            ((this.periodo==null && other.getPeriodo()==null) || 
             (this.periodo!=null &&
              this.periodo.equals(other.getPeriodo()))) &&
            ((this.turno==null && other.getTurno()==null) || 
             (this.turno!=null &&
              this.turno.equals(other.getTurno())));
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
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getNumAddetti() != null) {
            _hashCode += getNumAddetti().hashCode();
        }
        if (getPeriodo() != null) {
            _hashCode += getPeriodo().hashCode();
        }
        if (getTurno() != null) {
            _hashCode += getTurno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(VedetteAntincendio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "vedetteAntincendio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("numAddetti");
        elemField.setXmlName(new javax.xml.namespace.QName("", "numAddetti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("periodo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "periodo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("turno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "turno"));
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
