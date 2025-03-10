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
* SetSecurity.java
* ---------------
*/
package it.eng.hybrid.module.jpedal.io;

import java.security.Key;
import java.security.Security;
import java.security.cert.Certificate;

import org.apache.log4j.Logger;

public class SetSecurity {

	public final static Logger logger = Logger.getLogger(SetSecurity.class);
	
    private static String altSP=null;
	
	public static void init(){

        //allow user to over-ride
        altSP=System.getProperty("org.jpedal.securityprovider");

        if(altSP==null)
            altSP="org.bouncycastle.jce.provider.BouncyCastleProvider";

        try {

            Class c = Class.forName(altSP);
            java.security.Provider provider = (java.security.Provider) c.newInstance();

            Security.addProvider(provider);
        } catch (Exception e) {

        	logger.info("Unable to run custom security provider " + altSP+" Exception " + e);
        	
            throw new RuntimeException("This PDF file is encrypted and JPedal needs an additional library to \n" +
                    "decode on the classpath (we recommend bouncycastle library).\n" +
                    "There is additional explanation at http://www.jpedal.org/support_AddJars.php"+ '\n');


        }
    }

    /**
     * cycle through all possible values to find match (only tested with Bouncy castle)
     * @param certificate
     * @param key
     */
    public static byte[] extractCertificateData(byte[][] recipients, Certificate certificate, Key key) {

        //break if not bc
        if(altSP==null || !altSP.equals("org.bouncycastle.jce.provider.BouncyCastleProvider"))
            throw new RuntimeException("only Bouncy castle currently supported with certificates");

        @SuppressWarnings("UnusedAssignment")
        byte[] envelopedData=null;
        
        //<start-adobe><start-wrap>
        //Adobe do not use Bouncy castle and only added for that        
        envelopedData=CertificateReader.readCertificate(recipients, certificate, key);
        
        //<end-wrap><end-adobe>

        return envelopedData;
    }
}
