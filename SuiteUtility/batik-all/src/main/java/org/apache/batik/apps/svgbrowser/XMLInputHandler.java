// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import org.apache.batik.dom.util.DOMUtilities;
import org.apache.batik.dom.util.HashTable;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Element;
import org.w3c.dom.Attr;
import org.w3c.dom.svg.SVGDocument;
import javax.xml.transform.Transformer;
import org.w3c.dom.Document;
import java.io.Reader;
import java.io.StringReader;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import javax.xml.transform.Result;
import java.io.Writer;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Node;
import javax.xml.transform.dom.DOMSource;
import java.io.StringWriter;
import javax.xml.transform.URIResolver;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import org.apache.batik.util.ParsedURL;
import java.io.File;

public class XMLInputHandler implements SquiggleInputHandler
{
    public static final String[] XVG_MIME_TYPES;
    public static final String[] XVG_FILE_EXTENSIONS;
    public static final String ERROR_NO_XML_STYLESHEET_PROCESSING_INSTRUCTION = "XMLInputHandler.error.no.xml.stylesheet.processing.instruction";
    public static final String ERROR_TRANSFORM_OUTPUT_NOT_SVG = "XMLInputHandler.error.transform.output.not.svg";
    public static final String ERROR_TRANSFORM_PRODUCED_NO_CONTENT = "XMLInputHandler.error.transform.produced.no.content";
    public static final String ERROR_TRANSFORM_OUTPUT_WRONG_NS = "XMLInputHandler.error.transform.output.wrong.ns";
    public static final String ERROR_RESULT_GENERATED_EXCEPTION = "XMLInputHandler.error.result.generated.exception";
    public static final String XSL_PROCESSING_INSTRUCTION_TYPE = "text/xsl";
    public static final String PSEUDO_ATTRIBUTE_TYPE = "type";
    public static final String PSEUDO_ATTRIBUTE_HREF = "href";
    
    public String[] getHandledMimeTypes() {
        return XMLInputHandler.XVG_MIME_TYPES;
    }
    
    public String[] getHandledExtensions() {
        return XMLInputHandler.XVG_FILE_EXTENSIONS;
    }
    
    public String getDescription() {
        return "";
    }
    
    public boolean accept(final File file) {
        return file.isFile() && this.accept(file.getPath());
    }
    
    public boolean accept(final ParsedURL parsedURL) {
        return parsedURL != null && this.accept(parsedURL.getPath());
    }
    
    public boolean accept(final String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < XMLInputHandler.XVG_FILE_EXTENSIONS.length; ++i) {
            if (s.endsWith(XMLInputHandler.XVG_FILE_EXTENSIONS[i])) {
                return true;
            }
        }
        return false;
    }
    
    public void handle(final ParsedURL parsedURL, final JSVGViewerFrame jsvgViewerFrame) throws Exception {
        final String string = parsedURL.toString();
        final TransformerFactory instance = TransformerFactory.newInstance();
        final DocumentBuilderFactory instance2 = DocumentBuilderFactory.newInstance();
        instance2.setValidating(false);
        instance2.setNamespaceAware(true);
        final Document parse = instance2.newDocumentBuilder().parse(string);
        String xslProcessingInstruction = this.extractXSLProcessingInstruction(parse);
        if (xslProcessingInstruction == null) {
            xslProcessingInstruction = string;
        }
        final ParsedURL parsedURL2 = new ParsedURL(string, xslProcessingInstruction);
        final Transformer transformer = instance.newTransformer(new StreamSource(parsedURL2.toString()));
        transformer.setURIResolver(new DocumentURIResolver(parsedURL2.toString()));
        final StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(parse), new StreamResult(writer));
        writer.flush();
        writer.close();
        final SAXSVGDocumentFactory saxsvgDocumentFactory = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
        SVGDocument svgDocument;
        try {
            svgDocument = saxsvgDocumentFactory.createSVGDocument(string, new StringReader(writer.toString()));
        }
        catch (Exception ex) {
            System.err.println("======================================");
            System.err.println(writer.toString());
            System.err.println("======================================");
            throw new IllegalArgumentException(Resources.getString("XMLInputHandler.error.result.generated.exception"));
        }
        jsvgViewerFrame.getJSVGCanvas().setSVGDocument(svgDocument);
        jsvgViewerFrame.setSVGDocument(svgDocument, string, svgDocument.getTitle());
    }
    
    protected void checkAndPatch(final Document document) {
        final Element documentElement = document.getDocumentElement();
        final Node firstChild = documentElement.getFirstChild();
        final String s = "http://www.w3.org/2000/svg";
        if (firstChild == null) {
            throw new IllegalArgumentException(Resources.getString("XMLInputHandler.error.transform.produced.no.content"));
        }
        if (firstChild.getNodeType() != 1 || !"svg".equals(firstChild.getLocalName())) {
            throw new IllegalArgumentException(Resources.getString("XMLInputHandler.error.transform.output.not.svg"));
        }
        if (!s.equals(firstChild.getNamespaceURI())) {
            throw new IllegalArgumentException(Resources.getString("XMLInputHandler.error.transform.output.wrong.ns"));
        }
        for (Node node = firstChild.getFirstChild(); node != null; node = firstChild.getFirstChild()) {
            documentElement.appendChild(node);
        }
        final NamedNodeMap attributes = firstChild.getAttributes();
        for (int length = attributes.getLength(), i = 0; i < length; ++i) {
            documentElement.setAttributeNode((Attr)attributes.item(i));
        }
        documentElement.removeChild(firstChild);
    }
    
    protected String extractXSLProcessingInstruction(final Document document) {
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() == 7) {
                final ProcessingInstruction processingInstruction = (ProcessingInstruction)node;
                final HashTable hashTable = new HashTable();
                DOMUtilities.parseStyleSheetPIData(processingInstruction.getData(), hashTable);
                if ("text/xsl".equals(hashTable.get("type"))) {
                    final Object value = hashTable.get("href");
                    if (value != null) {
                        return value.toString();
                    }
                    return null;
                }
            }
        }
        return null;
    }
    
    static {
        XVG_MIME_TYPES = new String[] { "image/xml+xsl+svg" };
        XVG_FILE_EXTENSIONS = new String[] { ".xml", ".xsl" };
    }
    
    public class DocumentURIResolver implements URIResolver
    {
        String documentURI;
        
        public DocumentURIResolver(final String documentURI) {
            this.documentURI = documentURI;
        }
        
        public Source resolve(final String s, String documentURI) {
            if (documentURI == null || "".equals(documentURI)) {
                documentURI = this.documentURI;
            }
            return new StreamSource(new ParsedURL(documentURI, s).toString());
        }
    }
}
