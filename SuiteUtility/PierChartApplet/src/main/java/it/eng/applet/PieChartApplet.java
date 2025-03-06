package it.eng.applet;

import it.eng.applet.action.impl.DetailLeftClickAction;
import it.eng.applet.action.impl.ThirdLevelLeftClickAction;
import it.eng.applet.dataset.BarChartBuilder;
import it.eng.applet.dataset.BarChartProperty;
import it.eng.applet.dataset.PieChartBuilder;
import it.eng.applet.dataset.PieChartProperty;
import it.eng.applet.dataset.bean.PieDataBean;
import it.eng.applet.menu.impl.BackMenuItem;
import it.eng.applet.menu.impl.DetailMenuItem;
import it.eng.applet.menu.impl.ExcelMenuItem;
import it.eng.applet.menu.impl.PdfMenuItem;
import it.eng.applet.menu.impl.ThirdLevelMenuItem;
import it.eng.applet.panel.BarChartPanel;
import it.eng.applet.panel.DetailPieChartPanel;
import it.eng.applet.panel.PieChartPanel;
import it.eng.applet.panel.ThirdLevelChartPanel;
import it.eng.applet.panel.button.BackButton;
import it.eng.applet.panel.type.ChartType;
import it.eng.applet.pdf.util.PdfBuilder;
import it.eng.applet.xls.util.XlsBuilder;
import it.eng.generator.url.UrlBuilder;
import it.eng.generator.url.bar.CustomBarUrlGenerator;
import it.eng.generator.url.pie.CustomPieUrlGenerator;
import it.eng.parameter.ReportParameterBean;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

public class PieChartApplet extends JApplet implements ActionListener {

	private static final String PERCENTAGE_SERVLET = "percentageServlet";
	private static final String PDF_BUILDER_PERCENTAGE_SERVLET = "pdfBuilderPercentageServlet";
	private static final String XLS_BUILDER_PERCENTAGE_SERVLET = "xlsBuilderPercentageServlet";
	private static final String ID_SCHEMA = "idSchema";
	private static final String ID_DOMINIO = "idDominio";
	private static final String ID_UTENTE = "idUtente";
	private static final String RICHIESTA = "richiesta";
	private static final String DA = "da";
	private static final String A = "a";
	private static final String LEVEL = "level";
	private static final String TIPO_REPORT = "tipoReport";
	private static final String TIPO_REGISTRAZIONE = "tipoDiRegistrazione";
	private static final String WIDTH = "width";
	private static final String HEIGTH = "height";
	private static final int widthDefault = 600;
	private static final int heigthDefault = 300;
	private static final long serialVersionUID = 1206265757349584604L;
	private static ReportParameterBean parameters;
	private static Logger mLogger = Logger.getLogger(PieChartApplet.class);
	private JFreeChart actualChart;
	private String asseX;
	private String asseY;
	//	private PieChartProperty mPieChartProperty;
	private JComponent[] panels;
	public static HashMap<Integer, HashMap<Integer, PieDataBean>> mapValues;
	private ChartType type;
	private int actualLevel;

	/**
	 * Costruttore generico
	 */
	public PieChartApplet(){
		setName("Visualizzazione Grafici");
	}

	@Override
	public void init() {
		setMapValues(new HashMap<Integer, HashMap<Integer,PieDataBean>>());
		//Recupero i parametri
		recuperaParametri();
		//Stabilisco il tipo di chart
		if (parameters.getTipoReport().equals("tempi_medi_tra_assegnazione_e_presa_in_carico")){
			type = ChartType.BAR;
		} else {
			type = ChartType.PIE;
		}
		//inizializzo
		try {
			initPanel();
		} catch (Throwable e) {
			mLogger.error("Errore " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile inizializzare l'applet " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initPanel() throws MalformedURLException, IOException {
		panels = new JPanel[100];
		actualLevel = 0;
		switch (type) {
		case PIE:
			panels[actualLevel] = buildPie("1");
			break;
		case BAR:
			panels[actualLevel] = buildBar("1");
			break;
		default:
			break;
		} 
		if (panels[actualLevel] == null){
			JOptionPane.showMessageDialog(this, "Nessun dato presente", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
			destroy();
			System.exit(-1);
		}
		//		mPieChartProperty = PieChartBuilder.retrieveDataset(UrlBuilder.buildUrl());
		//Disegna il pannello principale
		drawMainPanel();
		//Setta le dimensioni ricevute in ingresso
		setSize(new Dimension(parameters.getWidth(), parameters.getHeight()));
		//Aggiusta il layout
		validate();
	}

	private JPanel buildBar(String pStrLevel) throws MalformedURLException, IOException {
		BarChartProperty lBarChartProperty = BarChartBuilder.retrieveDataset(UrlBuilder.buildUrl());
		if (lBarChartProperty.getDataset().getColumnCount()==0){
			return null;
		} else {
			mapValues.put(actualLevel, BarChartBuilder.mapValues);
			BarChartPanel mainPanel = new BarChartPanel(lBarChartProperty, this, 
					new ThirdLevelLeftClickAction(this), new CustomBarUrlGenerator(pStrLevel), true, new DetailMenuItem(this));
			return mainPanel;
		}
	}

	private JPanel buildPie(String pStrLevel) throws MalformedURLException, IOException {
		PieChartProperty lPieChartProperty = PieChartBuilder.retrieveDataset(UrlBuilder.buildUrl());
		if (lPieChartProperty.getDataset().getItemCount()==0){
			return null;
		} else {
			mapValues.put(actualLevel, PieChartBuilder.mapValues);
			PieChartPanel mainPanel = new PieChartPanel(lPieChartProperty, this, true, new DetailLeftClickAction(this), new CustomPieUrlGenerator(pStrLevel), new DetailMenuItem(this));
			return mainPanel;
		}
	}

	/**
	 * Recupera i parametri dell'applet
	 */
	private void recuperaParametri() {
		ReportParameterBean lReportParameterBean = new ReportParameterBean();
		lReportParameterBean.setPercentageServlet(getParameter(PERCENTAGE_SERVLET));
		lReportParameterBean.setPdfBuilderPercentageServlet(getParameter(PDF_BUILDER_PERCENTAGE_SERVLET));
		lReportParameterBean.setXlsBuilderPercentageServlet(getParameter(XLS_BUILDER_PERCENTAGE_SERVLET));
		lReportParameterBean.setIdDominio(getParameter(ID_DOMINIO));
		lReportParameterBean.setIdSchema(getParameter(ID_SCHEMA));
		lReportParameterBean.setIdUtente(getParameter(ID_UTENTE));
		lReportParameterBean.setRichiesta(getParameter(RICHIESTA));
		lReportParameterBean.setA(getParameter(A));
		lReportParameterBean.setDa(getParameter(DA));
		lReportParameterBean.setLevel(getParameter(LEVEL));
		lReportParameterBean.setTipoRegistrazione(getParameter(TIPO_REGISTRAZIONE));
		lReportParameterBean.setTipoReport(getParameter(TIPO_REPORT));
		String width = getParameter(WIDTH);
		String heigth = getParameter(HEIGTH);
		if (width != null){
			try {
				int lIntWidth = Integer.valueOf(width);
				lReportParameterBean.setWidth(lIntWidth);
			} catch (Exception e){
				lReportParameterBean.setWidth(widthDefault);
			}
		} else {
			lReportParameterBean.setWidth(widthDefault);
		}
		if (heigth != null){
			try {
				int lIntHeigth = Integer.valueOf(heigth);
				lReportParameterBean.setHeight(lIntHeigth);
			} catch (Exception e){
				lReportParameterBean.setHeight(heigthDefault);
			}
		} else {
			lReportParameterBean.setHeight(heigthDefault);
		}
		parameters = lReportParameterBean;
	}

	public static ReportParameterBean getParameters() {
		return parameters;
	}

	public static void setParameters(ReportParameterBean parameters) {
		PieChartApplet.parameters = parameters;
	}

	public void detail(String url) {
		try {
			actualLevel++;
			switch (type) {
			case PIE:
				panels[actualLevel] = buildDetailPie(url, actualLevel+1);
				break;
			case BAR:
				panels[actualLevel] = buildDetailBar(url, actualLevel+1);
				break;
			default:
				break;
			} 
			setContentPane(panels[actualLevel]);
			repaint();
			validate();
			//			update(getGraphics());
			//			revalidate();
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile visualizzare il dettaglio " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		}
	}

	private JPanel buildDetailBar(String url, int level) throws MalformedURLException, IOException {
		boolean generateUrl;
//		if (level == 3) {
		if (false) {
			generateUrl = false;
		} else generateUrl = true;
		ThirdLevelChartPanel lThirdLevelChartPanel = new ThirdLevelChartPanel(url, this, generateUrl);
		mapValues.put(actualLevel, BarChartBuilder.mapValues);
		return lThirdLevelChartPanel;
	}

	private JPanel buildDetailPie(String url, int level) throws MalformedURLException, IOException {
		boolean generateUrl;
//		if (level == 3) {
		if (false) {
			generateUrl = false;
		} else generateUrl = true;
		DetailPieChartPanel detailPanel = new DetailPieChartPanel(url, this, generateUrl);
		mapValues.put(actualLevel, PieChartBuilder.mapValues);
		return detailPanel;
	}


	/**
	 * Disegna un PieChartPanel recuperando le info da percentageSevlet, generando gli url, mettendo come azione per il tasto sinistro del mouse la 
	 * {@link DetailLeftClickAction} e aggiungendo al contextMenu il {@link DetailMenuItem}
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	protected void drawMainPanel() throws MalformedURLException, IOException {
		setContentPane(panels[0]);
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getActionCommand().equals(PdfMenuItem.ACTION_PDF)){
			mLogger.debug("Salvo PDF " + actualChart.getTitle().getText());
			savePdf();
		} else if (paramActionEvent.getActionCommand().equals(ExcelMenuItem.ACTION_EXCEL)){
			mLogger.debug("Salvo Excel " + actualChart.getTitle().getText());
			saveXls();
		} else if (paramActionEvent.getActionCommand().equals(DetailMenuItem.ACTION_DETAIL)){
			Object source = paramActionEvent.getSource();
			DetailMenuItem lDetailMenuItem = (DetailMenuItem)source;
			String url = lDetailMenuItem.getOpzioni();
			detail(url);
		} else if (paramActionEvent.getActionCommand().equals(BackMenuItem.ACTION_BACK_TO_MAIN)){
			back();
		} else if (paramActionEvent.getActionCommand().equals(BackButton.ACTION)){
			back();
		}else if (paramActionEvent.getActionCommand().equals(ThirdLevelMenuItem.ACTION_DETAIL)){
			Object source = paramActionEvent.getSource();
			DetailMenuItem lDetailMenuItem = (DetailMenuItem)source;
			String url = lDetailMenuItem.getOpzioni();
			detail(url);
		}
	}

	private void back() {
		actualLevel--;
		try {
			//drawMainPanel();
			setContentPane(panels[actualLevel]);
			repaint();
			validate();
			//			revalidate();
		} catch (Exception e) {
			mLogger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile tornare indietro " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Apre una maschera di scelta file con soli pdf
	 * e se ne viene selezionato uno lo salva
	 */
	protected void savePdf() {
		//Creo il dialog
		JFileChooser lFileChooser = new JFileChooser();
		//Metto un filtro per pdf
		lFileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "PDF document";
			}
			@Override
			public boolean accept(File paramFile) {
				if (paramFile.isDirectory()) return true;
				String fname = paramFile.getName().toLowerCase();
				return fname.endsWith("pdf");
			}
		});
		int retrival = lFileChooser.showSaveDialog(this);
		//Se ho premuto save
		if (retrival == JFileChooser.APPROVE_OPTION){
			File file = null;
			//Se il file è un .pdf
			if (lFileChooser.getSelectedFile().getName().toLowerCase().endsWith(".pdf")){
				file = lFileChooser.getSelectedFile();
			} else {
				//Altrimenti aggiungo l'estensione
				file = new File(lFileChooser.getSelectedFile().getPath() + ".pdf");
			}
			try {
				mLogger.debug(getWidth());
				mLogger.debug(getHeight());
				//Scrivo il pdf
				PdfBuilder.writeChartToPdfFile(file, actualChart, getWidth(), getHeight(), mapValues.get(new Integer(actualLevel)), type,
						asseX, asseY);
				JOptionPane.showMessageDialog(this, "File Salvato Correttamente", "Info", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				mLogger.error("Errore: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(this, "Impossibile salvare correttamente il file " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	/**
	 * Apre una maschera di scelta file con soli pdf
	 * e se ne viene selezionato uno lo salva
	 */
	protected void saveXls() {
		//Creo il dialog
		JFileChooser lFileChooser = new JFileChooser();
		//Metto un filtro per pdf
		lFileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Foglio Excel";
			}
			@Override
			public boolean accept(File paramFile) {
				if (paramFile.isDirectory()) return true;
				String fname = paramFile.getName().toLowerCase();
				return fname.endsWith("xls");
			}
		});
		int retrival = lFileChooser.showSaveDialog(this);
		//Se ho premuto save
		if (retrival == JFileChooser.APPROVE_OPTION){
			File file = null;
			//Se il file è un .pdf
			if (lFileChooser.getSelectedFile().getName().toLowerCase().endsWith(".xls")){
				file = lFileChooser.getSelectedFile();
			} else {
				//Altrimenti aggiungo l'estensione
				file = new File(lFileChooser.getSelectedFile().getPath() + ".xls");
			}
			try {
				//Scrivo il pdf
				XlsBuilder.getXlsFromServer(file, actualChart, parameters.getWidth(), parameters.getHeight(), mapValues.get(new Integer(actualLevel)), type);
				JOptionPane.showMessageDialog(this, "File Salvato Correttamente", "Info", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				mLogger.error("Errore: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(this, "Impossibile salvare correttamente il file " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public JFreeChart getActualChart() {
		return actualChart;
	}

	public void setActualChart(JFreeChart actualChart) {
		this.actualChart = actualChart;
	}

	public int getActualLevel() {
		return actualLevel;
	}

	public void setActualLevel(int actualLevel) {
		this.actualLevel = actualLevel;
	}

	public void setMapValues(HashMap<Integer, HashMap<Integer, PieDataBean>> mapValues) {
		this.mapValues = mapValues;
	}

	public HashMap<Integer, HashMap<Integer, PieDataBean>> getMapValues() {
		return mapValues;
	}

	public void setAsseX(String asseX) {
		this.asseX = asseX;
	}

	public String getAsseX() {
		return asseX;
	}

	public void setAsseY(String asseY) {
		this.asseY = asseY;
	}

	public String getAsseY() {
		return asseY;
	}
}
