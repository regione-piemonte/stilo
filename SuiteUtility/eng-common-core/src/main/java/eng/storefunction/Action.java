// 
// Decompiled by Procyon v0.5.36
// 

package eng.storefunction;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public abstract class Action
{
    private final List commands;
    private boolean isMultiple;
    private int multipleInput;
    private int multipleOutputNames;
    private int multipleOutputValues;
    
    public Action() {
        this.isMultiple = false;
        this.commands = new ArrayList();
    }
    
    public abstract String normalizeCommand(final String p0);
    
    public void addCommand(final int index, final String command) {
        this.commands.add(index, this.normalizeCommand(command));
    }
    
    public void addCommand(final String command) {
        this.commands.add(this.normalizeCommand(command));
    }
    
    public void addCommandAll(final Collection commands) {
        final Iterator it = commands.iterator();
        while (it.hasNext()) {
            this.addCommand(it.next());
        }
    }
    
    public void addCommandAll(final String[] commands) {
        this.addCommandAll(Arrays.asList(commands));
    }
    
    public String getFirstCommand() {
        if (this.commands.size() > 0) {
            return this.commands.get(0);
        }
        return null;
    }
    
    public String getCommand(final int index) {
        if (index < this.commands.size()) {
            return this.commands.get(index);
        }
        return "";
    }
    
    public Iterator getCommands() {
        return this.commands.iterator();
    }
    
    public int getMultipleInput() {
        return this.multipleInput;
    }
    
    public int getMultipleOutputNames() {
        return this.multipleOutputNames;
    }
    
    public int getMultipleOutputValues() {
        return this.multipleOutputValues;
    }
    
    public boolean isMultiple() {
        return this.isMultiple;
    }
    
    public Action setMultiple(final int multipleInput, final int multipleOutputNames, final int multipleOutputValues) {
        this.multipleInput = multipleInput;
        this.multipleOutputNames = multipleOutputNames;
        this.multipleOutputValues = multipleOutputValues;
        this.isMultiple = true;
        return this;
    }
    
    public void substituteCommand(final int index, final String command) {
        try {
            this.commands.remove(index);
        }
        catch (UnsupportedOperationException ex) {}
        catch (IndexOutOfBoundsException ex2) {}
        this.addCommand(index, command);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("(Action:\n");
        final Iterator it = this.commands.iterator();
        while (it.hasNext()) {
            sb.append(it.next() + ",\n");
        }
        sb.append(")");
        return sb.toString();
    }
}
