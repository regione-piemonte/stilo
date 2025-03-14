/**
* ===========================================
* Java Pdf Extraction Decoding Access Library
* ===========================================
*
* Project Info:  http://www.jpedal.org
* (C) Copyright 1997-2008, IDRsolutions and Contributors.
*
* 	This file is part of JPedal
*
    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


*
* ---------------
* Details.java
* ---------------
*/

package it.eng.hybrid.module.jpedal.acroforms;

import javax.swing.table.TableModel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigInteger;

public class Details extends javax.swing.JPanel {

    private String publicKey;

    public void setValues(int version, String hashAlgorithm, String subject, String issuer, BigInteger serialNumber, String notBefore,
                          String notAfter, String publicKeyDescription, String publicKey, String x509Data, String sha1Digest, String md5Digest) {
        TableModel model = jTable1.getModel();

        this.publicKey = publicKey;

        model.setValueAt(String.valueOf(version), 0, 1);
        model.setValueAt(hashAlgorithm, 1, 1);
        model.setValueAt(subject, 2, 1);
        model.setValueAt(issuer, 3, 1);
        model.setValueAt(Long.toHexString(serialNumber.longValue()).toUpperCase(), 4, 1);
        model.setValueAt(notBefore, 5, 1);
        model.setValueAt(notAfter, 6, 1);
        model.setValueAt(publicKeyDescription, 7, 1);
        model.setValueAt(x509Data, 8, 1);
        model.setValueAt(sha1Digest, 9, 1);
        model.setValueAt(md5Digest, 10, 1);
    }

    /**
     * Creates new form Details
     */
    public Details() {
        initComponents();

        //jTable1.getModel().setValueAt("Test String", 0, 1);

        jTable1.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow == 7) { // public key row
                    descriptionBox.setText(publicKey);
                } else {
                    descriptionBox.setText((String) jTable1.getModel().getValueAt(selectedRow, 1));
                }

                descriptionBox.setCaretPosition(0);
            }

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionBox = new javax.swing.JTextArea();

        setLayout(null);

        jLabel1.setText("Certificate data:");
        add(jLabel1);
        jLabel1.setBounds(10, 10, 220, 14);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {"Version", null},
                        {"Signature algorithum", null},
                        {"Subject", null},
                        {"Issuer", null},
                        {"Serial number", null},
                        {"Validity starts", null},
                        {"Validity ends", null},
                        {"Public key", null},
                        {"X.509 data", null},
                        {"SHA-1 digest", null},
                        {"MD5 digest", null}
                },
                new String[]{
                        "Name", "Value"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1);
        jScrollPane1.setBounds(10, 30, 380, 150);

        descriptionBox.setColumns(20);
        descriptionBox.setEditable(false);
        descriptionBox.setLineWrap(true);
        descriptionBox.setRows(5);
        descriptionBox.setWrapStyleWord(true);
        jScrollPane2.setViewportView(descriptionBox);

        add(jScrollPane2);
        jScrollPane2.setBounds(10, 190, 380, 120);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea descriptionBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables


}
