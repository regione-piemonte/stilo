/**
 * WsTipiSchedaMonitoraggioDescriptor.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsTipiSchedaMonitoraggioDescriptor  implements java.io.Serializable {
    private com.hyperborea.sira.ws.CampiSchedaMon[] campiSchedaMons;

    private com.hyperborea.sira.ws.SezioniSchedaMon[] sezioniSchedaMonitoraggios;

    private com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef wsTipiSchedaMonitoraggioRef;

    public WsTipiSchedaMonitoraggioDescriptor() {
    }

    public WsTipiSchedaMonitoraggioDescriptor(
           com.hyperborea.sira.ws.CampiSchedaMon[] campiSchedaMons,
           com.hyperborea.sira.ws.SezioniSchedaMon[] sezioniSchedaMonitoraggios,
           com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef wsTipiSchedaMonitoraggioRef) {
           this.campiSchedaMons = campiSchedaMons;
           this.sezioniSchedaMonitoraggios = sezioniSchedaMonitoraggios;
           this.wsTipiSchedaMonitoraggioRef = wsTipiSchedaMonitoraggioRef;
    }


    /**
     * Gets the campiSchedaMons value for this WsTipiSchedaMonitoraggioDescriptor.
     * 
     * @return campiSchedaMons
     */
    public com.hyperborea.sira.ws.CampiSchedaMon[] getCampiSchedaMons() {
        return campiSchedaMons;
    }


    /**
     * Sets the campiSchedaMons value for this WsTipiSchedaMonitoraggioDescriptor.
     * 
     * @param campiSchedaMons
     */
    public void setCampiSchedaMons(com.hyperborea.sira.ws.CampiSchedaMon[] campiSchedaMons) {
        this.campiSchedaMons = campiSchedaMons;
    }

    public com.hyperborea.sira.ws.CampiSchedaMon getCampiSchedaMons(int i) {
        return this.campiSchedaMons[i];
    }

    public void setCampiSchedaMons(int i, com.hyperborea.sira.ws.CampiSchedaMon _value) {
        this.campiSchedaMons[i] = _value;
    }


    /**
     * Gets the sezioniSchedaMonitoraggios value for this WsTipiSchedaMonitoraggioDescriptor.
     * 
     * @return sezioniSchedaMonitoraggios
     */
    public com.hyperborea.sira.ws.SezioniSchedaMon[] getSezioniSchedaMonitoraggios() {
        return sezioniSchedaMonitoraggios;
    }


    /**
     * Sets the sezioniSchedaMonitoraggios value for this WsTipiSchedaMonitoraggioDescriptor.
     * 
     * @param sezioniSchedaMonitoraggios
     */
    public void setSezioniSchedaMonitoraggios(com.hyperborea.sira.ws.SezioniSchedaMon[] sezioniSchedaMonitoraggios) {
        this.sezioniSchedaMonitoraggios = sezioniSchedaMonitoraggios;
    }

    public com.hyperborea.sira.ws.SezioniSchedaMon getSezioniSchedaMonitoraggios(int i) {
        return this.sezioniSchedaMonitoraggios[i];
    }

    public void setSezioniSchedaMonitoraggios(int i, com.hyperborea.sira.ws.SezioniSchedaMon _value) {
        this.sezioniSchedaMonitoraggios[i] = _value;
    }


    /**
     * Gets the wsTipiSchedaMonitoraggioRef value for this WsTipiSchedaMonitoraggioDescriptor.
     * 
     * @return wsTipiSchedaMonitoraggioRef
     */
    public com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef getWsTipiSchedaMonitoraggioRef() {
        return wsTipiSchedaMonitoraggioRef;
    }


    /**
     * Sets the wsTipiSchedaMonitoraggioRef value for this WsTipiSchedaMonitoraggioDescriptor.
     * 
     * @param wsTipiSchedaMonitoraggioRef
     */
    public void setWsTipiSchedaMonitoraggioRef(com.hyperborea.sira.ws.WsTipiSchedaMonitoraggioRef wsTipiSchedaMonitoraggioRef) {
        this.wsTipiSchedaMonitoraggioRef = wsTipiSchedaMonitoraggioRef;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsTipiSchedaMonitoraggioDescriptor)) return false;
        WsTipiSchedaMonitoraggioDescriptor other = (WsTipiSchedaMonitoraggioDescriptor) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.campiSchedaMons==null && other.getCampiSchedaMons()==null) || 
             (this.campiSchedaMons!=null &&
              java.util.Arrays.equals(this.campiSchedaMons, other.getCampiSchedaMons()))) &&
            ((this.sezioniSchedaMonitoraggios==null && other.getSezioniSchedaMonitoraggios()==null) || 
             (this.sezioniSchedaMonitoraggios!=null &&
              java.util.Arrays.equals(this.sezioniSchedaMonitoraggios, other.getSezioniSchedaMonitoraggios()))) &&
            ((this.wsTipiSchedaMonitoraggioRef==null && other.getWsTipiSchedaMonitoraggioRef()==null) || 
             (this.wsTipiSchedaMonitoraggioRef!=null &&
              this.wsTipiSchedaMonitoraggioRef.equals(other.getWsTipiSchedaMonitoraggioRef())));
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
        if (getCampiSchedaMons() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCampiSchedaMons());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCampiSchedaMons(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSezioniSchedaMonitoraggios() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSezioniSchedaMonitoraggios());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSezioniSchedaMonitoraggios(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getWsTipiSchedaMonitoraggioRef() != null) {
            _hashCode += getWsTipiSchedaMonitoraggioRef().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WsTipiSchedaMonitoraggioDescriptor.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioDescriptor"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campiSchedaMons");
        elemField.setXmlName(new javax.xml.namespace.QName("", "campiSchedaMons"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "campiSchedaMon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sezioniSchedaMonitoraggios");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sezioniSchedaMonitoraggios"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "sezioniSchedaMon"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wsTipiSchedaMonitoraggioRef");
        elemField.setXmlName(new javax.xml.namespace.QName("", "wsTipiSchedaMonitoraggioRef"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsTipiSchedaMonitoraggioRef"));
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
