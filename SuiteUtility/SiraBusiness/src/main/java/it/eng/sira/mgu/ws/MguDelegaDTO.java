/**
 * MguDelegaDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.eng.sira.mgu.ws;

public class MguDelegaDTO  implements java.io.Serializable {
    private java.util.Calendar dataFineValidita;

    private java.util.Calendar dataInizioValidita;

    private it.eng.sira.mgu.ws.MguUtenteDTO delegante;

    private it.eng.sira.mgu.ws.MguUtenteDTO delegato;

    private it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] permessi;

    public MguDelegaDTO() {
    }

    public MguDelegaDTO(
           java.util.Calendar dataFineValidita,
           java.util.Calendar dataInizioValidita,
           it.eng.sira.mgu.ws.MguUtenteDTO delegante,
           it.eng.sira.mgu.ws.MguUtenteDTO delegato,
           it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] permessi) {
           this.dataFineValidita = dataFineValidita;
           this.dataInizioValidita = dataInizioValidita;
           this.delegante = delegante;
           this.delegato = delegato;
           this.permessi = permessi;
    }


    /**
     * Gets the dataFineValidita value for this MguDelegaDTO.
     * 
     * @return dataFineValidita
     */
    public java.util.Calendar getDataFineValidita() {
        return dataFineValidita;
    }


    /**
     * Sets the dataFineValidita value for this MguDelegaDTO.
     * 
     * @param dataFineValidita
     */
    public void setDataFineValidita(java.util.Calendar dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }


    /**
     * Gets the dataInizioValidita value for this MguDelegaDTO.
     * 
     * @return dataInizioValidita
     */
    public java.util.Calendar getDataInizioValidita() {
        return dataInizioValidita;
    }


    /**
     * Sets the dataInizioValidita value for this MguDelegaDTO.
     * 
     * @param dataInizioValidita
     */
    public void setDataInizioValidita(java.util.Calendar dataInizioValidita) {
        this.dataInizioValidita = dataInizioValidita;
    }


    /**
     * Gets the delegante value for this MguDelegaDTO.
     * 
     * @return delegante
     */
    public it.eng.sira.mgu.ws.MguUtenteDTO getDelegante() {
        return delegante;
    }


    /**
     * Sets the delegante value for this MguDelegaDTO.
     * 
     * @param delegante
     */
    public void setDelegante(it.eng.sira.mgu.ws.MguUtenteDTO delegante) {
        this.delegante = delegante;
    }


    /**
     * Gets the delegato value for this MguDelegaDTO.
     * 
     * @return delegato
     */
    public it.eng.sira.mgu.ws.MguUtenteDTO getDelegato() {
        return delegato;
    }


    /**
     * Sets the delegato value for this MguDelegaDTO.
     * 
     * @param delegato
     */
    public void setDelegato(it.eng.sira.mgu.ws.MguUtenteDTO delegato) {
        this.delegato = delegato;
    }


    /**
     * Gets the permessi value for this MguDelegaDTO.
     * 
     * @return permessi
     */
    public it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] getPermessi() {
        return permessi;
    }


    /**
     * Sets the permessi value for this MguDelegaDTO.
     * 
     * @param permessi
     */
    public void setPermessi(it.eng.sira.mgu.ws.MguPermessoRisorsaDTO[] permessi) {
        this.permessi = permessi;
    }

    public it.eng.sira.mgu.ws.MguPermessoRisorsaDTO getPermessi(int i) {
        return this.permessi[i];
    }

    public void setPermessi(int i, it.eng.sira.mgu.ws.MguPermessoRisorsaDTO _value) {
        this.permessi[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof MguDelegaDTO)) return false;
        MguDelegaDTO other = (MguDelegaDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.dataFineValidita==null && other.getDataFineValidita()==null) || 
             (this.dataFineValidita!=null &&
              this.dataFineValidita.equals(other.getDataFineValidita()))) &&
            ((this.dataInizioValidita==null && other.getDataInizioValidita()==null) || 
             (this.dataInizioValidita!=null &&
              this.dataInizioValidita.equals(other.getDataInizioValidita()))) &&
            ((this.delegante==null && other.getDelegante()==null) || 
             (this.delegante!=null &&
              this.delegante.equals(other.getDelegante()))) &&
            ((this.delegato==null && other.getDelegato()==null) || 
             (this.delegato!=null &&
              this.delegato.equals(other.getDelegato()))) &&
            ((this.permessi==null && other.getPermessi()==null) || 
             (this.permessi!=null &&
              java.util.Arrays.equals(this.permessi, other.getPermessi())));
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
        if (getDataFineValidita() != null) {
            _hashCode += getDataFineValidita().hashCode();
        }
        if (getDataInizioValidita() != null) {
            _hashCode += getDataInizioValidita().hashCode();
        }
        if (getDelegante() != null) {
            _hashCode += getDelegante().hashCode();
        }
        if (getDelegato() != null) {
            _hashCode += getDelegato().hashCode();
        }
        if (getPermessi() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPermessi());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPermessi(), i);
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
        new org.apache.axis.description.TypeDesc(MguDelegaDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguDelegaDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataFineValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataFineValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dataInizioValidita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "dataInizioValidita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delegante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "delegante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguUtenteDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("delegato");
        elemField.setXmlName(new javax.xml.namespace.QName("", "delegato"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguUtenteDTO"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("permessi");
        elemField.setXmlName(new javax.xml.namespace.QName("", "permessi"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.mgu.sira.eng.it/", "mguPermessoRisorsaDTO"));
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
