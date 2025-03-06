// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.dom;

import org.w3c.dom.stylesheets.StyleSheet;
import org.apache.batik.dom.util.HashTable;
import org.w3c.dom.Node;

public interface StyleSheetFactory
{
    StyleSheet createStyleSheet(final Node p0, final HashTable p1);
}
