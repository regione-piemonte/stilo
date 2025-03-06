/**
 * CcostAreaGallinaPrataiola.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class CcostAreaGallinaPrataiola  implements java.io.Serializable {
    private java.lang.Integer idCcost;

    private java.lang.Float migRiproduttiva;

    private java.lang.Float migStanziale;

    private java.lang.Float migSvernante;

    private java.lang.Float stanziale;

    public CcostAreaGallinaPrataiola() {
    }

    public CcostAreaGallinaPrataiola(
           java.lang.Integer idCcost,
           java.lang.Float migRiproduttiva,
           java.lang.Float migStanziale,
           java.lang.Float migSvernante,
           java.lang.Float stanziale) {
           this.idCcost = idCcost;
           this.migRiproduttiva = migRiproduttiva;
           this.migStanziale = migStanziale;
           this.migSvernante = migSvernante;
           this.stanziale = stanziale;
    }


    /**
     * Gets the idCcost value for this CcostAreaGallinaPrataiola.
     * 
     * @return idCcost
     */
    public java.lang.Integer getIdCcost() {
        return idCcost;
    }


    /**
     * Sets the idCcost value for this CcostAreaGallinaPrataiola.
     * 
     * @param idCcost
     */
    public void setIdCcost(java.lang.Integer idCcost) {
        this.idCcost = idCcost;
    }


    /**
     * Gets the migRiproduttiva value for this CcostAreaGallinaPrataiola.
     * 
     * @return migRiproduttiva
     */
    public java.lang.Float getMigRiproduttiva() {
        return migRiproduttiva;
    }


    /**
     * Sets the migRiproduttiva value for this CcostAreaGallinaPrataiola.
     * 
     * @param migRiproduttiva
     */
    public void setMigRiproduttiva(java.lang.Float migRiproduttiva) {
        this.migRiproduttiva = migRiproduttiva;
    }


    /**
     * Gets the migStanziale value for this CcostAreaGallinaPrataiola.
     * 
     * @return migStanziale
     */
    public java.lang.Float getMigStanziale() {
        return migStanziale;
    }


    /**
     * Sets the migStanziale value for this CcostAreaGallinaPrataiola.
     * 
     * @param migStanziale
     */
    public void setMigStanziale(java.lang.Float migStanziale) {
        this.migStanziale = migStanziale;
    }


    /**
     * Gets the migSvernante value for this CcostAreaGallinaPrataiola.
     * 
     * @return migSvernante
     */
    public java.lang.Float getMigSvernante() {
        return migSvernante;
    }


    /**
     * Sets the migSvernante value for this CcostAreaGallinaPrataiola.
     * 
     * @param migSvernante
     */
    public void setMigSvernante(java.lang.Float migSvernante) {
        this.migSvernante = migSvernante;
    }


    /**
     * Gets the stanziale value for this CcostAreaGallinaPrataiola.
     * 
     * @return stanziale
     */
    public java.lang.Float getStanziale() {
        return stanziale;
    }


    /**
     * Sets the stanziale value for this CcostAreaGallinaPrataiola.
     * 
     * @param stanziale
     */
    public void setStanziale(java.lang.Float stanziale) {
        this.stanziale = stanziale;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CcostAreaGallinaPrataiola)) return false;
        CcostAreaGallinaPrataiola other = (CcostAreaGallinaPrataiola) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idCcost==null && other.getIdCcost()==null) || 
             (this.idCcost!=null &&
              this.idCcost.equals(other.getIdCcost()))) &&
            ((this.migRiproduttiva==null && other.getMigRiproduttiva()==null) || 
             (this.migRiproduttiva!=null &&
              this.migRiproduttiva.equals(other.getMigRiproduttiva()))) &&
            ((this.migStanziale==null && other.getMigStanziale()==null) || 
             (this.migStanziale!=null &&
              this.migStanziale.equals(other.getMigStanziale()))) &&
            ((this.migSvernante==null && other.getMigSvernante()==null) || 
             (this.migSvernante!=null &&
              this.migSvernante.equals(other.getMigSvernante()))) &&
            ((this.stanziale==null && other.getStanziale()==null) || 
             (this.stanziale!=null &&
              this.stanziale.equals(other.getStanziale())));
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
        if (getIdCcost() != null) {
            _hashCode += getIdCcost().hashCode();
        }
        if (getMigRiproduttiva() != null) {
            _hashCode += getMigRiproduttiva().hashCode();
        }
        if (getMigStanziale() != null) {
            _hashCode += getMigStanziale().hashCode();
        }
        if (getMigSvernante() != null) {
            _hashCode += getMigSvernante().hashCode();
        }
        if (getStanziale() != null) {
            _hashCode += getStanziale().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CcostAreaGallinaPrataiola.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAreaGallinaPrataiola"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idCcost");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idCcost"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("migRiproduttiva");
        elemField.setXmlName(new javax.xml.namespace.QName("", "migRiproduttiva"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("migStanziale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "migStanziale"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("migSvernante");
        elemField.setXmlName(new javax.xml.namespace.QName("", "migSvernante"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "float"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stanziale");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stanziale"));
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
