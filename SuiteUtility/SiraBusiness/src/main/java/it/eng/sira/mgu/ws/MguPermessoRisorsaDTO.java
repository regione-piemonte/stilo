/**
 * MguPermessoRisorsaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.sira.mgu.ws;

public class MguPermessoRisorsaDTO  implements java.io.Serializable {
    private it.eng.sira.mgu.ws.MguPermessoDTO azione;

    private it.eng.sira.mgu.ws.MguRisorsaDTO[] risorse;

    private it.eng.sira.mgu.ws.MguSgiuridicoOstDTO[] sgiuridicoost;

    public MguPermessoRisorsaDTO() {
    }

    public MguPermessoRisorsaDTO(
           it.eng.sira.mgu.ws.MguPermessoDTO azione,
           it.eng.sira.mgu.ws.MguRisorsaDTO[] risorse,
           it.eng.sira.mgu.ws.MguSgiuridicoOstDTO[] sgiuridicoost) {
           this.azione = azione;
           this.risorse = risorse;
           this.sgiuridicoost = sgiuridicoost;
    }


    /**
     * Gets the azione value for this MguPermessoRisorsaDTO.
     * 
     * @return azione
     */
    public it.eng.sira.mgu.ws.MguPermessoDTO getAzione() {
        return azione;
    }


    /**
     * Sets the azione value for this MguPermessoRisorsaDTO.
     * 
     * @param azione
     */
    public void setAzione(it.eng.sira.mgu.ws.MguPermessoDTO azione) {
        this.azione = azione;
    }


    /**
     * Gets the risorse value for this MguPermessoRisorsaDTO.
     * 
     * @return risorse
     */
    public it.eng.sira.mgu.ws.MguRisorsaDTO[] getRisorse() {
        return risorse;
    }


    /**
     * Sets the risorse value for this MguPermessoRisorsaDTO.
     * 
     * @param risorse
     */
    public void setRisorse(it.eng.sira.mgu.ws.MguRisorsaDTO[] risorse) {
        this.risorse = risorse;
    }

    public it.eng.sira.mgu.ws.MguRisorsaDTO getRisorse(int i) {
        return this.risorse[i];
    }

    public void setRisorse(int i, it.eng.sira.mgu.ws.MguRisorsaDTO _value) {
        this.risorse[i] = _value;
    }


    /**
     * Gets the sgiuridicoost value for this MguPermessoRisorsaDTO.
     * 
     * @return sgiuridicoost
     */
    public it.eng.sira.mgu.ws.MguSgiuridicoOstDTO[] getSgiuridicoost() {
        return sgiuridicoost;
    }


    /**
     * Sets the sgiuridicoost value for this MguPermessoRisorsaDTO.
     * 
     * @param sgiuridicoost
     */
    public void setSgiuridicoost(it.eng.sira.mgu.ws.MguSgiuridicoOstDTO[] sgiuridicoost) {
        this.sgiuridicoost = sgiuridicoost;
    }

    public it.eng.sira.mgu.ws.MguSgiuridicoOstDTO getSgiuridicoost(int i) {
        return this.sgiuridicoost[i];
    }

    public void setSgiuridicoost(int i, it.eng.sira.mgu.ws.MguSgiuridicoOstDTO _value) {
        this.sgiuridicoost[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MguPermessoRisorsaDTO)) return false;
        MguPermessoRisorsaDTO other = (MguPermessoRisorsaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.azione==null && other.getAzione()==null) || 
             (this.azione!=null &&
              this.azione.equals(other.getAzione()))) &&
            ((this.risorse==null && other.getRisorse()==null) || 
             (this.risorse!=null &&
              java.util.Arrays.equals(this.risorse, other.getRisorse()))) &&
            ((this.sgiuridicoost==null && other.getSgiuridicoost()==null) || 
             (this.sgiuridicoost!=null &&
              java.util.Arrays.equals(this.sgiuridicoost, other.getSgiuridicoost())));
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
        if (getAzione() != null) {
            _hashCode += getAzione().hashCode();
        }
        if (getRisorse() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getRisorse());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getRisorse(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getSgiuridicoost() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSgiuridicoost());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSgiuridicoost(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(MguPermessoRisorsaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguPermessoRisorsaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("azione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "azione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguPermessoDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("risorse");
        elemField.setXmlName(new javax.xml.namespace.QName("", "risorse"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguRisorsaDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sgiuridicoost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "sgiuridicoost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguSgiuridicoOstDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(true);
        elemField.setMaxOccursUnbounded(true);
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
