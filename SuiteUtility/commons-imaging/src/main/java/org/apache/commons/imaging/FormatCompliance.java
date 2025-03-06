// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging;

import java.io.IOException;
import java.util.logging.Level;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FormatCompliance
{
    private static final Logger LOGGER;
    private final boolean failOnError;
    private final String description;
    private final List<String> comments;
    
    public FormatCompliance(final String description) {
        this.comments = new ArrayList<String>();
        this.description = description;
        this.failOnError = false;
    }
    
    public FormatCompliance(final String description, final boolean failOnError) {
        this.comments = new ArrayList<String>();
        this.description = description;
        this.failOnError = failOnError;
    }
    
    public static FormatCompliance getDefault() {
        return new FormatCompliance("ignore", false);
    }
    
    public void addComment(final String comment) throws ImageReadException {
        this.comments.add(comment);
        if (this.failOnError) {
            throw new ImageReadException(comment);
        }
    }
    
    public void addComment(final String comment, final int value) throws ImageReadException {
        this.addComment(comment + ": " + this.getValueDescription(value));
    }
    
    @Override
    public String toString() {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw);
        this.dump(pw);
        return sw.getBuffer().toString();
    }
    
    public void dump() {
        try (final StringWriter sw = new StringWriter();
             final PrintWriter pw = new PrintWriter(sw)) {
            this.dump(pw);
            pw.flush();
            sw.flush();
            FormatCompliance.LOGGER.fine(sw.toString());
        }
        catch (IOException e) {
            FormatCompliance.LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }
    
    public void dump(final PrintWriter pw) {
        pw.println("Format Compliance: " + this.description);
        if (this.comments.isEmpty()) {
            pw.println("\tNo comments.");
        }
        else {
            for (int i = 0; i < this.comments.size(); ++i) {
                pw.println("\t" + (i + 1) + ": " + this.comments.get(i));
            }
        }
        pw.println("");
        pw.flush();
    }
    
    private String getValueDescription(final int value) {
        return value + " (" + Integer.toHexString(value) + ")";
    }
    
    public boolean compareBytes(final String name, final byte[] expected, final byte[] actual) throws ImageReadException {
        if (expected.length != actual.length) {
            this.addComment(name + ": Unexpected length: (expected: " + expected.length + ", actual: " + actual.length + ")");
            return false;
        }
        for (int i = 0; i < expected.length; ++i) {
            if (expected[i] != actual[i]) {
                this.addComment(name + ": Unexpected value: (expected: " + this.getValueDescription(expected[i]) + ", actual: " + this.getValueDescription(actual[i]) + ")");
                return false;
            }
        }
        return true;
    }
    
    public boolean checkBounds(final String name, final int min, final int max, final int actual) throws ImageReadException {
        if (actual < min || actual > max) {
            this.addComment(name + ": bounds check: " + min + " <= " + actual + " <= " + max + ": false");
            return false;
        }
        return true;
    }
    
    public boolean compare(final String name, final int valid, final int actual) throws ImageReadException {
        return this.compare(name, new int[] { valid }, actual);
    }
    
    public boolean compare(final String name, final int[] valid, final int actual) throws ImageReadException {
        for (final int element : valid) {
            if (actual == element) {
                return true;
            }
        }
        final StringBuilder result = new StringBuilder(43);
        result.append(name);
        result.append(": Unexpected value: (valid: ");
        if (valid.length > 1) {
            result.append('{');
        }
        for (int i = 0; i < valid.length; ++i) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(this.getValueDescription(valid[i]));
        }
        if (valid.length > 1) {
            result.append('}');
        }
        result.append(", actual: " + this.getValueDescription(actual) + ")");
        this.addComment(result.toString());
        return false;
    }
    
    static {
        LOGGER = Logger.getLogger(FormatCompliance.class.getName());
    }
}
