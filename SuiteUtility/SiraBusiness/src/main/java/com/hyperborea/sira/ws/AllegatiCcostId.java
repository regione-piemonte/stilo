/**
 * AllegatiCcostId.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class AllegatiCcostId  implements java.io.Serializable {
    private com.hyperborea.sira.ws.Allegati allegati;

    private com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst;

    public AllegatiCcostId() {
    }

    public AllegatiCcostId(
           com.hyperborea.sira.ws.Allegati allegati,
           com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst) {
           this.allegati = allegati;
           this.caratterizzazioniOst = caratterizzazioniOst;
    }


    /**
     * Gets the allegati value for this AllegatiCcostId.
     * 
     * @return allegati
     */
    public com.hyperborea.sira.ws.Allegati getAllegati() {
        return allegati;
    }


    /**
     * Sets the allegati value for this AllegatiCcostId.
     * 
     * @param allegati
     */
    public void setAllegati(com.hyperborea.sira.ws.Allegati allegati) {
        this.allegati = allegati;
    }


    /**
     * Gets the caratterizzazioniOst value for this AllegatiCcostId.
     * 
     * @return caratterizzazioniOst
     */
    public com.hyperborea.sira.ws.CaratterizzazioniOst getCaratterizzazioniOst() {
        return caratterizzazioniOst;
    }


    /**
     * Sets the caratterizzazioniOst value for this AllegatiCcostId.
     * 
     * @param caratterizzazioniOst
     */
    public void setCaratterizzazioniOst(com.hyperborea.sira.ws.CaratterizzazioniOst caratterizzazioniOst) {
        this.caratterizzazioniOst = caratterizzazioniOst;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AllegatiCcostId)) return false;
        AllegatiCcostId other = (AllegatiCcostId) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.allegati==null && other.getAllegati()==null) || 
             (this.allegati!=null &&
              this.allegati.equals(other.getAllegati()))) &&
            ((this.caratterizzazioniOst==null && other.getCaratterizzazioniOst()==null) || 
             (this.caratterizzazioniOst!=null &&
              this.caratterizzazioniOst.equals(other.getCaratterizzazioniOst())));
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
        if (getAllegati() != null) {
            _hashCode += getAllegati().hashCode();
        }
        if (getCaratterizzazioniOst() != null) {
            _hashCode += getCaratterizzazioniOst().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AllegatiCcostId.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "allegatiCcostId"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("allegati");
        elemField.setXmlName(new javax.xml.namespace.QName("", "allegati"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "allegati"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caratterizzazioniOst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "caratterizzazioniOst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "caratterizzazioniOst"));
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
