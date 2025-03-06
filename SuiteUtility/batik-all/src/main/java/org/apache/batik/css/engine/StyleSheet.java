// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.css.engine;

import org.w3c.css.sac.SACMediaList;

public class StyleSheet
{
    protected Rule[] rules;
    protected int size;
    protected StyleSheet parent;
    protected boolean alternate;
    protected SACMediaList media;
    protected String title;
    
    public StyleSheet() {
        this.rules = new Rule[16];
    }
    
    public void setMedia(final SACMediaList media) {
        this.media = media;
    }
    
    public SACMediaList getMedia() {
        return this.media;
    }
    
    public StyleSheet getParent() {
        return this.parent;
    }
    
    public void setParent(final StyleSheet parent) {
        this.parent = parent;
    }
    
    public void setAlternate(final boolean alternate) {
        this.alternate = alternate;
    }
    
    public boolean isAlternate() {
        return this.alternate;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Rule getRule(final int n) {
        return this.rules[n];
    }
    
    public void clear() {
        this.size = 0;
        this.rules = new Rule[10];
    }
    
    public void append(final Rule rule) {
        if (this.size == this.rules.length) {
            final Rule[] rules = new Rule[this.size * 2];
            System.arraycopy(this.rules, 0, rules, 0, this.size);
            this.rules = rules;
        }
        this.rules[this.size++] = rule;
    }
    
    public String toString(final CSSEngine cssEngine) {
        final StringBuffer sb = new StringBuffer(this.size * 8);
        for (int i = 0; i < this.size; ++i) {
            sb.append(this.rules[i].toString(cssEngine));
        }
        return sb.toString();
    }
}
