// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.Node;
import java.util.ArrayList;
import java.io.IOException;
import org.apache.batik.parser.ParseException;
import org.apache.batik.xml.XMLUtilities;
import org.apache.batik.parser.AbstractScanner;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg12.XBLOMContentElement;

public class XPathSubsetContentSelector extends AbstractContentSelector
{
    protected static final int SELECTOR_INVALID = -1;
    protected static final int SELECTOR_ANY = 0;
    protected static final int SELECTOR_QNAME = 1;
    protected static final int SELECTOR_ID = 2;
    protected int selectorType;
    protected String prefix;
    protected String localName;
    protected int index;
    protected SelectedNodes selectedContent;
    
    public XPathSubsetContentSelector(final ContentManager contentManager, final XBLOMContentElement xblomContentElement, final Element element, final String s) {
        super(contentManager, xblomContentElement, element);
        this.parseSelector(s);
    }
    
    protected void parseSelector(final String s) {
        this.selectorType = -1;
        final Scanner scanner = new Scanner(s);
        final int next = scanner.next();
        if (next == 1) {
            final String stringValue = scanner.getStringValue();
            final int next2 = scanner.next();
            if (next2 == 0) {
                this.selectorType = 1;
                this.prefix = null;
                this.localName = stringValue;
                this.index = 0;
                return;
            }
            if (next2 == 2) {
                final int next3 = scanner.next();
                if (next3 == 1) {
                    final String stringValue2 = scanner.getStringValue();
                    final int next4 = scanner.next();
                    if (next4 == 0) {
                        this.selectorType = 1;
                        this.prefix = stringValue;
                        this.localName = stringValue2;
                        this.index = 0;
                        return;
                    }
                    if (next4 == 3 && scanner.next() == 8) {
                        final int int1 = Integer.parseInt(scanner.getStringValue());
                        if (scanner.next() == 4 && scanner.next() == 0) {
                            this.selectorType = 1;
                            this.prefix = stringValue;
                            this.localName = stringValue2;
                            this.index = int1;
                        }
                    }
                }
                else if (next3 == 3) {
                    if (scanner.next() == 8) {
                        final int int2 = Integer.parseInt(scanner.getStringValue());
                        if (scanner.next() == 4 && scanner.next() == 0) {
                            this.selectorType = 1;
                            this.prefix = null;
                            this.localName = stringValue;
                            this.index = int2;
                        }
                    }
                }
                else if (next3 == 5 && stringValue.equals("id") && scanner.next() == 7) {
                    final String stringValue3 = scanner.getStringValue();
                    if (scanner.next() == 6 && scanner.next() == 0) {
                        this.selectorType = 2;
                        this.localName = stringValue3;
                    }
                }
            }
        }
        else if (next == 9) {
            final int next5 = scanner.next();
            if (next5 == 0) {
                this.selectorType = 0;
                return;
            }
            if (next5 == 3 && scanner.next() == 8) {
                final int int3 = Integer.parseInt(scanner.getStringValue());
                if (scanner.next() == 4 && scanner.next() == 0) {
                    this.selectorType = 0;
                    this.index = int3;
                }
            }
        }
    }
    
    public NodeList getSelectedContent() {
        if (this.selectedContent == null) {
            this.selectedContent = new SelectedNodes();
        }
        return this.selectedContent;
    }
    
    boolean update() {
        if (this.selectedContent == null) {
            this.selectedContent = new SelectedNodes();
            return true;
        }
        return this.selectedContent.update();
    }
    
    protected static class Scanner extends AbstractScanner
    {
        public static final int EOF = 0;
        public static final int NAME = 1;
        public static final int COLON = 2;
        public static final int LEFT_SQUARE_BRACKET = 3;
        public static final int RIGHT_SQUARE_BRACKET = 4;
        public static final int LEFT_PARENTHESIS = 5;
        public static final int RIGHT_PARENTHESIS = 6;
        public static final int STRING = 7;
        public static final int NUMBER = 8;
        public static final int ASTERISK = 9;
        
        public Scanner(final String s) {
            super(s);
        }
        
        protected int endGap() {
            return (this.current != -1) ? 1 : 0;
        }
        
        protected void nextToken() throws ParseException {
            try {
                switch (this.current) {
                    case -1: {
                        this.type = 0;
                    }
                    case 58: {
                        this.nextChar();
                        this.type = 2;
                    }
                    case 91: {
                        this.nextChar();
                        this.type = 3;
                    }
                    case 93: {
                        this.nextChar();
                        this.type = 4;
                    }
                    case 40: {
                        this.nextChar();
                        this.type = 5;
                    }
                    case 41: {
                        this.nextChar();
                        this.type = 6;
                    }
                    case 42: {
                        this.nextChar();
                        this.type = 9;
                    }
                    case 9:
                    case 10:
                    case 12:
                    case 13:
                    case 32: {
                        do {
                            this.nextChar();
                        } while (XMLUtilities.isXMLSpace((char)this.current));
                        this.nextToken();
                    }
                    case 39: {
                        this.type = this.string1();
                    }
                    case 34: {
                        this.type = this.string2();
                    }
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57: {
                        this.type = this.number();
                    }
                    default: {
                        if (XMLUtilities.isXMLNameFirstCharacter((char)this.current)) {
                            do {
                                this.nextChar();
                            } while (this.current != -1 && this.current != 58 && XMLUtilities.isXMLNameCharacter((char)this.current));
                            this.type = 1;
                            return;
                        }
                        this.nextChar();
                        throw new ParseException("identifier.character", this.reader.getLine(), this.reader.getColumn());
                    }
                }
            }
            catch (IOException ex) {
                throw new ParseException(ex);
            }
        }
        
        protected int string1() throws IOException {
            this.start = this.position;
            while (true) {
                switch (this.nextChar()) {
                    case -1: {
                        throw new ParseException("eof", this.reader.getLine(), this.reader.getColumn());
                    }
                    case 39: {
                        this.nextChar();
                        return 7;
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        
        protected int string2() throws IOException {
            this.start = this.position;
            while (true) {
                switch (this.nextChar()) {
                    case -1: {
                        throw new ParseException("eof", this.reader.getLine(), this.reader.getColumn());
                    }
                    case 34: {
                        this.nextChar();
                        return 7;
                    }
                    default: {
                        continue;
                    }
                }
            }
        }
        
        protected int number() throws IOException {
            while (true) {
                switch (this.nextChar()) {
                    case 46: {
                        switch (this.nextChar()) {
                            case 48:
                            case 49:
                            case 50:
                            case 51:
                            case 52:
                            case 53:
                            case 54:
                            case 55:
                            case 56:
                            case 57: {
                                return this.dotNumber();
                            }
                            default: {
                                throw new ParseException("character", this.reader.getLine(), this.reader.getColumn());
                            }
                        }
                        break;
                    }
                    default: {
                        return 8;
                    }
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57: {
                        continue;
                    }
                }
            }
        }
        
        protected int dotNumber() throws IOException {
            while (true) {
                switch (this.nextChar()) {
                    default: {
                        return 8;
                    }
                    case 48:
                    case 49:
                    case 50:
                    case 51:
                    case 52:
                    case 53:
                    case 54:
                    case 55:
                    case 56:
                    case 57: {
                        continue;
                    }
                }
            }
        }
    }
    
    protected class SelectedNodes implements NodeList
    {
        protected ArrayList nodes;
        
        public SelectedNodes() {
            this.nodes = new ArrayList(10);
            this.update();
        }
        
        protected boolean update() {
            final ArrayList list = (ArrayList)this.nodes.clone();
            this.nodes.clear();
            int n = 0;
            for (Node node = XPathSubsetContentSelector.this.boundElement.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (node.getNodeType() == 1) {
                    final Element e = (Element)node;
                    boolean b = XPathSubsetContentSelector.this.selectorType == 0;
                    switch (XPathSubsetContentSelector.this.selectorType) {
                        case 2: {
                            b = e.getAttributeNS(null, "id").equals(XPathSubsetContentSelector.this.localName);
                            break;
                        }
                        case 1: {
                            if (XPathSubsetContentSelector.this.prefix == null) {
                                b = (e.getNamespaceURI() == null);
                            }
                            else {
                                final String lookupNamespaceURI = XPathSubsetContentSelector.this.contentElement.lookupNamespaceURI(XPathSubsetContentSelector.this.prefix);
                                if (lookupNamespaceURI != null) {
                                    b = e.getNamespaceURI().equals(lookupNamespaceURI);
                                }
                            }
                            b = (b && XPathSubsetContentSelector.this.localName.equals(e.getLocalName()));
                            break;
                        }
                    }
                    if (XPathSubsetContentSelector.this.selectorType == 0 || XPathSubsetContentSelector.this.selectorType == 1) {
                        b = (b && (XPathSubsetContentSelector.this.index == 0 || ++n == XPathSubsetContentSelector.this.index));
                    }
                    if (b && !XPathSubsetContentSelector.this.isSelected(node)) {
                        this.nodes.add(e);
                    }
                }
            }
            final int size = this.nodes.size();
            if (list.size() != size) {
                return true;
            }
            for (int i = 0; i < size; ++i) {
                if (list.get(i) != this.nodes.get(i)) {
                    return true;
                }
            }
            return false;
        }
        
        public Node item(final int index) {
            if (index < 0 || index >= this.nodes.size()) {
                return null;
            }
            return this.nodes.get(index);
        }
        
        public int getLength() {
            return this.nodes.size();
        }
    }
}
