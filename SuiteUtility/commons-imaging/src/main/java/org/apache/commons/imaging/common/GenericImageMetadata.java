// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.commons.imaging.common;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public class GenericImageMetadata implements ImageMetadata
{
    private static final String NEWLINE;
    private final List<ImageMetadataItem> items;
    
    public GenericImageMetadata() {
        this.items = new ArrayList<ImageMetadataItem>();
    }
    
    public void add(final String keyword, final String text) {
        this.add(new GenericImageMetadataItem(keyword, text));
    }
    
    public void add(final ImageMetadataItem item) {
        this.items.add(item);
    }
    
    @Override
    public List<? extends ImageMetadataItem> getItems() {
        return new ArrayList<ImageMetadataItem>(this.items);
    }
    
    @Override
    public String toString() {
        return this.toString(null);
    }
    
    @Override
    public String toString(String prefix) {
        if (null == prefix) {
            prefix = "";
        }
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < this.items.size(); ++i) {
            if (i > 0) {
                result.append(GenericImageMetadata.NEWLINE);
            }
            final ImageMetadataItem item = this.items.get(i);
            result.append(item.toString(prefix + "\t"));
        }
        return result.toString();
    }
    
    static {
        NEWLINE = System.getProperty("line.separator");
    }
    
    public static class GenericImageMetadataItem implements ImageMetadataItem
    {
        private final String keyword;
        private final String text;
        
        public GenericImageMetadataItem(final String keyword, final String text) {
            this.keyword = keyword;
            this.text = text;
        }
        
        public String getKeyword() {
            return this.keyword;
        }
        
        public String getText() {
            return this.text;
        }
        
        @Override
        public String toString() {
            return this.toString(null);
        }
        
        @Override
        public String toString(final String prefix) {
            final String result = this.keyword + ": " + this.text;
            if (null != prefix) {
                return prefix + result;
            }
            return result;
        }
    }
}
