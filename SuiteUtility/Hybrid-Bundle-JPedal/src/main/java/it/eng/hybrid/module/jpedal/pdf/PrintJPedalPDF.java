package it.eng.hybrid.module.jpedal.pdf;

import it.eng.hybrid.module.jpedal.exception.PdfException;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 * Classe JPedal che implementa l'interfaccia Printable per consentire la stampa dei file attraverso JPedal.
 * <br>
 * @author upescato
 *
 */


//La classe e' stata scopiazzata da un thread sul forum di JPedal

//Il metodo print opera con un fattore di scala pari a 2, altrimenti, come letto sul forum di supporto di JPedal si presenta qualche problema
//di renderizzazione.

// https://idrsolutions.fogbugz.com/default.asp?support.2.975.6 [consultato il 02.07.2012, upescato]

class PrintJPedalPDF implements Printable {

        private PdfDecoder _pdfDecoder = null;

        public PrintJPedalPDF(PdfDecoder pdfDecoder) {
            _pdfDecoder = pdfDecoder;
        }       

        public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

            if (pageIndex < _pdfDecoder.getNumberOfPages()) {

                try {
                    int scale_factor = 2;
                    _pdfDecoder.setPageParameters(scale_factor, pageIndex+1);

                    BufferedImage bufferedImage = _pdfDecoder.getPageAsImage(pageIndex+1);

                    graphics.drawImage(
                    		bufferedImage, 0, 0, 
                    		bufferedImage.getWidth()/scale_factor, 
                    		bufferedImage.getHeight()/scale_factor, 
                    		null);

                } catch (PdfException ex) {
                    ex.printStackTrace();
                }

                return PAGE_EXISTS;

            }

            else {
                return NO_SUCH_PAGE;
            }

        }

    } 