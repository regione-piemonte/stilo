// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.gvt.text;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.text.AttributedString;
import java.text.StringCharacterIterator;
import java.text.AttributedCharacterIterator;
import java.text.CharacterIterator;
import java.util.ArrayList;
import java.util.Set;

public class GVTACIImpl implements GVTAttributedCharacterIterator
{
    private String simpleString;
    private Set allAttributes;
    private ArrayList mapList;
    private static int START_RUN;
    private static int END_RUN;
    private static int MID_RUN;
    private static int SINGLETON;
    private int[] charInRun;
    private CharacterIterator iter;
    private int currentIndex;
    
    public GVTACIImpl() {
        this.iter = null;
        this.currentIndex = -1;
        this.simpleString = "";
        this.buildAttributeTables();
    }
    
    public GVTACIImpl(final AttributedCharacterIterator attributedCharacterIterator) {
        this.iter = null;
        this.currentIndex = -1;
        this.buildAttributeTables(attributedCharacterIterator);
    }
    
    public void setString(final String simpleString) {
        this.simpleString = simpleString;
        this.iter = new StringCharacterIterator(this.simpleString);
        this.buildAttributeTables();
    }
    
    public void setString(final AttributedString attributedString) {
        this.iter = attributedString.getIterator();
        this.buildAttributeTables((AttributedCharacterIterator)this.iter);
    }
    
    public void setAttributeArray(final TextAttribute textAttribute, final Object[] array, int max, int min) {
        max = Math.max(max, 0);
        min = Math.min(min, this.simpleString.length());
        if (this.charInRun[max] == GVTACIImpl.END_RUN) {
            if (this.charInRun[max - 1] == GVTACIImpl.MID_RUN) {
                this.charInRun[max - 1] = GVTACIImpl.END_RUN;
            }
            else {
                this.charInRun[max - 1] = GVTACIImpl.SINGLETON;
            }
        }
        if (this.charInRun[min + 1] == GVTACIImpl.END_RUN) {
            this.charInRun[min + 1] = GVTACIImpl.SINGLETON;
        }
        else if (this.charInRun[min + 1] == GVTACIImpl.MID_RUN) {
            this.charInRun[min + 1] = GVTACIImpl.START_RUN;
        }
        for (int i = max; i <= min; ++i) {
            this.charInRun[i] = GVTACIImpl.SINGLETON;
            ((Map<TextAttribute, Object>)this.mapList.get(i)).put(textAttribute, array[Math.min(i, array.length - 1)]);
        }
    }
    
    public Set getAllAttributeKeys() {
        return this.allAttributes;
    }
    
    public Object getAttribute(final AttributedCharacterIterator.Attribute attribute) {
        return this.getAttributes().get(attribute);
    }
    
    public Map getAttributes() {
        return this.mapList.get(this.currentIndex);
    }
    
    public int getRunLimit() {
        int currentIndex = this.currentIndex;
        do {
            ++currentIndex;
        } while (this.charInRun[currentIndex] == GVTACIImpl.MID_RUN);
        return currentIndex;
    }
    
    public int getRunLimit(final AttributedCharacterIterator.Attribute attribute) {
        int currentIndex = this.currentIndex;
        final Object value = this.getAttributes().get(attribute);
        if (value == null) {
            do {
                ++currentIndex;
            } while (((Map)this.mapList.get(currentIndex)).get(attribute) == null);
        }
        else {
            do {
                ++currentIndex;
            } while (value.equals(this.mapList.get(currentIndex).get(attribute)));
        }
        return currentIndex;
    }
    
    public int getRunLimit(final Set set) {
        int currentIndex = this.currentIndex;
        do {
            ++currentIndex;
        } while (set.equals(this.mapList.get(currentIndex)));
        return currentIndex;
    }
    
    public int getRunStart() {
        int currentIndex;
        for (currentIndex = this.currentIndex; this.charInRun[currentIndex] == GVTACIImpl.MID_RUN; --currentIndex) {}
        return currentIndex;
    }
    
    public int getRunStart(final AttributedCharacterIterator.Attribute attribute) {
        int n = this.currentIndex - 1;
        final Object value = this.getAttributes().get(attribute);
        try {
            if (value == null) {
                while (((Map)this.mapList.get(n - 1)).get(attribute) == null) {
                    --n;
                }
            }
            else {
                while (value.equals(this.mapList.get(n - 1).get(attribute))) {
                    --n;
                }
            }
        }
        catch (IndexOutOfBoundsException ex) {}
        return n;
    }
    
    public int getRunStart(final Set set) {
        int currentIndex = this.currentIndex;
        try {
            while (set.equals(this.mapList.get(currentIndex - 1))) {
                --currentIndex;
            }
        }
        catch (IndexOutOfBoundsException ex) {}
        return currentIndex;
    }
    
    public Object clone() {
        return new GVTACIImpl(this);
    }
    
    public char current() {
        return this.iter.current();
    }
    
    public char first() {
        return this.iter.first();
    }
    
    public int getBeginIndex() {
        return this.iter.getBeginIndex();
    }
    
    public int getEndIndex() {
        return this.iter.getEndIndex();
    }
    
    public int getIndex() {
        return this.iter.getIndex();
    }
    
    public char last() {
        return this.iter.last();
    }
    
    public char next() {
        return this.iter.next();
    }
    
    public char previous() {
        return this.iter.previous();
    }
    
    public char setIndex(final int index) {
        return this.iter.setIndex(index);
    }
    
    private void buildAttributeTables() {
        this.allAttributes = new HashSet();
        this.mapList = new ArrayList(this.simpleString.length());
        this.charInRun = new int[this.simpleString.length()];
        for (int i = 0; i < this.charInRun.length; ++i) {
            this.charInRun[i] = GVTACIImpl.SINGLETON;
            this.mapList.set(i, new HashMap());
        }
    }
    
    private void buildAttributeTables(final AttributedCharacterIterator attributedCharacterIterator) {
        this.allAttributes = attributedCharacterIterator.getAllAttributeKeys();
        final int initialCapacity = attributedCharacterIterator.getEndIndex() - attributedCharacterIterator.getBeginIndex();
        this.mapList = new ArrayList(initialCapacity);
        this.charInRun = new int[initialCapacity];
        char c = attributedCharacterIterator.first();
        final char[] value = new char[initialCapacity];
        for (int i = 0; i < initialCapacity; ++i) {
            value[i] = c;
            this.charInRun[i] = GVTACIImpl.SINGLETON;
            this.mapList.set(i, new HashMap(attributedCharacterIterator.getAttributes()));
            c = attributedCharacterIterator.next();
        }
        this.simpleString = new String(value);
    }
    
    static {
        GVTACIImpl.START_RUN = 2;
        GVTACIImpl.END_RUN = 3;
        GVTACIImpl.MID_RUN = 1;
        GVTACIImpl.SINGLETON = 0;
    }
    
    public class TransformAttributeFilter implements AttributeFilter
    {
        public AttributedCharacterIterator mutateAttributes(final AttributedCharacterIterator attributedCharacterIterator) {
            return attributedCharacterIterator;
        }
    }
}
