/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import it.eng.utility.ui.servlet.fileExtractor.FileExtractor;
import it.eng.utility.ui.servlet.fileExtractor.FileExtractorInstance;

@Controller
@RequestMapping("/previewtiff")
public class PreviewTIFFToPDFServlet {

	private static Logger logger = Logger.getLogger(PreviewTIFFToPDFServlet.class);

	@RequestMapping(value = "", method = RequestMethod.GET)
	@ResponseBody
	public HttpEntity<byte[]> download(@RequestParam("fromRecord") boolean fromRecord,
			@RequestParam("recordType") String recordType, @RequestParam("record") String record,
			@RequestParam("mimetype") String mimetype, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		
		HttpHeaders header = new HttpHeaders();
		if(mimetype != null && !"".equals(mimetype)) {
			// oltre a "text/html", anche per i mimetype "application/xhtml+xml" e "image/svg+xml" restituire il content-type = "text/plain" per non farlo eseguire dal client in caso ci fossero degli script al suo interno
			if("text/html".equalsIgnoreCase(mimetype) || "application/xhtml+xml".equalsIgnoreCase(mimetype) || "image/svg+xml".equalsIgnoreCase(mimetype)) {
				header.setContentType(MediaType.parseMediaType("text/plain"));
			} else {
				header.setContentType(MediaType.parseMediaType(mimetype));	
			}
		} else {
			header.setContentType(new MediaType("octet", "stream"));
		}
		
		byte[] documentBody = null;
		try {
			FileExtractor lFileExtractor = FileExtractorInstance.getInstance().getRelatedFileExtractor(recordType, req);
			lFileExtractor.getFileName();
			InputStream stream = lFileExtractor.getStream();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			writeMultipageTiffAsPdfToStream(stream, baos);

			documentBody = baos.toByteArray();

			header.setContentLength(documentBody.length);
			header.setCacheControl("public");
			header.add("Cache-Control", "public");
			return new HttpEntity<byte[]>(documentBody, header);
		} catch (Exception e) {
			logger.error("Errore download " + e.getMessage(), e);
			HttpHeaders responseHeaders = new HttpHeaders();
			responseHeaders.setContentType(MediaType.TEXT_HTML);
			responseHeaders.setCacheControl("private, no-store, no-cache, must-revalidate");
			responseHeaders.add("Content-Type", "text/html;charset=ISO-8859-1");
			responseHeaders.add("Cache-Control", "private, no-store, no-cache, must-revalidate");
			StringBuffer lStringBuffer = new StringBuffer();
			lStringBuffer.append("<html>");
			lStringBuffer.append("<head>");
			lStringBuffer.append("<body>");
			lStringBuffer.append("<h1>File not found</h1>");
			lStringBuffer.append("</body>");
			lStringBuffer.append("</html>");
			return new ResponseEntity<byte[]>(lStringBuffer.toString().getBytes(), responseHeaders, HttpStatus.OK);
		}
	}

	public void writeMultipageTiffAsPdfToStream(InputStream iStream, OutputStream outputStream)
			throws IOException, DocumentException {

		ImageInputStream is = ImageIO.createImageInputStream(iStream);
		Iterator<ImageReader> imageReaders = ImageIO.getImageReadersByFormatName("TIFF");

		if (!imageReaders.hasNext()) {
			ImageIO.scanForPlugins();
			imageReaders = ImageIO.getImageReadersByFormatName("TIFF");
			if (!imageReaders.hasNext()) {
				throw new UnsupportedOperationException("No TIFF Reader found!");
			}
		}

		ImageReader reader = imageReaders.next();
		reader.setInput(is);

		// Document doc = new Document(PageSize.A4, 0, 0, 0, 0);
		Document doc = new Document();
		PdfWriter.getInstance(doc, outputStream);
		doc.open();

		int pages = reader.getNumImages(true);

		for (int imageIndex = 0; imageIndex < pages; imageIndex++) {

			BufferedImage bufferedImage = null;
			try {
				// A seguire sono presenti due metodi di conversione delle immagini. Da
				// utilizzare solo quello piu opportuno
				// e tenere commentato il rimanente.
				bufferedImage = reader.read(imageIndex);
				Image image = Image.getInstance(bufferedImage, null);

				// 1) Soluzione: Utilizza le dimensioni originali delle immagini tiff per la
				// conversione.
				// Rectangle pageSize = new Rectangle(image.getWidth(), image.getHeight());
				// doc.setPageSize(pageSize);

				// 2) Soluzione: Forza le immagini tiff in formato A4 per la conversione.
				image.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight());
				doc.setMargins(image.getBorderWidthLeft(), image.getBorderWidthRight(), image.getBorderWidthTop(),
						image.getBorderWidthBottom());

				doc.newPage();
				doc.add(image);

			} catch (Exception e) {
				logger.error("Errore conversione tiff " + e.getMessage(), e);
			}
		}
		doc.close();
	}
}