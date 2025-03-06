// 
// Decompiled by Procyon v0.5.36
// 

package org.apache.batik.swing.svg;

import javax.swing.Icon;
import org.apache.batik.util.gui.JErrorPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import java.awt.Component;

public class SVGUserAgentGUIAdapter extends SVGUserAgentAdapter
{
    public Component parentComponent;
    
    public SVGUserAgentGUIAdapter(final Component parentComponent) {
        this.parentComponent = parentComponent;
    }
    
    public void displayError(final String message) {
        final JDialog dialog = new JOptionPane(message, 0).createDialog(this.parentComponent, "ERROR");
        dialog.setModal(false);
        dialog.setVisible(true);
    }
    
    public void displayError(final Exception ex) {
        final JDialog dialog = new JErrorPane(ex, 0).createDialog(this.parentComponent, "ERROR");
        dialog.setModal(false);
        dialog.setVisible(true);
    }
    
    public void displayMessage(final String s) {
    }
    
    public void showAlert(final String str) {
        JOptionPane.showMessageDialog(this.parentComponent, "Script alert:\n" + str);
    }
    
    public String showPrompt(final String str) {
        return JOptionPane.showInputDialog(this.parentComponent, "Script prompt:\n" + str);
    }
    
    public String showPrompt(final String str, final String initialSelectionValue) {
        return (String)JOptionPane.showInputDialog(this.parentComponent, "Script prompt:\n" + str, null, -1, null, null, initialSelectionValue);
    }
    
    public boolean showConfirm(final String str) {
        return JOptionPane.showConfirmDialog(this.parentComponent, "Script confirm:\n" + str, "Confirm", 0) == 0;
    }
}
