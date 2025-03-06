/**
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.jpedal.org
 *
 * (C) Copyright 2012, IDRsolutions and Contributors.
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

  * DefaultIO.java
  * ---------------
  * (C) Copyright 2012, by IDRsolutions and Contributors.
  *
  *
  * --------------------------
 */
package it.eng.hybrid.module.jpedal.render.output;

import it.eng.hybrid.module.jpedal.io.ObjectStore;
import it.eng.hybrid.module.jpedal.ui.JPedalApplication;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.*;

public class DefaultIO implements CustomIO {

	public final static Logger logger = Logger.getLogger(DefaultIO.class);
	
    private BufferedWriter output =null;

    public void writeFont(String path, byte[] rawFontData) {

        try {

            BufferedOutputStream fontOutput = new BufferedOutputStream(new FileOutputStream(path));
            fontOutput.write(rawFontData);
            fontOutput.flush();
            fontOutput.close();

        } catch (Exception e) {
            //tell user and log
            logger.info("Exception: "+e.getMessage());
        }
    }

    public void writeJS(String rootDir, InputStream url) throws IOException {

        //make sure js Dir exists
        String cssPath = rootDir  + "/js";
        File cssDir = new File(cssPath);
        if (!cssDir.exists()) {
            cssDir.mkdirs();
        }

        BufferedInputStream stylesheet = new BufferedInputStream(url);

        BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(rootDir+"/js/aform.js"));
        ObjectStore.copy(stylesheet, bos);
        bos.flush();
        bos.close();

        stylesheet.close();
    }

    public void writeCSS(String rootDir, String fileName, StringBuilder css) {

        //make sure css Dir exists
        String cssPath = rootDir + fileName + '/';
        File cssDir = new File(cssPath);
        if (!cssDir.exists()) {
            cssDir.mkdirs();
        }

        try {
            //PrintWriter CSSOutput = new PrintWriter(new FileOutputStream(cssPath + "styles.css"));
            BufferedOutputStream CSSOutput = new BufferedOutputStream(new FileOutputStream(cssPath + "styles.css"));

            //css header

            CSSOutput.write(css.toString().getBytes());

            CSSOutput.flush();
            CSSOutput.close();

        } catch (Exception e) {
            //tell user and log
            logger.info("Exception: "+e.getMessage());
        }
    }

    public boolean isOutputOpen() {
        return output!=null;
    }

    public void setupOutput(String path, boolean append, String encodingUsed) throws FileNotFoundException, UnsupportedEncodingException {

        output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path,append), encodingUsed));

    }

    public void flush() {

        try{
            output.flush();
            output.close();
            output =null;
        }catch(Exception e){
            //tell user and log
            logger.info("Exception: "+e.getMessage());
        }
    }

    public void writeString(String str) {

        try {
            output.write(str);
            output.write('\n');
            output.flush();
        } catch (Exception e) {
            //tell user and log
            logger.info("Exception: "+e.getMessage());
        }
    }

    public String writeImage(String rootDir,String path, BufferedImage image) {

        String file=path+getImageTypeUsed();

        try{
            ImageIO.write(image, "PNG", new File(rootDir+file));
        }catch(Exception e){
            //tell user and log
            logger.info("Exception: "+e.getMessage());
        }

        return file;
    }

    public String getImageTypeUsed(){
        return ".png";
    }
}
