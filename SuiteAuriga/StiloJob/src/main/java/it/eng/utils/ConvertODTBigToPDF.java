/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.odftoolkit.odfdom.converter.pdf.PdfConverter;
import org.odftoolkit.odfdom.converter.pdf.PdfOptions;
import org.odftoolkit.odfdom.doc.OdfDocument;



public class ConvertODTBigToPDF
{
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ConvertODTBigToPDF.class);
	
	private static ConvertODTBigToPDF instance;
	
	public static ConvertODTBigToPDF getInstance() {
		if (instance == null) {
			instance = new ConvertODTBigToPDF();
		}
		return instance;
	}
	
	
	
    public static void main( String[] args )
    {
        
    	File filledTemplate = new File("C:\\\\Downloads\\\\EXPORT_CODA\\\\Rilascio_batch\\\\ComuneTorino-Modellorelatadipubblicazione.odt");
    	File relataPdf = new File("C:\\Downloads\\EXPORT_CODA\\Rilascio_batch\\ComuneTorino-Modellorelatadipubblicazione.pdf");
    	
    	ConvertODTBigToPDF convi = new ConvertODTBigToPDF();
    	convi.convert(filledTemplate, relataPdf);
    }
    
	public ConvertODTBigToPDF() {
	}
	
	public File convert (File modelloWithValues,File relataPdf)
	
	{
		logger.info("convert INIZIO");
		
		Class klass = PdfConverter.class;
		URL location = klass.getResource('/' + klass.getName().replace('.', '/') + ".class");
		logger.info("location file: "+location.getFile());
		
		long startTime = System.currentTimeMillis();

        try
        {
        	Class klass1 = OdfDocument.class;
    		URL location1 = klass1.getResource('/' + klass1.getName().replace('.', '/') + ".class");
    		logger.info("location file: "+location1.getFile());
    		logger.info("modelloWithValues: "+modelloWithValues.getAbsolutePath());
        	// 1) Load odt with ODFDOM
    		
    		OdfDocument document = OdfDocument.loadDocument(modelloWithValues);

    		logger.info("document: "+document.getDocumentPath());
            
            OutputStream out = new FileOutputStream( relataPdf );
            PdfOptions pdfOptions = PdfOptions.create(); //PdfOptions.create().fontEncoding( "windows-1250" );
            logger.info("pdfOptions.getFontEncoding(): "+pdfOptions.getFontEncoding());
            PdfConverter.getInstance().convert( document, out, pdfOptions );
            out.close();
        }
        catch ( Exception e )
        {
            logger.error("Throwable: "+e.getMessage());
        }
        logger.info( "Generate relataPdf with " + ( System.currentTimeMillis() - startTime ) + " ms." );
		
        logger.info("convert FINE");
		return relataPdf;
	}
    
    
}
