/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2007, IDRsolutions and Contributors.
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

  * FileAccess.java
  * ---------------
  * (C) Copyright 2007, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package org.jpedal;

import org.jpedal.constants.PDFflags;
import org.jpedal.io.DecryptionFactory;
import org.jpedal.io.PdfFileReader;
import org.jpedal.io.PdfObjectReader;
import org.jpedal.io.PdfReader;

import java.security.PrivateKey;
import java.security.cert.Certificate;

public class FileAccess {

    /**user can open encrypted file with certificate*/
    Certificate certificate;

    /**used for opening encrypted file*/
    PrivateKey key;

    public boolean isFileViewable(PdfObjectReader currentPdfFile) {
        if (currentPdfFile != null){
            PdfFileReader objectReader=currentPdfFile.getObjectReader();

            DecryptionFactory decryption=objectReader.getDecryptionObject();
            return decryption==null || decryption.getBooleanValue(PDFflags.IS_FILE_VIEWABLE) || certificate!=null;
        }else
            return false;
    }

    public boolean isPasswordSupplied(PdfObjectReader currentPdfFile) {
        //allow through if user has verified password or set certificate
        if (currentPdfFile != null){
            PdfFileReader objectReader=currentPdfFile.getObjectReader();

            DecryptionFactory decryption=objectReader.getDecryptionObject();
            return decryption!=null && (decryption.getBooleanValue(PDFflags.IS_PASSWORD_SUPPLIED) || certificate!=null);
        }else
            return false;
    }

    public void setUserEncryption(Certificate certificate, PrivateKey key) {
        
        this.certificate=certificate;
        this.key=key;
    }

    public PdfObjectReader getNewReader() {

        PdfObjectReader currentPdfFile;

        if(certificate!=null){
            currentPdfFile = new PdfReader(certificate, key);
        }else
            currentPdfFile = new PdfReader();

        return currentPdfFile;
    }
}
