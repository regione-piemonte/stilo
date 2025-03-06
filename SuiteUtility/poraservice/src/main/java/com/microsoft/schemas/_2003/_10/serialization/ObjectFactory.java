// 
// Decompiled by Procyon v0.5.36
// 

package com.microsoft.schemas._2003._10.serialization;

import javax.xml.datatype.Duration;
import java.math.BigInteger;
import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory
{
    private static final QName _AnyType_QNAME;
    private static final QName _AnyURI_QNAME;
    private static final QName _Base64Binary_QNAME;
    private static final QName _Boolean_QNAME;
    private static final QName _Byte_QNAME;
    private static final QName _DateTime_QNAME;
    private static final QName _Decimal_QNAME;
    private static final QName _Double_QNAME;
    private static final QName _Float_QNAME;
    private static final QName _Int_QNAME;
    private static final QName _Long_QNAME;
    private static final QName _QName_QNAME;
    private static final QName _Short_QNAME;
    private static final QName _String_QNAME;
    private static final QName _UnsignedByte_QNAME;
    private static final QName _UnsignedInt_QNAME;
    private static final QName _UnsignedLong_QNAME;
    private static final QName _UnsignedShort_QNAME;
    private static final QName _Char_QNAME;
    private static final QName _Duration_QNAME;
    private static final QName _Guid_QNAME;
    
    static {
        _AnyType_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyType");
        _AnyURI_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "anyURI");
        _Base64Binary_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "base64Binary");
        _Boolean_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "boolean");
        _Byte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "byte");
        _DateTime_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "dateTime");
        _Decimal_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "decimal");
        _Double_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "double");
        _Float_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "float");
        _Int_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "int");
        _Long_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "long");
        _QName_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "QName");
        _Short_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "short");
        _String_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "string");
        _UnsignedByte_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedByte");
        _UnsignedInt_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedInt");
        _UnsignedLong_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedLong");
        _UnsignedShort_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "unsignedShort");
        _Char_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "char");
        _Duration_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "duration");
        _Guid_QNAME = new QName("http://schemas.microsoft.com/2003/10/Serialization/", "guid");
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyType")
    public JAXBElement<Object> createAnyType(final Object value) {
        return (JAXBElement<Object>)new JAXBElement(ObjectFactory._AnyType_QNAME, (Class)Object.class, (Class)null, value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "anyURI")
    public JAXBElement<String> createAnyURI(final String value) {
        return (JAXBElement<String>)new JAXBElement(ObjectFactory._AnyURI_QNAME, (Class)String.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "base64Binary")
    public JAXBElement<byte[]> createBase64Binary(final byte[] value) {
        return (JAXBElement<byte[]>)new JAXBElement(ObjectFactory._Base64Binary_QNAME, (Class)byte[].class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "boolean")
    public JAXBElement<Boolean> createBoolean(final Boolean value) {
        return (JAXBElement<Boolean>)new JAXBElement(ObjectFactory._Boolean_QNAME, (Class)Boolean.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "byte")
    public JAXBElement<Byte> createByte(final Byte value) {
        return (JAXBElement<Byte>)new JAXBElement(ObjectFactory._Byte_QNAME, (Class)Byte.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "dateTime")
    public JAXBElement<XMLGregorianCalendar> createDateTime(final XMLGregorianCalendar value) {
        return (JAXBElement<XMLGregorianCalendar>)new JAXBElement(ObjectFactory._DateTime_QNAME, (Class)XMLGregorianCalendar.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "decimal")
    public JAXBElement<BigDecimal> createDecimal(final BigDecimal value) {
        return (JAXBElement<BigDecimal>)new JAXBElement(ObjectFactory._Decimal_QNAME, (Class)BigDecimal.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "double")
    public JAXBElement<Double> createDouble(final Double value) {
        return (JAXBElement<Double>)new JAXBElement(ObjectFactory._Double_QNAME, (Class)Double.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "float")
    public JAXBElement<Float> createFloat(final Float value) {
        return (JAXBElement<Float>)new JAXBElement(ObjectFactory._Float_QNAME, (Class)Float.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "int")
    public JAXBElement<Integer> createInt(final Integer value) {
        return (JAXBElement<Integer>)new JAXBElement(ObjectFactory._Int_QNAME, (Class)Integer.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "long")
    public JAXBElement<Long> createLong(final Long value) {
        return (JAXBElement<Long>)new JAXBElement(ObjectFactory._Long_QNAME, (Class)Long.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "QName")
    public JAXBElement<QName> createQName(final QName value) {
        return (JAXBElement<QName>)new JAXBElement(ObjectFactory._QName_QNAME, (Class)QName.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "short")
    public JAXBElement<Short> createShort(final Short value) {
        return (JAXBElement<Short>)new JAXBElement(ObjectFactory._Short_QNAME, (Class)Short.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "string")
    public JAXBElement<String> createString(final String value) {
        return (JAXBElement<String>)new JAXBElement(ObjectFactory._String_QNAME, (Class)String.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedByte")
    public JAXBElement<Short> createUnsignedByte(final Short value) {
        return (JAXBElement<Short>)new JAXBElement(ObjectFactory._UnsignedByte_QNAME, (Class)Short.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedInt")
    public JAXBElement<Long> createUnsignedInt(final Long value) {
        return (JAXBElement<Long>)new JAXBElement(ObjectFactory._UnsignedInt_QNAME, (Class)Long.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedLong")
    public JAXBElement<BigInteger> createUnsignedLong(final BigInteger value) {
        return (JAXBElement<BigInteger>)new JAXBElement(ObjectFactory._UnsignedLong_QNAME, (Class)BigInteger.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "unsignedShort")
    public JAXBElement<Integer> createUnsignedShort(final Integer value) {
        return (JAXBElement<Integer>)new JAXBElement(ObjectFactory._UnsignedShort_QNAME, (Class)Integer.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "char")
    public JAXBElement<Integer> createChar(final Integer value) {
        return (JAXBElement<Integer>)new JAXBElement(ObjectFactory._Char_QNAME, (Class)Integer.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "duration")
    public JAXBElement<Duration> createDuration(final Duration value) {
        return (JAXBElement<Duration>)new JAXBElement(ObjectFactory._Duration_QNAME, (Class)Duration.class, (Class)null, (Object)value);
    }
    
    @XmlElementDecl(namespace = "http://schemas.microsoft.com/2003/10/Serialization/", name = "guid")
    public JAXBElement<String> createGuid(final String value) {
        return (JAXBElement<String>)new JAXBElement(ObjectFactory._Guid_QNAME, (Class)String.class, (Class)null, (Object)value);
    }
}
