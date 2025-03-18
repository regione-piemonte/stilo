/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.apache.commons.io.FileUtils;

public final class MyBase64Adapter extends XmlAdapter<String, File> {
    public File unmarshal(String s) throws Exception{
        if (s == null)
            return null;
         File f = new File("temp");
        byte[] content= org.apache.commons.codec.binary.Base64.decodeBase64(s.getBytes());
        FileUtils.writeByteArrayToFile(f, content);
        return f;
    }

    public String marshal(File bytes)throws Exception {
        if (bytes == null)
            return null;
        byte[] contents=FileUtils.readFileToByteArray(bytes);
        return org.apache.commons.codec.binary.Base64.encodeBase64String(contents);
       
    }
}