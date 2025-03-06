// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.util;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DocumentFragment;
import java.util.Iterator;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;
import java.util.ArrayList;
import org.w3c.dom.NodeList;
import java.io.StringWriter;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Attr;
import java.io.IOException;
import org.w3c.dom.Node;
import java.io.Writer;
import org.w3c.dom.Document;
import org.apache.batik.xml.XMLUtilities;

public class DOMUtilities extends XMLUtilities
{
    protected static final String[] LOCK_STRINGS;
    protected static final String[] MODIFIER_STRINGS;
    
    protected DOMUtilities() {
    }
    
    public static void writeDocument(final Document document, final Writer writer) throws IOException {
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            writeNode(node, writer);
        }
    }
    
    public static void writeNode(final Node node, final Writer writer) throws IOException {
        switch (node.getNodeType()) {
            case 1: {
                writer.write("<");
                writer.write(node.getNodeName());
                if (node.hasAttributes()) {
                    final NamedNodeMap attributes = node.getAttributes();
                    for (int length = attributes.getLength(), i = 0; i < length; ++i) {
                        final Attr attr = (Attr)attributes.item(i);
                        writer.write(" ");
                        writer.write(attr.getNodeName());
                        writer.write("=\"");
                        writer.write(contentToString(attr.getNodeValue()));
                        writer.write("\"");
                    }
                }
                Node node2 = node.getFirstChild();
                if (node2 != null) {
                    writer.write(">");
                    while (node2 != null) {
                        writeNode(node2, writer);
                        node2 = node2.getNextSibling();
                    }
                    writer.write("</");
                    writer.write(node.getNodeName());
                    writer.write(">");
                    break;
                }
                writer.write("/>");
                break;
            }
            case 3: {
                writer.write(contentToString(node.getNodeValue()));
                break;
            }
            case 4: {
                writer.write("<![CDATA[");
                writer.write(node.getNodeValue());
                writer.write("]]>");
                break;
            }
            case 5: {
                writer.write("&");
                writer.write(node.getNodeName());
                writer.write(";");
                break;
            }
            case 7: {
                writer.write("<?");
                writer.write(node.getNodeName());
                writer.write(" ");
                writer.write(node.getNodeValue());
                writer.write("?>");
                break;
            }
            case 8: {
                writer.write("<!--");
                writer.write(node.getNodeValue());
                writer.write("-->");
                break;
            }
            case 10: {
                final DocumentType documentType = (DocumentType)node;
                writer.write("<!DOCTYPE ");
                writer.write(node.getOwnerDocument().getDocumentElement().getNodeName());
                final String publicId = documentType.getPublicId();
                if (publicId != null) {
                    writer.write(" PUBLIC \"" + documentType.getNodeName() + "\" \"" + publicId + "\">");
                    break;
                }
                final String systemId = documentType.getSystemId();
                if (systemId != null) {
                    writer.write(" SYSTEM \"" + systemId + "\">");
                    break;
                }
                break;
            }
            default: {
                throw new IOException("Unknown DOM node type " + node.getNodeType());
            }
        }
    }
    
    public static String getXML(final Node node) {
        final StringWriter stringWriter = new StringWriter();
        try {
            writeNode(node, stringWriter);
            stringWriter.close();
        }
        catch (IOException ex) {
            return "";
        }
        return stringWriter.toString();
    }
    
    public static String contentToString(final String s) {
        final StringBuffer sb = new StringBuffer(s.length());
        for (int i = 0; i < s.length(); ++i) {
            final char char1 = s.charAt(i);
            switch (char1) {
                case 60: {
                    sb.append("&lt;");
                    break;
                }
                case 62: {
                    sb.append("&gt;");
                    break;
                }
                case 38: {
                    sb.append("&amp;");
                    break;
                }
                case 34: {
                    sb.append("&quot;");
                    break;
                }
                case 39: {
                    sb.append("&apos;");
                    break;
                }
                default: {
                    sb.append(char1);
                    break;
                }
            }
        }
        return sb.toString();
    }
    
    public static int getChildIndex(final Node node, final Node node2) {
        if (node == null || node.getParentNode() != node2 || node.getParentNode() == null) {
            return -1;
        }
        return getChildIndex(node);
    }
    
    public static int getChildIndex(final Node node) {
        final NodeList childNodes = node.getParentNode().getChildNodes();
        for (int i = 0; i < childNodes.getLength(); ++i) {
            if (childNodes.item(i) == node) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean isAnyNodeAncestorOf(final ArrayList list, final Node node) {
        for (int size = list.size(), i = 0; i < size; ++i) {
            if (isAncestorOf(list.get(i), node)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAncestorOf(final Node node, final Node node2) {
        if (node == null || node2 == null) {
            return false;
        }
        for (Node node3 = node2.getParentNode(); node3 != null; node3 = node3.getParentNode()) {
            if (node3 == node) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isParentOf(final Node node, final Node node2) {
        return node != null && node2 != null && node.getParentNode() == node2;
    }
    
    public static boolean canAppend(final Node node, final Node node2) {
        return node != null && node2 != null && node != node2 && !isAncestorOf(node, node2);
    }
    
    public static boolean canAppendAny(final ArrayList list, final Node node) {
        if (!canHaveChildren(node)) {
            return false;
        }
        for (int size = list.size(), i = 0; i < size; ++i) {
            if (canAppend(list.get(i), node)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean canHaveChildren(final Node node) {
        if (node == null) {
            return false;
        }
        switch (node.getNodeType()) {
            case 3:
            case 4:
            case 7:
            case 8:
            case 9: {
                return false;
            }
            default: {
                return true;
            }
        }
    }
    
    public static Node parseXML(final String s, final Document document, final String s2, final Map map, final String s3, final SAXDocumentFactory saxDocumentFactory) {
        String string = "";
        String string2 = "";
        if (s3 != null) {
            String str = "<" + s3;
            if (map != null) {
                str += " ";
                for (final String str2 : map.keySet()) {
                    str = str + str2 + "=\"" + map.get(str2) + "\" ";
                }
            }
            string = str + ">";
            string2 = string2 + "</" + s3 + ">";
        }
        if (string.trim().length() == 0 && string2.trim().length() == 0) {
            try {
                final Document document2 = saxDocumentFactory.createDocument(s2, new StringReader(s));
                if (document == null) {
                    return document2;
                }
                final DocumentFragment documentFragment = document.createDocumentFragment();
                documentFragment.appendChild(document.importNode(document2.getDocumentElement(), true));
                return documentFragment;
            }
            catch (Exception ex) {}
        }
        final StringBuffer sb = new StringBuffer(string.length() + s.length() + string2.length());
        sb.append(string);
        sb.append(s);
        sb.append(string2);
        final String string3 = sb.toString();
        try {
            final Document document3 = saxDocumentFactory.createDocument(s2, new StringReader(string3));
            if (document == null) {
                return document3;
            }
            for (Node node = document3.getDocumentElement().getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == 1) {
                    final Node importNode = document.importNode(node, true);
                    final DocumentFragment documentFragment2 = document.createDocumentFragment();
                    documentFragment2.appendChild(importNode);
                    return documentFragment2;
                }
            }
        }
        catch (Exception ex2) {}
        return null;
    }
    
    public static Document deepCloneDocument(final Document document, final DOMImplementation domImplementation) {
        final Element documentElement = document.getDocumentElement();
        final Document document2 = domImplementation.createDocument(documentElement.getNamespaceURI(), documentElement.getNodeName(), null);
        final Element documentElement2 = document2.getDocumentElement();
        boolean b = true;
        for (Node node = document.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node == documentElement) {
                b = false;
                if (documentElement.hasAttributes()) {
                    final NamedNodeMap attributes = documentElement.getAttributes();
                    for (int length = attributes.getLength(), i = 0; i < length; ++i) {
                        documentElement2.setAttributeNode((Attr)document2.importNode(attributes.item(i), true));
                    }
                }
                for (Node node2 = documentElement.getFirstChild(); node2 != null; node2 = node2.getNextSibling()) {
                    documentElement2.appendChild(document2.importNode(node2, true));
                }
            }
            else if (node.getNodeType() != 10) {
                if (b) {
                    document2.insertBefore(document2.importNode(node, true), documentElement2);
                }
                else {
                    document2.appendChild(document2.importNode(node, true));
                }
            }
        }
        return document2;
    }
    
    public static boolean isValidName(final String s) {
        final int length = s.length();
        if (length == 0) {
            return false;
        }
        final char char1 = s.charAt(0);
        if ((DOMUtilities.NAME_FIRST_CHARACTER[char1 / ' '] & 1 << char1 % ' ') == 0x0) {
            return false;
        }
        for (int i = 1; i < length; ++i) {
            final char char2 = s.charAt(i);
            if ((DOMUtilities.NAME_CHARACTER[char2 / ' '] & 1 << char2 % ' ') == 0x0) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValidName11(final String s) {
        final int length = s.length();
        if (length == 0) {
            return false;
        }
        final char char1 = s.charAt(0);
        if ((DOMUtilities.NAME11_FIRST_CHARACTER[char1 / ' '] & 1 << char1 % ' ') == 0x0) {
            return false;
        }
        for (int i = 1; i < length; ++i) {
            final char char2 = s.charAt(i);
            if ((DOMUtilities.NAME11_CHARACTER[char2 / ' '] & 1 << char2 % ' ') == 0x0) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValidPrefix(final String s) {
        return s.indexOf(58) == -1;
    }
    
    public static String getPrefix(final String s) {
        final int index = s.indexOf(58);
        return (index == -1 || index == s.length() - 1) ? null : s.substring(0, index);
    }
    
    public static String getLocalName(final String s) {
        final int index = s.indexOf(58);
        return (index == -1 || index == s.length() - 1) ? s : s.substring(index + 1);
    }
    
    public static void parseStyleSheetPIData(final String str, final HashTable hashTable) {
        int i;
        for (i = 0; i < str.length(); ++i) {
            if (!XMLUtilities.isXMLSpace(str.charAt(i))) {
                break;
            }
        }
        while (i < str.length()) {
            final char char1 = str.charAt(i);
            if ((DOMUtilities.NAME_FIRST_CHARACTER[char1 / ' '] & 1 << char1 % ' ') == 0x0) {
                throw new DOMException((short)5, "Wrong name initial:  " + char1);
            }
            final StringBuffer sb = new StringBuffer();
            sb.append(char1);
            while (++i < str.length()) {
                final char char2 = str.charAt(i);
                if ((DOMUtilities.NAME_CHARACTER[char2 / ' '] & 1 << char2 % ' ') == 0x0) {
                    break;
                }
                sb.append(char2);
            }
            if (i >= str.length()) {
                throw new DOMException((short)12, "Wrong xml-stylesheet data: " + str);
            }
            while (i < str.length() && XMLUtilities.isXMLSpace(str.charAt(i))) {
                ++i;
            }
            if (i >= str.length()) {
                throw new DOMException((short)12, "Wrong xml-stylesheet data: " + str);
            }
            if (str.charAt(i) != '=') {
                throw new DOMException((short)12, "Wrong xml-stylesheet data: " + str);
            }
            ++i;
            while (i < str.length() && XMLUtilities.isXMLSpace(str.charAt(i))) {
                ++i;
            }
            if (i >= str.length()) {
                throw new DOMException((short)12, "Wrong xml-stylesheet data: " + str);
            }
            final char char3 = str.charAt(i);
            ++i;
            final StringBuffer sb2 = new StringBuffer();
            if (char3 == '\'') {
                while (i < str.length()) {
                    final char char4 = str.charAt(i);
                    if (char4 == '\'') {
                        break;
                    }
                    sb2.append(char4);
                    ++i;
                }
                if (i >= str.length()) {
                    throw new DOMException((short)12, "Wrong xml-stylesheet data: " + str);
                }
            }
            else {
                if (char3 != '\"') {
                    throw new DOMException((short)12, "Wrong xml-stylesheet data: " + str);
                }
                while (i < str.length()) {
                    final char char5 = str.charAt(i);
                    if (char5 == '\"') {
                        break;
                    }
                    sb2.append(char5);
                    ++i;
                }
                if (i >= str.length()) {
                    throw new DOMException((short)12, "Wrong xml-stylesheet data: " + str);
                }
            }
            hashTable.put(sb.toString().intern(), sb2.toString());
            ++i;
            while (i < str.length()) {
                if (!XMLUtilities.isXMLSpace(str.charAt(i))) {
                    break;
                }
                ++i;
            }
        }
    }
    
    public static String getModifiersList(final int n, final int n2) {
        return DOMUtilitiesSupport.getModifiersList(n, n2);
    }
    
    static {
        LOCK_STRINGS = new String[] { "", "CapsLock", "NumLock", "NumLock CapsLock", "Scroll", "Scroll CapsLock", "Scroll NumLock", "Scroll NumLock CapsLock", "KanaMode", "KanaMode CapsLock", "KanaMode NumLock", "KanaMode NumLock CapsLock", "KanaMode Scroll", "KanaMode Scroll CapsLock", "KanaMode Scroll NumLock", "KanaMode Scroll NumLock CapsLock" };
        MODIFIER_STRINGS = new String[] { "", "Shift", "Control", "Control Shift", "Meta", "Meta Shift", "Control Meta", "Control Meta Shift", "Alt", "Alt Shift", "Alt Control", "Alt Control Shift", "Alt Meta", "Alt Meta Shift", "Alt Control Meta", "Alt Control Meta Shift", "AltGraph", "AltGraph Shift", "AltGraph Control", "AltGraph Control Shift", "AltGraph Meta", "AltGraph Meta Shift", "AltGraph Control Meta", "AltGraph Control Meta Shift", "Alt AltGraph", "Alt AltGraph Shift", "Alt AltGraph Control", "Alt AltGraph Control Shift", "Alt AltGraph Meta", "Alt AltGraph Meta Shift", "Alt AltGraph Control Meta", "Alt AltGraph Control Meta Shift" };
    }
}
