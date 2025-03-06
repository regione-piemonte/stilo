package it.eng.hybrid.module.pieChart.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import it.eng.areas.hybrid.module.util.json.JSONArray;
import it.eng.areas.hybrid.module.util.json.JSONObject;
import it.eng.hybrid.module.pieChart.PieChartClientModule;
import it.eng.hybrid.module.pieChart.dataset.BarChartBuilder;
import it.eng.hybrid.module.pieChart.dataset.BarChartProperty;
import it.eng.hybrid.module.pieChart.dataset.PieChartBuilder;
import it.eng.hybrid.module.pieChart.dataset.PieChartProperty;
import it.eng.hybrid.module.pieChart.dataset.PieDataBean;
import it.eng.hybrid.module.pieChart.parameters.ReportParameterBean;
import it.eng.hybrid.module.pieChart.url.CustomBarUrlGenerator;
import it.eng.hybrid.module.pieChart.url.CustomPieUrlGenerator;
import it.eng.hybrid.module.pieChart.url.UrlBuilder;
import it.eng.hybrid.module.pieChart.util.PdfBuilder;
import it.eng.hybrid.module.pieChart.util.XlsBuilder;

public class PieChartApplication extends JFrame implements ActionListener {

	public final static Logger logger = Logger.getLogger(PieChartApplication.class);

	private PieChartClientModule module;

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
	private JFreeChart actualChart;
	private String asseX;
	private String asseY;
	// private PieChartProperty mPieChartProperty;
	private JComponent[] panels;
	public static HashMap<Integer, HashMap<Integer, PieDataBean>> mapValues;
	private ChartType type;
	private int actualLevel;

	private Map<String, String> props = new HashMap<String, String>();

	/**
	 * Costruttore generico
	 */
	public PieChartApplication(PieChartClientModule module, JSONArray parameters) {
		setName("Visualizzazione Grafici");
		this.module = module;

		JSONObject options = parameters.getJSONObject(0);
		Iterator optionsItr = options.keys();
		List<String> optionNames = new ArrayList<String>();
		while (optionsItr.hasNext()) {
			optionNames.add((String) optionsItr.next());

		}
		for (int i = 0; i < options.length(); i++) {
			if (options.get(optionNames.get(i)) instanceof String) {
				String propValue = options.getString(optionNames.get(i)) != null ? options.getString(optionNames.get(i)) : "";
				props.put(optionNames.get(i), propValue);
				logger.info("Proprieta' " + optionNames.get(i) + "=" + options.getString(optionNames.get(i)));
			} else {
				logger.info("Proprieta' " + optionNames.get(i) + " non � una stringa");
			}
		}

		init();
	}

	public void init() {
		setMapValues(new HashMap<Integer, HashMap<Integer, PieDataBean>>());
		// Recupero i parametri
		recuperaParametri();
		// Stabilisco il tipo di chart
		if (parameters.getTipoReport().equals("tempi_medi_tra_assegnazione_e_presa_in_carico")) {
			type = ChartType.BAR;
		} else {
			type = ChartType.PIE;
		}
		// inizializzo
		try {
			initPanel();
		} catch (Throwable e) {
			logger.error("Errore " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile inizializzare l'applicazione " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
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
		if (panels[actualLevel] == null) {
			JOptionPane.showMessageDialog(this, "Nessun dato presente", "Attenzione", JOptionPane.INFORMATION_MESSAGE);
			forcedClose();
			// destroy();
			// System.exit(-1);
		} else {
			// mPieChartProperty = PieChartBuilder.retrieveDataset(UrlBuilder.buildUrl());
			// Disegna il pannello principale
			drawMainPanel();
			// Setta le dimensioni ricevute in ingresso
			setSize(new Dimension(parameters.getWidth(), parameters.getHeight()));
			// Aggiusta il layout
			validate();
		}
	}

	private JPanel buildBar(String pStrLevel) throws MalformedURLException, IOException {
		BarChartProperty lBarChartProperty = BarChartBuilder.retrieveDataset(UrlBuilder.buildUrl());
		if (lBarChartProperty.getDataset().getColumnCount() == 0) {
			return null;
		} else {
			mapValues.put(actualLevel, BarChartBuilder.mapValues);
			BarChartPanel mainPanel = new BarChartPanel(lBarChartProperty, this, new ThirdLevelLeftClickAction(this), new CustomBarUrlGenerator(pStrLevel),
					true, new DetailMenuItem(this));
			return mainPanel;
		}
	}

	private JPanel buildPie(String pStrLevel) throws MalformedURLException, IOException {
		PieChartProperty lPieChartProperty = PieChartBuilder.retrieveDataset(UrlBuilder.buildUrl());
		if (lPieChartProperty.getDataset().getItemCount() == 0) {
			return null;
		} else {
			mapValues.put(actualLevel, PieChartBuilder.mapValues);
			PieChartPanel mainPanel = new PieChartPanel(lPieChartProperty, this, true, new DetailLeftClickAction(this), new CustomPieUrlGenerator(pStrLevel),
					new DetailMenuItem(this));
			return mainPanel;
		}
	}

	/**
	 * Recupera i parametri
	 */
	private void recuperaParametri() {
		ReportParameterBean lReportParameterBean = new ReportParameterBean();
		lReportParameterBean.setPercentageServlet(props.get(PERCENTAGE_SERVLET));
		lReportParameterBean.setPdfBuilderPercentageServlet(props.get(PDF_BUILDER_PERCENTAGE_SERVLET));
		lReportParameterBean.setXlsBuilderPercentageServlet(props.get(XLS_BUILDER_PERCENTAGE_SERVLET));
		lReportParameterBean.setIdDominio(props.get(ID_DOMINIO));
		lReportParameterBean.setIdSchema(props.get(ID_SCHEMA));
		lReportParameterBean.setIdUtente(props.get(ID_UTENTE));
		lReportParameterBean.setRichiesta(props.get(RICHIESTA));
		lReportParameterBean.setA(props.get(A));
		lReportParameterBean.setDa(props.get(DA));
		lReportParameterBean.setLevel(props.get(LEVEL));
		lReportParameterBean.setTipoRegistrazione(props.get(TIPO_REGISTRAZIONE));
		lReportParameterBean.setTipoReport(props.get(TIPO_REPORT));
		String width = props.get(WIDTH);
		String heigth = props.get(HEIGTH);
		if (width != null) {
			try {
				int lIntWidth = Integer.valueOf(width);
				lReportParameterBean.setWidth(lIntWidth);
			} catch (Exception e) {
				lReportParameterBean.setWidth(widthDefault);
			}
		} else {
			lReportParameterBean.setWidth(widthDefault);
		}
		if (heigth != null) {
			try {
				int lIntHeigth = Integer.valueOf(heigth);
				lReportParameterBean.setHeight(lIntHeigth);
			} catch (Exception e) {
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
		PieChartApplication.parameters = parameters;
	}

	public void detail(String url) {
		try {
			actualLevel++;
			switch (type) {
			case PIE:
				panels[actualLevel] = buildDetailPie(url, actualLevel + 1);
				break;
			case BAR:
				panels[actualLevel] = buildDetailBar(url, actualLevel + 1);
				break;
			default:
				break;
			}
			setContentPane(panels[actualLevel]);
			repaint();
			validate();
			// update(getGraphics());
			// revalidate();
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile visualizzare il dettaglio " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		}
	}

	private JPanel buildDetailBar(String url, int level) throws MalformedURLException, IOException {
		boolean generateUrl;
		// if (level == 3) {
		if (false) {
			generateUrl = false;
		} else
			generateUrl = true;
		ThirdLevelChartPanel lThirdLevelChartPanel = new ThirdLevelChartPanel(url, this, generateUrl);
		mapValues.put(actualLevel, BarChartBuilder.mapValues);
		return lThirdLevelChartPanel;
	}

	private JPanel buildDetailPie(String url, int level) throws MalformedURLException, IOException {
		boolean generateUrl;
		// if (level == 3) {
		if (false) {
			generateUrl = false;
		} else
			generateUrl = true;
		DetailPieChartPanel detailPanel = new DetailPieChartPanel(url, this, generateUrl);
		mapValues.put(actualLevel, PieChartBuilder.mapValues);
		return detailPanel;
	}

	/**
	 * Disegna un PieChartPanel recuperando le info da percentageSevlet, generando gli url, mettendo come azione per il tasto sinistro del mouse la
	 * {@link DetailLeftClickAction} e aggiungendo al contextMenu il {@link DetailMenuItem}
	 * 
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	protected void drawMainPanel() throws MalformedURLException, IOException {
		setContentPane(panels[0]);
	}

	@Override
	public void actionPerformed(ActionEvent paramActionEvent) {
		if (paramActionEvent.getActionCommand().equals(PdfMenuItem.ACTION_PDF)) {
			logger.debug("Salvo PDF " + actualChart.getTitle().getText());
			savePdf();
		} else if (paramActionEvent.getActionCommand().equals(ExcelMenuItem.ACTION_EXCEL)) {
			logger.debug("Salvo Excel " + actualChart.getTitle().getText());
			saveXls();
		} else if (paramActionEvent.getActionCommand().equals(DetailMenuItem.ACTION_DETAIL)) {
			Object source = paramActionEvent.getSource();
			DetailMenuItem lDetailMenuItem = (DetailMenuItem) source;
			String url = lDetailMenuItem.getOpzioni();
			detail(url);
		} else if (paramActionEvent.getActionCommand().equals(BackMenuItem.ACTION_BACK_TO_MAIN)) {
			back();
		} else if (paramActionEvent.getActionCommand().equals(BackButton.ACTION)) {
			back();
		} else if (paramActionEvent.getActionCommand().equals(ThirdLevelMenuItem.ACTION_DETAIL)) {
			Object source = paramActionEvent.getSource();
			DetailMenuItem lDetailMenuItem = (DetailMenuItem) source;
			String url = lDetailMenuItem.getOpzioni();
			detail(url);
		}
	}

	private void back() {
		actualLevel--;
		try {
			// drawMainPanel();
			setContentPane(panels[actualLevel]);
			repaint();
			validate();
			// revalidate();
		} catch (Exception e) {
			logger.error("Errore: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "Impossibile tornare indietro " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Apre una maschera di scelta file con soli pdf e se ne viene selezionato uno lo salva
	 */
	protected void savePdf() {
		// Creo il dialog
		JFileChooser lFileChooser = new JFileChooser();
		// Metto un filtro per pdf
		lFileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "PDF document";
			}

			@Override
			public boolean accept(File paramFile) {
				if (paramFile.isDirectory())
					return true;
				String fname = paramFile.getName().toLowerCase();
				return fname.endsWith("pdf");
			}
		});
		int retrival = lFileChooser.showSaveDialog(this);
		// Se ho premuto save
		if (retrival == JFileChooser.APPROVE_OPTION) {
			File file = null;
			// Se il file � un .pdf
			if (lFileChooser.getSelectedFile().getName().toLowerCase().endsWith(".pdf")) {
				file = lFileChooser.getSelectedFile();
			} else {
				// Altrimenti aggiungo l'estensione
				file = new File(lFileChooser.getSelectedFile().getPath() + ".pdf");
			}
			try {
				logger.debug(getWidth());
				logger.debug(getHeight());
				// Scrivo il pdf
				PdfBuilder.writeChartToPdfFile(file, actualChart, getWidth(), getHeight(), mapValues.get(new Integer(actualLevel)), type, asseX, asseY);
				JOptionPane.showMessageDialog(this, "File Salvato Correttamente", "Info", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				logger.error("Errore: " + e.getMessage(), e);
				JOptionPane.showMessageDialog(this, "Impossibile salvare correttamente il file " + e.getMessage(), "Attenzione", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Apre una maschera di scelta file con soli pdf e se ne viene selezionato uno lo salva
	 */
	protected void saveXls() {
		// Creo il dialog
		JFileChooser lFileChooser = new JFileChooser();
		// Metto un filtro per pdf
		lFileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "Foglio Excel";
			}

			@Override
			public boolean accept(File paramFile) {
				if (paramFile.isDirectory())
					return true;
				String fname = paramFile.getName().toLowerCase();
				return fname.endsWith("xls");
			}
		});
		int retrival = lFileChooser.showSaveDialog(this);
		// Se ho premuto save
		if (retrival == JFileChooser.APPROVE_OPTION) {
			File file = null;
			// Se il file � un .pdf
			if (lFileChooser.getSelectedFile().getName().toLowerCase().endsWith(".xls")) {
				file = lFileChooser.getSelectedFile();
			} else {
				// Altrimenti aggiungo l'estensione
				file = new File(lFileChooser.getSelectedFile().getPath() + ".xls");
			}
			try {
				// Scrivo il pdf
				XlsBuilder.getXlsFromServer(file, actualChart, parameters.getWidth(), parameters.getHeight(), mapValues.get(new Integer(actualLevel)), type);
				JOptionPane.showMessageDialog(this, "File Salvato Correttamente", "Info", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				logger.error("Errore: " + e.getMessage(), e);
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

	/*
	 * Definizione di un metodo per definire il comportamento in caso di click sulla x della finestra di Windows
	 */
	public void forcedClose() {
		logger.info("Chiusura forzata");
		module.setCallBackFunction("Uscita forzata");
		this.dispose();
	}
}
