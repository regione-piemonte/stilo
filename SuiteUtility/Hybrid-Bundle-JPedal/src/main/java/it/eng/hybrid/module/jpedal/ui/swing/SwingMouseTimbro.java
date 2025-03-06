package it.eng.hybrid.module.jpedal.ui.swing;

import it.eng.hybrid.module.jpedal.pdf.PdfDecoder;
import it.eng.hybrid.module.jpedal.ui.MouseMode;
import it.eng.hybrid.module.jpedal.ui.SwingGUI;
import it.eng.hybrid.module.jpedal.viewer.Commands;
import it.eng.hybrid.module.jpedal.viewer.Values;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.util.Map;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import sun.security.action.GetBooleanAction;

public class SwingMouseTimbro {

	SwingMouseSelector selectionFunctions;
	private PdfDecoder decode_pdf;
	private SwingGUI currentGUI;
	private Values commonValues;
	private Commands currentCommands;

	public SwingMouseTimbro(PdfDecoder decode_pdf, SwingGUI currentGUI,
			Values commonValues,Commands currentCommands, SwingMouseSelector selectionFunctions) {

		this.decode_pdf = decode_pdf;
		this.currentGUI = currentGUI;
		this.commonValues = commonValues;
		this.currentCommands = currentCommands;
		this.selectionFunctions = selectionFunctions;
	}

	public void updateRectangle() {
		// TODO Auto-generated method stub

	}

	public void mouseClicked(MouseEvent event) {
		//System.out.println("mouseClicked timbro");
		
	}

	public void mouseEntered(MouseEvent event) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent event) {
		
	}

	public void mousePressed(MouseEvent event) {
		int page = commonValues.getCurrentPage();
		Point p = selectionFunctions.getCoordsOnPage(event.getX(), event.getY(), page);
		int x = (int)p.getX();
		int y = (int)p.getY();
		
		int widthPage = (int)(decode_pdf.getPdfPageData().getCropBoxWidth(commonValues.getCurrentPage()));
		int heightPage = (int)(decode_pdf.getPdfPageData().getCropBoxHeight(commonValues.getCurrentPage()));
			
		int rotation = currentGUI.getRotation();
		
		int posizioneTimbro = calcolaPosizioneTimbro(widthPage, heightPage, x, y, rotation);
		//currentCommands.getTimbroFrame().getListaPosizioniTimbro().setSelectedIndex(posizioneTimbro);
	}

	public void mouseReleased(MouseEvent event) {
		currentCommands.getMouseMode().setMouseMode(MouseMode.MOUSE_MODE_TEXT_SELECT);
		currentCommands.setCommandTimbro(false);
	}

	public void mouseDragged(MouseEvent event) {
		
	}

	public void mouseMoved(MouseEvent event) {

	}

	public void mouseWheelMoved(MouseWheelEvent event) {
		
	}

	private int calcolaPosizioneTimbro(int widthPage, int heightPage, int x, int y, int rotazione){
		int posizioneTimbro=0;
		int centroX=widthPage/2;
		int centroY=heightPage/2;
		
		
		if( rotazione==0){
			if(x>centroX && y>centroY)
				posizioneTimbro = 0;
			if(x>centroX && y<centroY)
				posizioneTimbro = 2;
			if(x<centroX && y>centroY)
				posizioneTimbro = 1;
			if(x<centroX && y<centroY)
				posizioneTimbro = 3;
		} else if( rotazione==90){
			if(x>centroX && y>centroY)
				posizioneTimbro = 2;
			if(x>centroX && y<centroY)
				posizioneTimbro = 3;
			if(x<centroX && y>centroY)
				posizioneTimbro = 0;
			if(x<centroX && y<centroY)
				posizioneTimbro = 1;
		} else if( rotazione==180){
			if(x>centroX && y>centroY)
				posizioneTimbro = 3;
			if(x>centroX && y<centroY)
				posizioneTimbro = 1;
			if(x<centroX && y>centroY)
				posizioneTimbro = 2;
			if(x<centroX && y<centroY)
				posizioneTimbro = 0;
		} else if( rotazione==270){
			if(x>centroX && y>centroY)
				posizioneTimbro = 1;
			if(x>centroX && y<centroY)
				posizioneTimbro = 0;
			if(x<centroX && y>centroY)
				posizioneTimbro = 3;
			if(x<centroX && y<centroY)
				posizioneTimbro = 2;
		}
		
		return posizioneTimbro;
	}

	public void dispose(){
		selectionFunctions=null;
		decode_pdf=null;
		currentGUI=null;
		commonValues=null;
		currentCommands=null;

	}
}


