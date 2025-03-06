// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.apps.svgpp;

import java.util.Iterator;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.batik.transcoder.TranscoderOutput;
import java.io.FileWriter;
import java.io.Reader;
import org.apache.batik.transcoder.TranscoderInput;
import java.io.FileReader;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import java.util.HashMap;
import org.apache.batik.transcoder.Transcoder;
import java.util.Map;
import org.apache.batik.i18n.LocalizableSupport;

public class Main
{
    public static final String BUNDLE_CLASSNAME = "org.apache.batik.apps.svgpp.resources.Messages";
    protected static LocalizableSupport localizableSupport;
    protected String[] arguments;
    protected int index;
    protected Map handlers;
    protected Transcoder transcoder;
    
    public static void main(final String[] array) {
        new Main(array).run();
    }
    
    public Main(final String[] arguments) {
        (this.handlers = new HashMap()).put("-doctype", new DoctypeHandler());
        this.handlers.put("-doc-width", new DocWidthHandler());
        this.handlers.put("-newline", new NewlineHandler());
        this.handlers.put("-public-id", new PublicIdHandler());
        this.handlers.put("-no-format", new NoFormatHandler());
        this.handlers.put("-system-id", new SystemIdHandler());
        this.handlers.put("-tab-width", new TabWidthHandler());
        this.handlers.put("-xml-decl", new XMLDeclHandler());
        this.transcoder = new SVGTranscoder();
        this.arguments = arguments;
    }
    
    public void run() {
        if (this.arguments.length == 0) {
            this.printUsage();
            return;
        }
        Label_0013: {
            break Label_0013;
            try {
                while (true) {
                    final OptionHandler optionHandler = this.handlers.get(this.arguments[this.index]);
                    if (optionHandler == null) {
                        break;
                    }
                    optionHandler.handleOption();
                }
                final TranscoderInput transcoderInput = new TranscoderInput(new FileReader(this.arguments[this.index++]));
                TranscoderOutput transcoderOutput;
                if (this.index < this.arguments.length) {
                    transcoderOutput = new TranscoderOutput(new FileWriter(this.arguments[this.index]));
                }
                else {
                    transcoderOutput = new TranscoderOutput(new OutputStreamWriter(System.out));
                }
                this.transcoder.transcode(transcoderInput, transcoderOutput);
            }
            catch (Exception ex) {
                ex.printStackTrace();
                this.printUsage();
            }
        }
    }
    
    protected void printUsage() {
        this.printHeader();
        System.out.println(Main.localizableSupport.formatMessage("syntax", null));
        System.out.println();
        System.out.println(Main.localizableSupport.formatMessage("options", null));
        final Iterator<String> iterator = this.handlers.keySet().iterator();
        while (iterator.hasNext()) {
            System.out.println(((OptionHandler)this.handlers.get(iterator.next())).getDescription());
        }
    }
    
    protected void printHeader() {
        System.out.println(Main.localizableSupport.formatMessage("header", null));
    }
    
    static {
        Main.localizableSupport = new LocalizableSupport("org.apache.batik.apps.svgpp.resources.Messages", Main.class.getClassLoader());
    }
    
    protected class DocWidthHandler implements OptionHandler
    {
        public void handleOption() {
            final Main this$0 = Main.this;
            ++this$0.index;
            if (Main.this.index >= Main.this.arguments.length) {
                throw new IllegalArgumentException();
            }
            Main.this.transcoder.addTranscodingHint(SVGTranscoder.KEY_DOCUMENT_WIDTH, new Integer(Main.this.arguments[Main.this.index++]));
        }
        
        public String getDescription() {
            return Main.localizableSupport.formatMessage("doc-width.description", null);
        }
    }
    
    protected class TabWidthHandler implements OptionHandler
    {
        public void handleOption() {
            final Main this$0 = Main.this;
            ++this$0.index;
            if (Main.this.index >= Main.this.arguments.length) {
                throw new IllegalArgumentException();
            }
            Main.this.transcoder.addTranscodingHint(SVGTranscoder.KEY_TABULATION_WIDTH, new Integer(Main.this.arguments[Main.this.index++]));
        }
        
        public String getDescription() {
            return Main.localizableSupport.formatMessage("tab-width.description", null);
        }
    }
    
    protected interface OptionHandler
    {
        void handleOption();
        
        String getDescription();
    }
    
    protected class XMLDeclHandler implements OptionHandler
    {
        public void handleOption() {
            final Main this$0 = Main.this;
            ++this$0.index;
            if (Main.this.index >= Main.this.arguments.length) {
                throw new IllegalArgumentException();
            }
            Main.this.transcoder.addTranscodingHint(SVGTranscoder.KEY_XML_DECLARATION, Main.this.arguments[Main.this.index++]);
        }
        
        public String getDescription() {
            return Main.localizableSupport.formatMessage("xml-decl.description", null);
        }
    }
    
    protected class SystemIdHandler implements OptionHandler
    {
        public void handleOption() {
            final Main this$0 = Main.this;
            ++this$0.index;
            if (Main.this.index >= Main.this.arguments.length) {
                throw new IllegalArgumentException();
            }
            Main.this.transcoder.addTranscodingHint(SVGTranscoder.KEY_SYSTEM_ID, Main.this.arguments[Main.this.index++]);
        }
        
        public String getDescription() {
            return Main.localizableSupport.formatMessage("system-id.description", null);
        }
    }
    
    protected class PublicIdHandler implements OptionHandler
    {
        public void handleOption() {
            final Main this$0 = Main.this;
            ++this$0.index;
            if (Main.this.index >= Main.this.arguments.length) {
                throw new IllegalArgumentException();
            }
            Main.this.transcoder.addTranscodingHint(SVGTranscoder.KEY_PUBLIC_ID, Main.this.arguments[Main.this.index++]);
        }
        
        public String getDescription() {
            return Main.localizableSupport.formatMessage("public-id.description", null);
        }
    }
    
    protected class NoFormatHandler implements OptionHandler
    {
        public void handleOption() {
            final Main this$0 = Main.this;
            ++this$0.index;
            Main.this.transcoder.addTranscodingHint(SVGTranscoder.KEY_FORMAT, Boolean.FALSE);
        }
        
        public String getDescription() {
            return Main.localizableSupport.formatMessage("no-format.description", null);
        }
    }
    
    protected class NewlineHandler implements OptionHandler
    {
        protected final Map values;
        
        protected NewlineHandler() {
            (this.values = new HashMap(6)).put("cr", SVGTranscoder.VALUE_NEWLINE_CR);
            this.values.put("cr-lf", SVGTranscoder.VALUE_NEWLINE_CR_LF);
            this.values.put("lf", SVGTranscoder.VALUE_NEWLINE_LF);
        }
        
        public void handleOption() {
            final Main this$0 = Main.this;
            ++this$0.index;
            if (Main.this.index >= Main.this.arguments.length) {
                throw new IllegalArgumentException();
            }
            final Object value = this.values.get(Main.this.arguments[Main.this.index++]);
            if (value == null) {
                throw new IllegalArgumentException();
            }
            Main.this.transcoder.addTranscodingHint(SVGTranscoder.KEY_NEWLINE, value);
        }
        
        public String getDescription() {
            return Main.localizableSupport.formatMessage("newline.description", null);
        }
    }
    
    protected class DoctypeHandler implements OptionHandler
    {
        protected final Map values;
        
        protected DoctypeHandler() {
            (this.values = new HashMap(6)).put("remove", SVGTranscoder.VALUE_DOCTYPE_REMOVE);
            this.values.put("change", SVGTranscoder.VALUE_DOCTYPE_CHANGE);
        }
        
        public void handleOption() {
            final Main this$0 = Main.this;
            ++this$0.index;
            if (Main.this.index >= Main.this.arguments.length) {
                throw new IllegalArgumentException();
            }
            final Object value = this.values.get(Main.this.arguments[Main.this.index++]);
            if (value == null) {
                throw new IllegalArgumentException();
            }
            Main.this.transcoder.addTranscodingHint(SVGTranscoder.KEY_DOCTYPE, value);
        }
        
        public String getDescription() {
            return Main.localizableSupport.formatMessage("doctype.description", null);
        }
    }
}
