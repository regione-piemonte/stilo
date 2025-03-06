/**
 * WsSearchSchedaMonitoraggioRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.hyperborea.sira.ws;

public class WsSearchSchedaMonitoraggioRequest  implements java.io.Serializable {
    private java.lang.Integer idTipoScheda;

    private java.lang.Integer pageNumber;

    private java.lang.Integer pageSize;

    private com.hyperborea.sira.ws.CcostAnySearchStringCriteria ccostAnySearchStringCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria ccostAnySearchDateRangeCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria ccostAnySearchFloatRangeCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria ccostAnySearchIntegerCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria ccostAnySearchIntegerRangeCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria ccostAnySearchDoubleRangeCriteria;

    private com.hyperborea.sira.ws.CcostAnySearchSmCriteria[] searchSmCommands;

    public WsSearchSchedaMonitoraggioRequest() {
    }

    public WsSearchSchedaMonitoraggioRequest(
           java.lang.Integer idTipoScheda,
           java.lang.Integer pageNumber,
           java.lang.Integer pageSize,
           com.hyperborea.sira.ws.CcostAnySearchStringCriteria ccostAnySearchStringCriteria,
           com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria ccostAnySearchDateRangeCriteria,
           com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria ccostAnySearchFloatRangeCriteria,
           com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria ccostAnySearchIntegerCriteria,
           com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria ccostAnySearchIntegerRangeCriteria,
           com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria ccostAnySearchDoubleRangeCriteria,
           com.hyperborea.sira.ws.CcostAnySearchSmCriteria[] searchSmCommands) {
           this.idTipoScheda = idTipoScheda;
           this.pageNumber = pageNumber;
           this.pageSize = pageSize;
           this.ccostAnySearchStringCriteria = ccostAnySearchStringCriteria;
           this.ccostAnySearchDateRangeCriteria = ccostAnySearchDateRangeCriteria;
           this.ccostAnySearchFloatRangeCriteria = ccostAnySearchFloatRangeCriteria;
           this.ccostAnySearchIntegerCriteria = ccostAnySearchIntegerCriteria;
           this.ccostAnySearchIntegerRangeCriteria = ccostAnySearchIntegerRangeCriteria;
           this.ccostAnySearchDoubleRangeCriteria = ccostAnySearchDoubleRangeCriteria;
           this.searchSmCommands = searchSmCommands;
    }


    /**
     * Gets the idTipoScheda value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return idTipoScheda
     */
    public java.lang.Integer getIdTipoScheda() {
        return idTipoScheda;
    }


    /**
     * Sets the idTipoScheda value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param idTipoScheda
     */
    public void setIdTipoScheda(java.lang.Integer idTipoScheda) {
        this.idTipoScheda = idTipoScheda;
    }


    /**
     * Gets the pageNumber value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return pageNumber
     */
    public java.lang.Integer getPageNumber() {
        return pageNumber;
    }


    /**
     * Sets the pageNumber value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param pageNumber
     */
    public void setPageNumber(java.lang.Integer pageNumber) {
        this.pageNumber = pageNumber;
    }


    /**
     * Gets the pageSize value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return pageSize
     */
    public java.lang.Integer getPageSize() {
        return pageSize;
    }


    /**
     * Sets the pageSize value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param pageSize
     */
    public void setPageSize(java.lang.Integer pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * Gets the ccostAnySearchStringCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return ccostAnySearchStringCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchStringCriteria getCcostAnySearchStringCriteria() {
        return ccostAnySearchStringCriteria;
    }


    /**
     * Sets the ccostAnySearchStringCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param ccostAnySearchStringCriteria
     */
    public void setCcostAnySearchStringCriteria(com.hyperborea.sira.ws.CcostAnySearchStringCriteria ccostAnySearchStringCriteria) {
        this.ccostAnySearchStringCriteria = ccostAnySearchStringCriteria;
    }


    /**
     * Gets the ccostAnySearchDateRangeCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return ccostAnySearchDateRangeCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria getCcostAnySearchDateRangeCriteria() {
        return ccostAnySearchDateRangeCriteria;
    }


    /**
     * Sets the ccostAnySearchDateRangeCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param ccostAnySearchDateRangeCriteria
     */
    public void setCcostAnySearchDateRangeCriteria(com.hyperborea.sira.ws.CcostAnySearchDateRangeCriteria ccostAnySearchDateRangeCriteria) {
        this.ccostAnySearchDateRangeCriteria = ccostAnySearchDateRangeCriteria;
    }


    /**
     * Gets the ccostAnySearchFloatRangeCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return ccostAnySearchFloatRangeCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria getCcostAnySearchFloatRangeCriteria() {
        return ccostAnySearchFloatRangeCriteria;
    }


    /**
     * Sets the ccostAnySearchFloatRangeCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param ccostAnySearchFloatRangeCriteria
     */
    public void setCcostAnySearchFloatRangeCriteria(com.hyperborea.sira.ws.CcostAnySearchFloatRangeCriteria ccostAnySearchFloatRangeCriteria) {
        this.ccostAnySearchFloatRangeCriteria = ccostAnySearchFloatRangeCriteria;
    }


    /**
     * Gets the ccostAnySearchIntegerCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return ccostAnySearchIntegerCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria getCcostAnySearchIntegerCriteria() {
        return ccostAnySearchIntegerCriteria;
    }


    /**
     * Sets the ccostAnySearchIntegerCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param ccostAnySearchIntegerCriteria
     */
    public void setCcostAnySearchIntegerCriteria(com.hyperborea.sira.ws.CcostAnySearchIntegerCriteria ccostAnySearchIntegerCriteria) {
        this.ccostAnySearchIntegerCriteria = ccostAnySearchIntegerCriteria;
    }


    /**
     * Gets the ccostAnySearchIntegerRangeCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return ccostAnySearchIntegerRangeCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria getCcostAnySearchIntegerRangeCriteria() {
        return ccostAnySearchIntegerRangeCriteria;
    }


    /**
     * Sets the ccostAnySearchIntegerRangeCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param ccostAnySearchIntegerRangeCriteria
     */
    public void setCcostAnySearchIntegerRangeCriteria(com.hyperborea.sira.ws.CcostAnySearchIntegerRangeCriteria ccostAnySearchIntegerRangeCriteria) {
        this.ccostAnySearchIntegerRangeCriteria = ccostAnySearchIntegerRangeCriteria;
    }


    /**
     * Gets the ccostAnySearchDoubleRangeCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return ccostAnySearchDoubleRangeCriteria
     */
    public com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria getCcostAnySearchDoubleRangeCriteria() {
        return ccostAnySearchDoubleRangeCriteria;
    }


    /**
     * Sets the ccostAnySearchDoubleRangeCriteria value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param ccostAnySearchDoubleRangeCriteria
     */
    public void setCcostAnySearchDoubleRangeCriteria(com.hyperborea.sira.ws.CcostAnySearchDoubleRangeCriteria ccostAnySearchDoubleRangeCriteria) {
        this.ccostAnySearchDoubleRangeCriteria = ccostAnySearchDoubleRangeCriteria;
    }


    /**
     * Gets the searchSmCommands value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @return searchSmCommands
     */
    public com.hyperborea.sira.ws.CcostAnySearchSmCriteria[] getSearchSmCommands() {
        return searchSmCommands;
    }


    /**
     * Sets the searchSmCommands value for this WsSearchSchedaMonitoraggioRequest.
     * 
     * @param searchSmCommands
     */
    public void setSearchSmCommands(com.hyperborea.sira.ws.CcostAnySearchSmCriteria[] searchSmCommands) {
        this.searchSmCommands = searchSmCommands;
    }

    public com.hyperborea.sira.ws.CcostAnySearchSmCriteria getSearchSmCommands(int i) {
        return this.searchSmCommands[i];
    }

    public void setSearchSmCommands(int i, com.hyperborea.sira.ws.CcostAnySearchSmCriteria _value) {
        this.searchSmCommands[i] = _value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WsSearchSchedaMonitoraggioRequest)) return false;
        WsSearchSchedaMonitoraggioRequest other = (WsSearchSchedaMonitoraggioRequest) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.idTipoScheda==null && other.getIdTipoScheda()==null) || 
             (this.idTipoScheda!=null &&
              this.idTipoScheda.equals(other.getIdTipoScheda()))) &&
            ((this.pageNumber==null && other.getPageNumber()==null) || 
             (this.pageNumber!=null &&
              this.pageNumber.equals(other.getPageNumber()))) &&
            ((this.pageSize==null && other.getPageSize()==null) || 
             (this.pageSize!=null &&
              this.pageSize.equals(other.getPageSize()))) &&
            ((this.ccostAnySearchStringCriteria==null && other.getCcostAnySearchStringCriteria()==null) || 
             (this.ccostAnySearchStringCriteria!=null &&
              this.ccostAnySearchStringCriteria.equals(other.getCcostAnySearchStringCriteria()))) &&
            ((this.ccostAnySearchDateRangeCriteria==null && other.getCcostAnySearchDateRangeCriteria()==null) || 
             (this.ccostAnySearchDateRangeCriteria!=null &&
              this.ccostAnySearchDateRangeCriteria.equals(other.getCcostAnySearchDateRangeCriteria()))) &&
            ((this.ccostAnySearchFloatRangeCriteria==null && other.getCcostAnySearchFloatRangeCriteria()==null) || 
             (this.ccostAnySearchFloatRangeCriteria!=null &&
              this.ccostAnySearchFloatRangeCriteria.equals(other.getCcostAnySearchFloatRangeCriteria()))) &&
            ((this.ccostAnySearchIntegerCriteria==null && other.getCcostAnySearchIntegerCriteria()==null) || 
             (this.ccostAnySearchIntegerCriteria!=null &&
              this.ccostAnySearchIntegerCriteria.equals(other.getCcostAnySearchIntegerCriteria()))) &&
            ((this.ccostAnySearchIntegerRangeCriteria==null && other.getCcostAnySearchIntegerRangeCriteria()==null) || 
             (this.ccostAnySearchIntegerRangeCriteria!=null &&
              this.ccostAnySearchIntegerRangeCriteria.equals(other.getCcostAnySearchIntegerRangeCriteria()))) &&
            ((this.ccostAnySearchDoubleRangeCriteria==null && other.getCcostAnySearchDoubleRangeCriteria()==null) || 
             (this.ccostAnySearchDoubleRangeCriteria!=null &&
              this.ccostAnySearchDoubleRangeCriteria.equals(other.getCcostAnySearchDoubleRangeCriteria()))) &&
            ((this.searchSmCommands==null && other.getSearchSmCommands()==null) || 
             (this.searchSmCommands!=null &&
              java.util.Arrays.equals(this.searchSmCommands, other.getSearchSmCommands())));
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
        if (getIdTipoScheda() != null) {
            _hashCode += getIdTipoScheda().hashCode();
        }
        if (getPageNumber() != null) {
            _hashCode += getPageNumber().hashCode();
        }
        if (getPageSize() != null) {
            _hashCode += getPageSize().hashCode();
        }
        if (getCcostAnySearchStringCriteria() != null) {
            _hashCode += getCcostAnySearchStringCriteria().hashCode();
        }
        if (getCcostAnySearchDateRangeCriteria() != null) {
            _hashCode += getCcostAnySearchDateRangeCriteria().hashCode();
        }
        if (getCcostAnySearchFloatRangeCriteria() != null) {
            _hashCode += getCcostAnySearchFloatRangeCriteria().hashCode();
        }
        if (getCcostAnySearchIntegerCriteria() != null) {
            _hashCode += getCcostAnySearchIntegerCriteria().hashCode();
        }
        if (getCcostAnySearchIntegerRangeCriteria() != null) {
            _hashCode += getCcostAnySearchIntegerRangeCriteria().hashCode();
        }
        if (getCcostAnySearchDoubleRangeCriteria() != null) {
            _hashCode += getCcostAnySearchDoubleRangeCriteria().hashCode();
        }
        if (getSearchSmCommands() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSearchSmCommands());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSearchSmCommands(), i);
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
        new org.apache.axis.description.TypeDesc(WsSearchSchedaMonitoraggioRequest.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "wsSearchSchedaMonitoraggioRequest"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("idTipoScheda");
        elemField.setXmlName(new javax.xml.namespace.QName("", "idTipoScheda"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pageSize");
        elemField.setXmlName(new javax.xml.namespace.QName("", "pageSize"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchStringCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchStringCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchStringCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchDateRangeCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDateRangeCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDateRangeCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchFloatRangeCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchFloatRangeCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchFloatRangeCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchIntegerCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchIntegerRangeCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerRangeCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchIntegerRangeCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccostAnySearchDoubleRangeCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDoubleRangeCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchDoubleRangeCriteria"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("searchSmCommands");
        elemField.setXmlName(new javax.xml.namespace.QName("", "searchSmCommands"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://ws.sira.hyperborea.com/", "ccostAnySearchSmCriteria"));
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
