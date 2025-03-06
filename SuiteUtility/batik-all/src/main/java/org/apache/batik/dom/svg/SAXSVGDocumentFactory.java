// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import java.util.MissingResourceException;
import java.io.StringReader;
import org.xml.sax.SAXException;
import org.apache.batik.dom.svg12.SVG12DOMImplementation;
import org.w3c.dom.DOMImplementation;
import java.net.MalformedURLException;
import org.xml.sax.InputSource;
import org.apache.batik.util.MimeTypeConstants;
import org.apache.batik.util.ParsedURL;
import org.w3c.dom.Document;
import java.io.Reader;
import java.io.InputStream;
import java.io.IOException;
import org.w3c.dom.svg.SVGDocument;
import java.util.Properties;
import org.apache.batik.dom.util.SAXDocumentFactory;

public class SAXSVGDocumentFactory extends SAXDocumentFactory implements SVGDocumentFactory
{
    public static final Object LOCK;
    public static final String KEY_PUBLIC_IDS = "publicIds";
    public static final String KEY_SKIPPABLE_PUBLIC_IDS = "skippablePublicIds";
    public static final String KEY_SKIP_DTD = "skipDTD";
    public static final String KEY_SYSTEM_ID = "systemId.";
    protected static final String DTDIDS = "org.apache.batik.dom.svg.resources.dtdids";
    protected static final String HTTP_CHARSET = "charset";
    protected static String dtdids;
    protected static String skippable_dtdids;
    protected static String skip_dtd;
    protected static Properties dtdProps;
    
    public SAXSVGDocumentFactory(final String s) {
        super(SVGDOMImplementation.getDOMImplementation(), s);
    }
    
    public SAXSVGDocumentFactory(final String s, final boolean b) {
        super(SVGDOMImplementation.getDOMImplementation(), s, b);
    }
    
    public SVGDocument createSVGDocument(final String s) throws IOException {
        return (SVGDocument)this.createDocument(s);
    }
    
    public SVGDocument createSVGDocument(final String s, final InputStream inputStream) throws IOException {
        return (SVGDocument)this.createDocument(s, inputStream);
    }
    
    public SVGDocument createSVGDocument(final String s, final Reader reader) throws IOException {
        return (SVGDocument)this.createDocument(s, reader);
    }
    
    public Document createDocument(final String systemId) throws IOException {
        final ParsedURL parsedURL = new ParsedURL(systemId);
        final InputSource inputSource = new InputSource(parsedURL.openStream(MimeTypeConstants.MIME_TYPES_SVG));
        String s = parsedURL.getContentType();
        int index = -1;
        if (s != null) {
            s = s.toLowerCase();
            index = s.indexOf("charset");
        }
        String trim = null;
        if (index != -1) {
            int index2 = s.indexOf(61, index + "charset".length());
            if (index2 != -1) {
                ++index2;
                int index3 = s.indexOf(44, index2);
                final int index4 = s.indexOf(59, index2);
                if (index4 != -1 && (index4 < index3 || index3 == -1)) {
                    index3 = index4;
                }
                String s2;
                if (index3 != -1) {
                    s2 = s.substring(index2, index3);
                }
                else {
                    s2 = s.substring(index2);
                }
                trim = s2.trim();
                inputSource.setEncoding(trim);
            }
        }
        inputSource.setSystemId(systemId);
        final SVGOMDocument svgomDocument = (SVGOMDocument)super.createDocument("http://www.w3.org/2000/svg", "svg", systemId, inputSource);
        svgomDocument.setParsedURL(parsedURL);
        svgomDocument.setDocumentInputEncoding(trim);
        svgomDocument.setXmlStandalone(this.isStandalone);
        svgomDocument.setXmlVersion(this.xmlVersion);
        return svgomDocument;
    }
    
    public Document createDocument(final String s, final InputStream byteStream) throws IOException {
        final InputSource inputSource = new InputSource(byteStream);
        inputSource.setSystemId(s);
        Document document;
        try {
            document = super.createDocument("http://www.w3.org/2000/svg", "svg", s, inputSource);
            if (s != null) {
                ((SVGOMDocument)document).setParsedURL(new ParsedURL(s));
            }
            final SVGOMDocument svgomDocument = (SVGOMDocument)document;
            svgomDocument.setDocumentURI(s);
            svgomDocument.setXmlStandalone(this.isStandalone);
            svgomDocument.setXmlVersion(this.xmlVersion);
        }
        catch (MalformedURLException ex) {
            throw new IOException(ex.getMessage());
        }
        return document;
    }
    
    public Document createDocument(final String s, final Reader characterStream) throws IOException {
        final InputSource inputSource = new InputSource(characterStream);
        inputSource.setSystemId(s);
        Document document;
        try {
            document = super.createDocument("http://www.w3.org/2000/svg", "svg", s, inputSource);
            if (s != null) {
                ((SVGOMDocument)document).setParsedURL(new ParsedURL(s));
            }
            final SVGOMDocument svgomDocument = (SVGOMDocument)document;
            svgomDocument.setDocumentURI(s);
            svgomDocument.setXmlStandalone(this.isStandalone);
            svgomDocument.setXmlVersion(this.xmlVersion);
        }
        catch (MalformedURLException ex) {
            throw new IOException(ex.getMessage());
        }
        return document;
    }
    
    public Document createDocument(final String anObject, final String anObject2, final String s) throws IOException {
        if (!"http://www.w3.org/2000/svg".equals(anObject) || !"svg".equals(anObject2)) {
            throw new RuntimeException("Bad root element");
        }
        return this.createDocument(s);
    }
    
    public Document createDocument(final String anObject, final String anObject2, final String s, final InputStream inputStream) throws IOException {
        if (!"http://www.w3.org/2000/svg".equals(anObject) || !"svg".equals(anObject2)) {
            throw new RuntimeException("Bad root element");
        }
        return this.createDocument(s, inputStream);
    }
    
    public Document createDocument(final String anObject, final String anObject2, final String s, final Reader reader) throws IOException {
        if (!"http://www.w3.org/2000/svg".equals(anObject) || !"svg".equals(anObject2)) {
            throw new RuntimeException("Bad root element");
        }
        return this.createDocument(s, reader);
    }
    
    public DOMImplementation getDOMImplementation(final String str) {
        if (str == null || str.length() == 0 || str.equals("1.0") || str.equals("1.1")) {
            return SVGDOMImplementation.getDOMImplementation();
        }
        if (str.equals("1.2")) {
            return SVG12DOMImplementation.getDOMImplementation();
        }
        throw new RuntimeException("Unsupport SVG version '" + str + "'");
    }
    
    public void startDocument() throws SAXException {
        super.startDocument();
    }
    
    public InputSource resolveEntity(final String s, final String s2) throws SAXException {
        try {
            synchronized (SAXSVGDocumentFactory.LOCK) {
                if (SAXSVGDocumentFactory.dtdProps == null) {
                    SAXSVGDocumentFactory.dtdProps = new Properties();
                    try {
                        SAXSVGDocumentFactory.dtdProps.load(SAXSVGDocumentFactory.class.getResourceAsStream("resources/dtdids.properties"));
                    }
                    catch (IOException e) {
                        throw new SAXException(e);
                    }
                }
                if (SAXSVGDocumentFactory.dtdids == null) {
                    SAXSVGDocumentFactory.dtdids = SAXSVGDocumentFactory.dtdProps.getProperty("publicIds");
                }
                if (SAXSVGDocumentFactory.skippable_dtdids == null) {
                    SAXSVGDocumentFactory.skippable_dtdids = SAXSVGDocumentFactory.dtdProps.getProperty("skippablePublicIds");
                }
                if (SAXSVGDocumentFactory.skip_dtd == null) {
                    SAXSVGDocumentFactory.skip_dtd = SAXSVGDocumentFactory.dtdProps.getProperty("skipDTD");
                }
            }
            if (s == null) {
                return null;
            }
            if (!this.isValidating && SAXSVGDocumentFactory.skippable_dtdids.indexOf(s) != -1) {
                return new InputSource(new StringReader(SAXSVGDocumentFactory.skip_dtd));
            }
            if (SAXSVGDocumentFactory.dtdids.indexOf(s) != -1) {
                final String property = SAXSVGDocumentFactory.dtdProps.getProperty("systemId." + s.replace(' ', '_'));
                if (property != null && !"".equals(property)) {
                    return new InputSource(this.getClass().getResource(property).toString());
                }
            }
        }
        catch (MissingResourceException e2) {
            throw new SAXException(e2);
        }
        return null;
    }
    
    static {
        LOCK = new Object();
    }
}
