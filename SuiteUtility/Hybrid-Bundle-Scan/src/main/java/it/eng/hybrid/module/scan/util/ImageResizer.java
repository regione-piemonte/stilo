package it.eng.hybrid.module.scan.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

public class ImageResizer {

	public final static Logger logger = Logger.getLogger(ImageResizer.class);

	private String filename;
	private int width;
	private int height;

	public ImageResizer(String pStrImageToShow, int width, int height) {
		filename = pStrImageToShow;
		this.width = width;
		this.height = height;
	}

	public Image getImageResized() throws IOException {
		
		logger.info("file da ridimensionare : " + filename);
		BufferedImage img = null;
		img = ImageIO.read(new File(filename));
		return img;
		
//		FileSeekableStream stream = new FileSeekableStream(filename);
//		TIFFDecodeParam decodeParam = new TIFFDecodeParam();
//		decodeParam.setDecodePaletteAsShorts(true);
//		ParameterBlock params = new ParameterBlock();
//		params.add(stream);
//		RenderedOp image1 = JAI.create("tiff", params);
//		BufferedImage img = image1.getAsBufferedImage();
//		float widthImage = img.getWidth();
//		float heightImage = img.getHeight();
//		float percW = widthImage / width;
//		float percH = heightImage / height;
//		float finalW = 0;
//		float finalH = 0;
//		if (percW < percH) {
//			finalW = width;
//			finalH = heightImage / percW;
//		} else {
//			finalH = height;
//			finalW = widthImage / percH;
//		}
//
//		image1.dispose();
//		image1 = null;
//		stream.close();
//
//		Image scaledImage = img.getScaledInstance(new Float(finalW).intValue(), new Float(finalH).intValue(), Image.SCALE_AREA_AVERAGING);
//		img.flush();
//		img = null;
//		return scaledImage;
	}

}
