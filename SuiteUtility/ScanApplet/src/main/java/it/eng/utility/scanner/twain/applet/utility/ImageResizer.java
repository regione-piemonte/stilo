package it.eng.utility.scanner.twain.applet.utility;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.ParameterBlock;
import java.io.IOException;
import java.util.ArrayList;

import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;

import com.sun.media.jai.codec.FileSeekableStream;
import com.sun.media.jai.codec.TIFFDecodeParam;

public class ImageResizer {

	private ArrayList<String> filename;
	private int width;
	private int height;

	public ImageResizer(ArrayList<String> pStrImageToShow, int width, int height) {
		filename = pStrImageToShow;
		this.width = width;
		this.height = height;
	}

	public Image getImageResized() throws IOException {
		// Per il bordo tra una pagina e l'altra
		int pageOffset = 10;
		BufferedImage img_final = null;

		// Per ogni immagine che è stata passata come input
		for (int indexImg = 0; indexImg < filename.size(); indexImg++) {
			FileSeekableStream stream = new FileSeekableStream(filename.get(indexImg));
			TIFFDecodeParam decodeParam = new TIFFDecodeParam();
			decodeParam.setDecodePaletteAsShorts(true);
			ParameterBlock params = new ParameterBlock();
			params.add(stream);

			RenderedOp image1 = JAI.create("tiff", params);

			if (img_final != null) {
				// Allora non è la prima immagine considerata e quindi bisogna unire a quella guà creata
				BufferedImage img_buff1 = img_final;
				BufferedImage img_buff2 = image1.getAsBufferedImage();

				img_final = new BufferedImage(img_buff1.getWidth(), img_buff1.getHeight() + img_buff2.getHeight(), BufferedImage.TYPE_INT_RGB);

				img_final.createGraphics().drawImage(img_buff1, 0, 0, null);
				img_final.createGraphics().drawImage(img_buff2, 0, img_buff1.getHeight() + pageOffset, null);
			} else {
				// E' la prima immagine creata
				img_final = image1.getAsBufferedImage();
			}
			// Chiusura dello stream - altrimenti non si riesce a cancellare le immagini
			stream.close();
		}

		if (img_final != null) {
			float widthImage = img_final.getWidth();
			float heightImage = img_final.getHeight();
			float percW = widthImage / width;
			float percH = heightImage / height;
			float finalW = 0;
			float finalH = 0;
			if (percW < percH) {
				finalW = width;
				finalH = heightImage / percW;
			} else {
				finalH = height;
				finalW = widthImage / percH;
			}

			Image scaledImage = img_final.getScaledInstance(new Float(finalW).intValue(), new Float(finalH).intValue(), Image.SCALE_AREA_AVERAGING);

			return scaledImage;
		}

		return null;
	}
}
