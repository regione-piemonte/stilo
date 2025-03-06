// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.parser;

import org.apache.batik.util.io.StringNormalizingReader;
import java.io.InputStream;
import java.io.IOException;
import org.apache.batik.util.io.StreamNormalizingReader;
import java.io.Reader;
import java.util.MissingResourceException;
import java.util.Locale;
import org.apache.batik.util.io.NormalizingReader;
import org.apache.batik.i18n.LocalizableSupport;

public abstract class AbstractParser implements Parser
{
    public static final String BUNDLE_CLASSNAME = "org.apache.batik.parser.resources.Messages";
    protected ErrorHandler errorHandler;
    protected LocalizableSupport localizableSupport;
    protected NormalizingReader reader;
    protected int current;
    
    public AbstractParser() {
        this.errorHandler = new DefaultErrorHandler();
        this.localizableSupport = new LocalizableSupport("org.apache.batik.parser.resources.Messages", AbstractParser.class.getClassLoader());
    }
    
    public int getCurrent() {
        return this.current;
    }
    
    public void setLocale(final Locale locale) {
        this.localizableSupport.setLocale(locale);
    }
    
    public Locale getLocale() {
        return this.localizableSupport.getLocale();
    }
    
    public String formatMessage(final String s, final Object[] array) throws MissingResourceException {
        return this.localizableSupport.formatMessage(s, array);
    }
    
    public void setErrorHandler(final ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
    
    public void parse(final Reader reader) throws ParseException {
        try {
            this.reader = new StreamNormalizingReader(reader);
            this.doParse();
        }
        catch (IOException ex) {
            this.errorHandler.error(new ParseException(this.createErrorMessage("io.exception", null), ex));
        }
    }
    
    public void parse(final InputStream inputStream, final String s) throws ParseException {
        try {
            this.reader = new StreamNormalizingReader(inputStream, s);
            this.doParse();
        }
        catch (IOException ex) {
            this.errorHandler.error(new ParseException(this.createErrorMessage("io.exception", null), ex));
        }
    }
    
    public void parse(final String s) throws ParseException {
        try {
            this.reader = new StringNormalizingReader(s);
            this.doParse();
        }
        catch (IOException ex) {
            this.errorHandler.error(new ParseException(this.createErrorMessage("io.exception", null), ex));
        }
    }
    
    protected abstract void doParse() throws ParseException, IOException;
    
    protected void reportError(final String s, final Object[] array) throws ParseException {
        this.errorHandler.error(new ParseException(this.createErrorMessage(s, array), this.reader.getLine(), this.reader.getColumn()));
    }
    
    protected void reportCharacterExpectedError(final char value, final int value2) {
        this.reportError("character.expected", new Object[] { new Character(value), new Integer(value2) });
    }
    
    protected void reportUnexpectedCharacterError(final int value) {
        this.reportError("character.unexpected", new Object[] { new Integer(value) });
    }
    
    protected String createErrorMessage(final String s, final Object[] array) {
        try {
            return this.formatMessage(s, array);
        }
        catch (MissingResourceException ex) {
            return s;
        }
    }
    
    protected String getBundleClassName() {
        return "org.apache.batik.parser.resources.Messages";
    }
    
    protected void skipSpaces() throws IOException {
        while (true) {
            switch (this.current) {
                default: {}
                case 9:
                case 10:
                case 13:
                case 32: {
                    this.current = this.reader.read();
                    continue;
                }
            }
        }
    }
    
    protected void skipCommaSpaces() throws IOException {
        while (true) {
            switch (this.current) {
                default: {
                    Label_0134: {
                        if (this.current == 44) {
                            while (true) {
                                switch (this.current = this.reader.read()) {
                                    default: {
                                        break Label_0134;
                                    }
                                    case 9:
                                    case 10:
                                    case 13:
                                    case 32: {
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
                case 9:
                case 10:
                case 13:
                case 32: {
                    this.current = this.reader.read();
                    continue;
                }
            }
        }
    }
}
