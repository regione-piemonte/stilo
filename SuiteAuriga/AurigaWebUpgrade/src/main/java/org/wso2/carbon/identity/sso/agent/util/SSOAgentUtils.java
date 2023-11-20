/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
// Decompiled by Procyon v0.5.36
// 

package org.wso2.carbon.identity.sso.agent.util;

import org.opensaml.xml.XMLObjectBuilder;
import javax.xml.namespace.QName;
import java.net.URLEncoder;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import java.util.List;
import org.opensaml.xml.signature.Signer;
import org.apache.xml.security.Init;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.Configuration;
import java.util.ArrayList;
import java.security.cert.CertificateEncodingException;
import org.wso2.carbon.identity.sso.agent.exception.SSOAgentException;
import org.apache.xml.security.utils.Base64;
import org.opensaml.xml.signature.X509Certificate;
import org.opensaml.xml.signature.X509Data;
import org.opensaml.xml.signature.KeyInfo;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.signature.Signature;
import org.opensaml.xml.security.x509.X509Credential;
import org.opensaml.saml2.core.AuthnRequest;
import java.util.Random;
import java.util.logging.Logger;

public class SSOAgentUtils
{
    private static Logger LOGGER;
    
    public static String createID() {
        final byte[] bytes = new byte[20];
        new Random().nextBytes(bytes);
        final char[] charMapping = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };
        final char[] chars = new char[40];
        for (int i = 0; i < bytes.length; ++i) {
            final int left = bytes[i] >> 4 & 0xF;
            final int right = bytes[i] & 0xF;
            chars[i * 2] = charMapping[left];
            chars[i * 2 + 1] = charMapping[right];
        }
        return String.valueOf(chars);
    }
    
    public static AuthnRequest setSignature(final AuthnRequest authnRequest, final String signatureAlgorithm, final X509Credential cred) throws SSOAgentException {
        try {
            final Signature signature = (Signature)buildXMLObject(Signature.DEFAULT_ELEMENT_NAME);
            signature.setSigningCredential((Credential)cred);
            signature.setSignatureAlgorithm(signatureAlgorithm);
            signature.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
            try {
                final KeyInfo keyInfo = (KeyInfo)buildXMLObject(KeyInfo.DEFAULT_ELEMENT_NAME);
                final X509Data data = (X509Data)buildXMLObject(X509Data.DEFAULT_ELEMENT_NAME);
                final X509Certificate cert = (X509Certificate)buildXMLObject(X509Certificate.DEFAULT_ELEMENT_NAME);
                final String value = Base64.encode(cred.getEntityCertificate().getEncoded());
                cert.setValue(value);
                data.getX509Certificates().add(cert);
                keyInfo.getX509Datas().add(data);
                signature.setKeyInfo(keyInfo);
            }
            catch (CertificateEncodingException e) {
                throw new SSOAgentException("Error getting certificate", e);
            }
            authnRequest.setSignature(signature);
            final List<Signature> signatureList = new ArrayList<Signature>();
            signatureList.add(signature);
            final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
            final Marshaller marshaller = marshallerFactory.getMarshaller((XMLObject)authnRequest);
            marshaller.marshall((XMLObject)authnRequest);
            Init.init();
            Signer.signObjects((List)signatureList);
            return authnRequest;
        }
        catch (Exception e2) {
            throw new SSOAgentException("Error while signing the SAML Request message", e2);
        }
    }
    
    public static LogoutRequest setSignature(final LogoutRequest logoutRequest, final String signatureAlgorithm, final X509Credential cred) throws SSOAgentException {
        try {
            final Signature signature = (Signature)buildXMLObject(Signature.DEFAULT_ELEMENT_NAME);
            signature.setSigningCredential((Credential)cred);
            signature.setSignatureAlgorithm(signatureAlgorithm);
            signature.setCanonicalizationAlgorithm("http://www.w3.org/2001/10/xml-exc-c14n#");
            try {
                final KeyInfo keyInfo = (KeyInfo)buildXMLObject(KeyInfo.DEFAULT_ELEMENT_NAME);
                final X509Data data = (X509Data)buildXMLObject(X509Data.DEFAULT_ELEMENT_NAME);
                final X509Certificate cert = (X509Certificate)buildXMLObject(X509Certificate.DEFAULT_ELEMENT_NAME);
                final String value = Base64.encode(cred.getEntityCertificate().getEncoded());
                cert.setValue(value);
                data.getX509Certificates().add(cert);
                keyInfo.getX509Datas().add(data);
                signature.setKeyInfo(keyInfo);
            }
            catch (CertificateEncodingException e) {
                throw new SSOAgentException("Error getting certificate", e);
            }
            logoutRequest.setSignature(signature);
            final List<Signature> signatureList = new ArrayList<Signature>();
            signatureList.add(signature);
            final MarshallerFactory marshallerFactory = Configuration.getMarshallerFactory();
            final Marshaller marshaller = marshallerFactory.getMarshaller((XMLObject)logoutRequest);
            marshaller.marshall((XMLObject)logoutRequest);
            Init.init();
            Signer.signObjects((List)signatureList);
            return logoutRequest;
        }
        catch (Exception e2) {
            throw new SSOAgentException("Error while signing the Logout Request message", e2);
        }
    }
    
    public static void addDeflateSignatureToHTTPQueryString(final StringBuilder httpQueryString, final X509Credential cred) throws SSOAgentException {
        try {
            httpQueryString.append("&SigAlg=" + URLEncoder.encode("http://www.w3.org/2000/09/xmldsig#rsa-sha1", "UTF-8").trim());
            final java.security.Signature signature = java.security.Signature.getInstance("SHA1withRSA");
            signature.initSign(cred.getPrivateKey());
            signature.update(httpQueryString.toString().getBytes());
            final byte[] signatureByteArray = signature.sign();
            final String signatureBase64encodedString = org.opensaml.xml.util.Base64.encodeBytes(signatureByteArray, 8);
            httpQueryString.append("&Signature=" + URLEncoder.encode(signatureBase64encodedString, "UTF-8").trim());
        }
        catch (Exception e) {
            throw new SSOAgentException("Error applying SAML2 Redirect Binding signature", e);
        }
    }
    
    private static XMLObject buildXMLObject(final QName objectQName) throws SSOAgentException {
        final XMLObjectBuilder builder = Configuration.getBuilderFactory().getBuilder(objectQName);
        if (builder == null) {
            throw new SSOAgentException("Unable to retrieve builder for object QName " + objectQName);
        }
        return builder.buildObject(objectQName.getNamespaceURI(), objectQName.getLocalPart(), objectQName.getPrefix());
    }
    
    static {
        SSOAgentUtils.LOGGER = Logger.getLogger("InfoLogging");
    }
}
