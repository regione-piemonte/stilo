/**
 * CcostAutoparcoEf.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAutoparcoEf  implements java.io.Serializable {
    private java.lang.String carrozzeria;

    private java.lang.Integer idCcost;

    private java.lang.String officina;

    private java.lang.String potenzialita;

    private java.lang.String produzione;

    private com.hyperborea.sira.ws.VeicoliAutoparcoEf[] veicoliAutoparcoEfs;

    public CcostAutoparcoEf() {
    }

    public CcostAutoparcoEf(
           java.lang.String carrozzeria,
           java.lang.Integer idCcost,
           java.lang.String officina,
           java.lang.String potenzialita,
           java.lang.String produzione,
           com.hyperborea.sira.ws.VeicoliAutoparcoEf[] veicoliAutoparcoEfs) {
           this.carrozzeria = carrozzeria;
           this.idCcost = idCcost;
           this.officina = officina;
           this.potenzialita = potenzialita;
           this.produzione = produzione;
           this.veicoliAutoparcoEfs = veicoliAutoparcoEfs;
    }


    /**
     * Gets the carrozzeria value for this CcostAutoparcoEf.
     * 
     * @return carrozzeria
     */
    public java.lang.String getCarrozzeria() {
        return carrozzeria;
    }


    /**
     * Sets the carrozzeria value for this CcostAutoparcoEf.
     * 
     * @param carrozzeria
     */
    public void setCarrozzeria(java.lang.String carrozzeria) {
        this.carrozzeria = carrozzeria;
    }


    /**
     * Gets the idCcost value for this CcostAutoparcoEf.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAutoparcoEf.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the officina value for this CcostAutoparcoEf.
     * 
     * @return officina
     */
    public java.lang.String getOfficina() {
        return officina;
    }


    /**
     * Sets the officina value for this CcostAutoparcoEf.
     * 
     * @param officina
     */
    public void setOfficina(java.lang.String officina) {
        this.officina = officina;
    }


    /**
     * Gets the potenzialita value for this CcostAutoparcoEf.
     * 
     * @return potenzialita
     */
    public java.lang.String getPotenzialita() {
        return potenzialita;
    }


    /**
     * Sets the potenzialita value for this CcostAutoparcoEf.
     * 
     * @param potenzialita
     */
    public void setPotenzialita(java.lang.String potenzialita) {
        this.potenzialita = potenzialita;
    }


    /**
     * Gets the produzione value for this CcostAutoparcoEf.
     * 
     * @return produzione
     */
    public java.lang.String getProduzione() {
        return produzione;
    }


    /**
     * Sets the produzione value for this CcostAutoparcoEf.
     * 
     * @param produzione
     */
    public void setProduzione(java.lang.String produzione) {
        this.produzione = produzione;
    }


    /**
     * Gets the veicoliAutoparcoEfs value for this CcostAutoparcoEf.
     * 
     * @return veicoliAutoparcoEfs
     */
    public com.hyperborea.sira.ws.VeicoliAutoparcoEf[] getVeicoliAutoparcoEfs() {
        return veicoliAutoparcoEfs;
    }


    /**
     * Sets the veicoliAutoparcoEfs value for this CcostAutoparcoEf.
     * 
     * @param veicoliAutoparcoEfs
     */
    public void setVeicoliAutoparcoEfs(com.hyperborea.sira.ws.VeicoliAutoparcoEf[] veicoliAutoparcoEfs) {
        this.veicoliAutoparcoEfs = veicoliAutoparcoEfs;
    }

    public com.hyperborea.sira.ws.VeicoliAutoparcoEf getVeicoliAutoparcoEfs(int i) {
        return this.veicoliAutoparcoEfs[i];
    }

    public void setVeicoliAutoparcoEfs(int i, com.hyperborea.sira.ws.VeicoliAutoparcoEf _value) {
        this.veicoliAutoparcoEfs[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAutoparcoEf)) return false;
        CcostAutoparcoEf other = (CcostAutoparcoEf) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.carrozzeria==null && other.getCarrozzeria()==null) || 
             (this.carrozzeria!=null &&
              this.carrozzeria.equals(other.getCarrozzeria()))) &&
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.officina==null && other.getOfficina()==null) || 
             (this.officina!=null &&
              this.officina.equals(other.getOfficina()))) &&
            ((this.potenzialita==null && other.getPotenzialita()==null) || 
             (this.potenzialita!=null &&
              this.potenzialita.equals(other.getPotenzialita()))) &&
            ((this.produzione==null && other.getProduzione()==null) || 
             (this.produzione!=null &&
              this.produzione.equals(other.getProduzione()))) &&
            ((this.veicoliAutoparcoEfs==null && other.getVeicoliAutoparcoEfs()==null) || 
             (this.veicoliAutoparcoEfs!=null &&
              java.util.Arrays.equals(this.veicoliAutoparcoEfs, other.getVeicoliAutoparcoEfs())));
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
        if (getCarrozzeria() != null) {
            _hashCode += getCarrozzeria().hashCode();
        }
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getOfficina() != null) {
            _hashCode += getOfficina().hashCode();
        }
        if (getPotenzialita() != null) {
            _hashCode += getPotenzialita().hashCode();
        }
        if (getProduzione() != null) {
            _hashCode += getProduzione().hashCode();
        }
        if (getVeicoliAutoparcoEfs() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getVeicoliAutoparcoEfs());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getVeicoliAutoparcoEfs(), i);
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
        new org.apache.axis.description.TypeDesc(CcostAutoparcoEf.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAutoparcoEf"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("carrozzeria");
        elemField.setXmlName(new javax.xml.namespace.QName("", "carrozzeria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("officina");
        elemField.setXmlName(new javax.xml.namespace.QName("", "officina"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("potenzialita");
        elemField.setXmlName(new javax.xml.namespace.QName("", "potenzialita"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("produzione");
        elemField.setXmlName(new javax.xml.namespace.QName("", "produzione"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("veicoliAutoparcoEfs");
        elemField.setXmlName(new javax.xml.namespace.QName("", "veicoliAutoparcoEfs"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "veicoliAutoparcoEf"));
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
