// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.util.gui.xmleditor;

import javax.swing.text.Element;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import java.awt.Color;
import javax.swing.text.EditorKit;
import javax.swing.undo.UndoManager;
import javax.swing.JEditorPane;

public class XMLTextEditor extends JEditorPane
{
    protected UndoManager undoManager;
    
    public XMLTextEditor() {
        this.setEditorKitForContentType("text/xml", new XMLEditorKit());
        this.setContentType("text/xml");
        this.setBackground(Color.white);
        this.undoManager = new UndoManager();
        this.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(final UndoableEditEvent undoableEditEvent) {
                XMLTextEditor.this.undoManager.addEdit(undoableEditEvent.getEdit());
            }
        });
    }
    
    public void setText(final String text) {
        super.setText(text);
        this.undoManager.discardAllEdits();
    }
    
    public void undo() {
        try {
            this.undoManager.undo();
        }
        catch (CannotUndoException ex) {}
    }
    
    public void redo() {
        try {
            this.undoManager.redo();
        }
        catch (CannotRedoException ex) {}
    }
    
    public void gotoLine(final int n) {
        final Element element = this.getDocument().getDefaultRootElement().getElement(n);
        if (element == null) {
            return;
        }
        this.setCaretPosition(element.getStartOffset());
    }
}
