// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.gvt;

import java.awt.event.InputEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.KeyListener;

public interface Interactor extends KeyListener, MouseListener, MouseMotionListener
{
    boolean startInteraction(final InputEvent p0);
    
    boolean endInteraction();
}
