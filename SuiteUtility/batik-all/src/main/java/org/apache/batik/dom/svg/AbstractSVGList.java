// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom.svg;

import java.util.Iterator;
import java.util.ArrayList;
import org.w3c.dom.DOMException;
import org.w3c.dom.svg.SVGException;
import org.apache.batik.parser.ParseException;
import java.util.List;

public abstract class AbstractSVGList
{
    protected boolean valid;
    protected List itemList;
    
    protected abstract String getItemSeparator();
    
    protected abstract SVGItem createSVGItem(final Object p0);
    
    protected abstract void doParse(final String p0, final ListHandler p1) throws ParseException;
    
    protected abstract void checkItemType(final Object p0) throws SVGException;
    
    protected abstract String getValueAsString();
    
    protected abstract void setAttributeValue(final String p0);
    
    protected abstract DOMException createDOMException(final short p0, final String p1, final Object[] p2);
    
    public int getNumberOfItems() {
        this.revalidate();
        if (this.itemList != null) {
            return this.itemList.size();
        }
        return 0;
    }
    
    public void clear() throws DOMException {
        this.revalidate();
        if (this.itemList != null) {
            this.clear(this.itemList);
            this.resetAttribute();
        }
    }
    
    protected SVGItem initializeImpl(final Object o) throws DOMException, SVGException {
        this.checkItemType(o);
        if (this.itemList == null) {
            this.itemList = new ArrayList(1);
        }
        else {
            this.clear(this.itemList);
        }
        final SVGItem removeIfNeeded = this.removeIfNeeded(o);
        this.itemList.add(removeIfNeeded);
        removeIfNeeded.setParent(this);
        this.resetAttribute();
        return removeIfNeeded;
    }
    
    protected SVGItem getItemImpl(final int value) throws DOMException {
        this.revalidate();
        if (value < 0 || this.itemList == null || value >= this.itemList.size()) {
            throw this.createDOMException((short)1, "index.out.of.bounds", new Object[] { new Integer(value) });
        }
        return this.itemList.get(value);
    }
    
    protected SVGItem insertItemBeforeImpl(final Object o, int size) throws DOMException, SVGException {
        this.checkItemType(o);
        this.revalidate();
        if (size < 0) {
            throw this.createDOMException((short)1, "index.out.of.bounds", new Object[] { new Integer(size) });
        }
        if (size > this.itemList.size()) {
            size = this.itemList.size();
        }
        final SVGItem removeIfNeeded = this.removeIfNeeded(o);
        this.itemList.add(size, removeIfNeeded);
        removeIfNeeded.setParent(this);
        this.resetAttribute();
        return removeIfNeeded;
    }
    
    protected SVGItem replaceItemImpl(final Object o, final int value) throws DOMException, SVGException {
        this.checkItemType(o);
        this.revalidate();
        if (value < 0 || value >= this.itemList.size()) {
            throw this.createDOMException((short)1, "index.out.of.bounds", new Object[] { new Integer(value) });
        }
        final SVGItem removeIfNeeded = this.removeIfNeeded(o);
        this.itemList.set(value, removeIfNeeded);
        removeIfNeeded.setParent(this);
        this.resetAttribute();
        return removeIfNeeded;
    }
    
    protected SVGItem removeItemImpl(final int value) throws DOMException {
        this.revalidate();
        if (value < 0 || value >= this.itemList.size()) {
            throw this.createDOMException((short)1, "index.out.of.bounds", new Object[] { new Integer(value) });
        }
        final SVGItem svgItem = this.itemList.remove(value);
        svgItem.setParent(null);
        this.resetAttribute();
        return svgItem;
    }
    
    protected SVGItem appendItemImpl(final Object o) throws DOMException, SVGException {
        this.checkItemType(o);
        this.revalidate();
        final SVGItem removeIfNeeded = this.removeIfNeeded(o);
        this.itemList.add(removeIfNeeded);
        removeIfNeeded.setParent(this);
        if (this.itemList.size() <= 1) {
            this.resetAttribute();
        }
        else {
            this.resetAttribute(removeIfNeeded);
        }
        return removeIfNeeded;
    }
    
    protected SVGItem removeIfNeeded(final Object o) {
        SVGItem svgItem;
        if (o instanceof SVGItem) {
            svgItem = (SVGItem)o;
            if (svgItem.getParent() != null) {
                svgItem.getParent().removeItem(svgItem);
            }
        }
        else {
            svgItem = this.createSVGItem(o);
        }
        return svgItem;
    }
    
    protected void revalidate() {
        if (this.valid) {
            return;
        }
        try {
            final ListBuilder listBuilder = new ListBuilder();
            this.doParse(this.getValueAsString(), listBuilder);
            final List list = listBuilder.getList();
            if (list != null) {
                this.clear(this.itemList);
            }
            this.itemList = list;
        }
        catch (ParseException ex) {
            this.itemList = null;
        }
        this.valid = true;
    }
    
    protected void setValueAsString(final List list) throws DOMException {
        String string = null;
        final Iterator<SVGItem> iterator = list.iterator();
        if (iterator.hasNext()) {
            final SVGItem svgItem = iterator.next();
            final StringBuffer sb = new StringBuffer(list.size() * 8);
            sb.append(svgItem.getValueAsString());
            while (iterator.hasNext()) {
                final SVGItem svgItem2 = iterator.next();
                sb.append(this.getItemSeparator());
                sb.append(svgItem2.getValueAsString());
            }
            string = sb.toString();
        }
        this.setAttributeValue(string);
        this.valid = true;
    }
    
    public void itemChanged() {
        this.resetAttribute();
    }
    
    protected void resetAttribute() {
        this.setValueAsString(this.itemList);
    }
    
    protected void resetAttribute(final SVGItem svgItem) {
        this.setAttributeValue(this.getValueAsString() + this.getItemSeparator() + svgItem.getValueAsString());
        this.valid = true;
    }
    
    public void invalidate() {
        this.valid = false;
    }
    
    protected void removeItem(final SVGItem svgItem) {
        if (this.itemList.contains(svgItem)) {
            this.itemList.remove(svgItem);
            svgItem.setParent(null);
            this.resetAttribute();
        }
    }
    
    protected void clear(final List list) {
        if (list == null) {
            return;
        }
        final Iterator<SVGItem> iterator = list.iterator();
        while (iterator.hasNext()) {
            iterator.next().setParent(null);
        }
        list.clear();
    }
    
    protected class ListBuilder implements ListHandler
    {
        protected List list;
        
        public List getList() {
            return this.list;
        }
        
        public void startList() {
            this.list = new ArrayList();
        }
        
        public void item(final SVGItem svgItem) {
            svgItem.setParent(AbstractSVGList.this);
            this.list.add(svgItem);
        }
        
        public void endList() {
        }
    }
}
