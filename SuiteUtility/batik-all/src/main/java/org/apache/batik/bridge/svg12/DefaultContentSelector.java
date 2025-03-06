// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.bridge.svg12;

import org.w3c.dom.Node;
import java.util.ArrayList;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.apache.batik.dom.svg12.XBLOMContentElement;

public class DefaultContentSelector extends AbstractContentSelector
{
    protected SelectedNodes selectedContent;
    
    public DefaultContentSelector(final ContentManager contentManager, final XBLOMContentElement xblomContentElement, final Element element) {
        super(contentManager, xblomContentElement, element);
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
            for (Node e = DefaultContentSelector.this.boundElement.getFirstChild(); e != null; e = e.getNextSibling()) {
                if (!DefaultContentSelector.this.isSelected(e)) {
                    this.nodes.add(e);
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
