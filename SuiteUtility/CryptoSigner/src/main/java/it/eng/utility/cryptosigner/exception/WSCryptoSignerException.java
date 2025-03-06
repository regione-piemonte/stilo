package it.eng.utility.cryptosigner.exception;



public class WSCryptoSignerException  extends it.eng.utility.cryptosigner.exception.CryptoSignerException  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int CRL_DISTRIBUTION_POINT_UNREACHABLE = 1;
	
	private byte[] attachedSerializedObject;

    private int errorCode;

    private it.eng.utility.cryptosigner.controller.bean.ValidationInfos validationInfos;

    public WSCryptoSignerException() {
    }

    public WSCryptoSignerException(
           byte[] attachedSerializedObject,
           int errorCode,
           it.eng.utility.cryptosigner.controller.bean.ValidationInfos validationInfos) {
        this.attachedSerializedObject = attachedSerializedObject;
        this.errorCode = errorCode;
        this.validationInfos = validationInfos;
    }


    /**
     * Gets the attachedSerializedObject value for this WSCryptoSignerException.
     * 
     * @return attachedSerializedObject
     */
    public byte[] getAttachedSerializedObject() {
        return attachedSerializedObject;
    }


    /**
     * Sets the attachedSerializedObject value for this WSCryptoSignerException.
     * 
     * @param attachedSerializedObject
     */
    public void setAttachedSerializedObject(byte[] attachedSerializedObject) {
        this.attachedSerializedObject = attachedSerializedObject;
    }


    /**
     * Gets the errorCode value for this WSCryptoSignerException.
     * 
     * @return errorCode
     */
    public int getErrorCode() {
        return errorCode;
    }


    /**
     * Sets the errorCode value for this WSCryptoSignerException.
     * 
     * @param errorCode
     */
    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }


    /**
     * Gets the validationInfos value for this WSCryptoSignerException.
     * 
     * @return validationInfos
     */
    public it.eng.utility.cryptosigner.controller.bean.ValidationInfos getValidationInfos() {
        return validationInfos;
    }


    /**
     * Sets the validationInfos value for this WSCryptoSignerException.
     * 
     * @param validationInfos
     */
    public void setValidationInfos(it.eng.utility.cryptosigner.controller.bean.ValidationInfos validationInfos) {
        this.validationInfos = validationInfos;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WSCryptoSignerException)) return false;
        WSCryptoSignerException other = (WSCryptoSignerException) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.attachedSerializedObject==null && other.getAttachedSerializedObject()==null) || 
             (this.attachedSerializedObject!=null &&
              java.util.Arrays.equals(this.attachedSerializedObject, other.getAttachedSerializedObject()))) &&
            this.errorCode == other.getErrorCode() &&
            ((this.validationInfos==null && other.getValidationInfos()==null) || 
             (this.validationInfos!=null &&
              this.validationInfos.equals(other.getValidationInfos())));
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
        if (getAttachedSerializedObject() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getAttachedSerializedObject());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getAttachedSerializedObject(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getErrorCode();
        if (getValidationInfos() != null) {
            _hashCode += getValidationInfos().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WSCryptoSignerException.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://exception.crypto.eng.it", "WSCryptoSignerException"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attachedSerializedObject");
        elemField.setXmlName(new javax.xml.namespace.QName("http://exception.crypto.eng.it", "attachedSerializedObject"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://exception.crypto.eng.it", "errorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validationInfos");
        elemField.setXmlName(new javax.xml.namespace.QName("http://exception.crypto.eng.it", "validationInfos"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://bean.controller.crypto.eng.it", "ValidationInfos"));
        elemField.setNillable(true);
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


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
