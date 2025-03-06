// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgbrowser;

import java.util.Iterator;
import org.apache.batik.dom.util.DOMUtilities;
import java.util.HashMap;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.IOException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.batik.dom.util.SAXDocumentFactory;
import org.apache.batik.dom.GenericDOMImplementation;
import java.io.InputStream;
import java.util.Properties;
import java.util.Map;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.batik.util.PreferenceManager;

public class XMLPreferenceManager extends PreferenceManager
{
    protected String xmlParserClassName;
    public static final String PREFERENCE_ENCODING = "8859_1";
    
    public XMLPreferenceManager(final String s) {
        this(s, null, XMLResourceDescriptor.getXMLParserClassName());
    }
    
    public XMLPreferenceManager(final String s, final Map map) {
        this(s, map, XMLResourceDescriptor.getXMLParserClassName());
    }
    
    public XMLPreferenceManager(final String s, final String s2) {
        this(s, null, s2);
    }
    
    public XMLPreferenceManager(final String s, final Map map, final String xmlParserClassName) {
        super(s, map);
        this.internal = new XMLProperties();
        this.xmlParserClassName = xmlParserClassName;
    }
    
    protected class XMLProperties extends Properties
    {
        public synchronized void load(final InputStream in) throws IOException {
            for (Node node = new SAXDocumentFactory(GenericDOMImplementation.getDOMImplementation(), XMLPreferenceManager.this.xmlParserClassName).createDocument("http://xml.apache.org/batik/preferences", "preferences", null, new BufferedReader(new InputStreamReader(in, "8859_1"))).getDocumentElement().getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == 1 && node.getNodeName().equals("property")) {
                    final String attributeNS = ((Element)node).getAttributeNS(null, "name");
                    final StringBuffer sb = new StringBuffer();
                    for (Node node2 = node.getFirstChild(); node2 != null && node2.getNodeType() == 3; node2 = node2.getNextSibling()) {
                        sb.append(node2.getNodeValue());
                    }
                    this.put(attributeNS, sb.toString());
                }
            }
        }
        
        public synchronized void store(final OutputStream out, final String s) throws IOException {
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "8859_1"));
            final HashMap hashMap = new HashMap<Object, Object>();
            this.enumerate(hashMap);
            bufferedWriter.write("<preferences xmlns=\"http://xml.apache.org/batik/preferences\">\n");
            for (final String str : hashMap.keySet()) {
                final String s2 = hashMap.get(str);
                bufferedWriter.write("<property name=\"" + str + "\">");
                bufferedWriter.write(DOMUtilities.contentToString(s2));
                bufferedWriter.write("</property>\n");
            }
            bufferedWriter.write("</preferences>\n");
            bufferedWriter.flush();
        }
        
        private synchronized void enumerate(final Map map) {
            if (this.defaults != null) {
                for (final Object next : map.keySet()) {
                    map.put(next, this.defaults.get(next));
                }
            }
            for (final Object next2 : this.keySet()) {
                map.put(next2, this.get(next2));
            }
        }
    }
}
