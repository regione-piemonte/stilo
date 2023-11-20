/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

import it.eng.module.foutility.beans.CheckPdfCommentiFileBean;
import it.eng.module.foutility.beans.VerificaPdfBean;
import it.eng.utility.pdfUtility.bean.PdfBean;
import it.eng.utility.pdfUtility.commenti.PdfCommentiUtil;

public class CheckPdfCommenti {

	public static final Logger logger = LogManager.getLogger(CheckPdfCommenti.class);
	
	public static CheckPdfCommentiFileBean isPdfConCommenti(File file) {
		logger.debug("Verifico se il file " + file + " contiene commenti");
		CheckPdfCommentiFileBean checkPdfCommentiFileBean = new CheckPdfCommentiFileBean();
		
		PdfBean pdfbean = PdfCommentiUtil.isPdfConCommenti( file );

		if( pdfbean!=null ){
			if( pdfbean.getWithComment()!=null )
				checkPdfCommentiFileBean.setFlgContieneCommenti( pdfbean.getWithComment() );
			if( pdfbean.getPagesWithComment()!=null )
				checkPdfCommentiFileBean.setPageWithCommentBox( pdfbean.getPagesWithComment() );
		}
		return checkPdfCommentiFileBean;
	}
	
	
	public static boolean isAttivaVerificaPdfCommenti() {
		try {
			ApplicationContext context = FileoperationContextProvider.getApplicationContext();
			VerificaPdfBean formatoBean = (VerificaPdfBean) context.getBean("VerificaPdfBean");
			
			// VERIFICA FORMATO PDF CON COMMENTI = ATTIVA
			if("true".equalsIgnoreCase(formatoBean.getAttivaVerificaCommenti())) {
				return true;
			} else {
				return false;
			}
		} catch (NoSuchBeanDefinitionException e) {
			//log.error(e);
			logger.warn("Verifica pdf con commenti non attiva, bean di configurazione non presente");
		} catch (Exception e1) {
			logger.error(e1);
		} finally {
			
		}

		return false;
	}
	
	public static void main(String[] args) {
		String filePath = "C:/Users/TESAURO/Downloads/All risk 2020 con quietanza 2021.pdf";
		filePath = "C:/Users/TESAURO/Desktop/FileEstratto-1223928707796676054.pdf";
		filePath = "C:/Users/TESAURO/Desktop/FileEstratto-9073785111623771608.pdf";
		filePath = "C:/Users/TESAURO/Downloads/Ricevuta di versamento_bonificoSingolo-3_problema.pdf";
		filePath = "C:/Users/TESAURO/Downloads/FileEstratto-1506504980292632273.file";
		
		
		filePath="C:/Users/TESAURO/Downloads/bond_300mln_2024b_15.03.2022_unicredit_signed_perde_firma_a_destra.pdf";
		//filePath="C:/Users/TESAURO/Downloads/bond_300mln_2024b_15.03.2022_unicredit_signed_perde_firma_a_destra_modificato.pdf";
		//filePath="C:/Users/TESAURO/Downloads/fileConCommenti.pdf";
		System.out.println(filePath);
		File file = new File(filePath);
		/*try {
			PdfReader pdfReader = new PdfReader(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*CheckPdfCommentiFileBean pages = PdfCommentiUtil.isPdfConCommenti(filePath);
		System.out.println(pages.getFlgContieneCommenti());
		System.out.println(pages.getPageWithCommentBox().size());
		if( pages.getPageWithCommentBox().size()>0){
			try {
				File fileSenzaCommenti = PdfCommentiUtil.convertPagesPdfToPdfImages(file, pages.getPageWithCommentBox());
				System.out.println(fileSenzaCommenti);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			*/
		
//		List<Integer> listPagesToConvert = new ArrayList<Integer>();
//		//listPagesToConvert.add(new Integer(3));
//		listPagesToConvert = pages.getPageWithCommentBox();
//		try {
//			System.out.println(rewriteDocument(file, listPagesToConvert , false));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		String testo="acbcbccxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
//		try{
//			PdfReader pdfReader = new PdfReader(new FileInputStream(file));
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			PdfStamper pdfStamper = new PdfStamper(pdfReader, baos);
//			
//			int totalPages = pdfReader.getNumberOfPages();
//			System.out.println(totalPages);
//			
//			PdfContentByte over = pdfStamper.getOverContent(3);
//			BaseFont bf = BaseFont.createFont("Courier", BaseFont.WINANSI, BaseFont.NOT_EMBEDDED);
//			over.setFontAndSize(bf, 20.0f);
//			
//			float dimensioneSingoloCarattere = bf.getWidthPoint(testo.charAt(0), 10.0f);
//			System.out.println("dimensioneSingoloCarattere::::  "  + dimensioneSingoloCarattere);
//			
//			System.out.println(pdfStamper.getReader().getPageSize(3).getWidth() );
//			System.out.println(pdfStamper.getReader().getPageSize(3).getHeight() );
//
//			System.out.println(pdfStamper.getReader().getPageSize(3).getRotation() );
//
//			int alignText = 0;
//			float orientationText = 0;
//			over.beginText();
//			over.showTextAligned(alignText, testo, 50, 50, orientationText);
//			over.endText();
//			
//			pdfStamper.getWriter().createXmpMetadata();
//
//			pdfStamper.close();
//			pdfReader.close();
//
//			// File fileTimbrato = new File( "provaTimbrata.pdf");
//			File fileTimbrato = new File(file.getParent() + "/" + "output/abc.pdf");
//			FileOutputStream fileOutputStream = new FileOutputStream(fileTimbrato);
//			IOUtils.write(baos.toByteArray(), fileOutputStream);
//			
//		} catch (Exception e){
//			e.printStackTrace();
//		}
		
	}
	
	
}
