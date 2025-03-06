package it.eng.client.applet;

import it.eng.client.applet.i18N.Messages;
import it.eng.client.applet.operation.detector.CadesDetector;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;

public class ManualFile extends JFrame {

	private JPanel contentPane;
	
	private File lastdirectory = null;
	private JLabel selectedFile = null;
	private JButton btnSelectFile = null;
	private JButton btnConferma = null; 
	private JButton btnAnnulla = null; 
	
	private String applicationurl = null;
	private String cookie = null;
	private ISmartCard card = null;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManualFile frame = new ManualFile("","",new File("c:/testCades/testoodt.odt"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
 
	
	/**
	 * Create the frame.
	 */
	public ManualFile(String url,String cookiestr,final File originalFile){
		super();
		
		this.applicationurl = url;
		this.cookie = cookiestr;
		//this.card = cardtmp;
		
		
		NimRODTheme nt = new NimRODTheme("it/eng/client/lookfeel/Default.theme");
		NimRODLookAndFeel nf = new NimRODLookAndFeel();
		NimRODLookAndFeel.setCurrentTheme(nt);
			
		try {
			UIManager.setLookAndFeel(nf);
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		selectedFile = new JLabel("selezionare il file");
		selectedFile.setBounds(15, 30, 200, 30);
		contentPane.add(selectedFile);
		/////////////conferma
		btnConferma = new JButton(Messages.getMessage("msg.Confirm"));
		//btnConferma.setIcon(new ImageIcon(ManualFile.class.getResource("/it/eng/client/applet/conferma.png")));
		btnConferma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO invia il file al chiamante
				ManualFile.this.dispose();
				 
			}
		});
		btnConferma.setBounds(15, 80, 100, 23);
		contentPane.add(btnConferma);
		btnConferma.setEnabled(false);
//////// annulla
//		btnAnnulla = new JButton(Messages.getMessage("msg.cancel"));
//		btnAnnulla.setIcon(new ImageIcon(ManualFile.class.getResource("/it/eng/client/applet/conferma.png")));
//		btnAnnulla.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				//Chiamo la fine della chiamata
//				ManualFile.this.dispose();
//				 
//			}
//		});
//		btnAnnulla.setBounds(100, 239, 120, 23);
//		contentPane.add(btnAnnulla);
//		btnAnnulla.setEnabled(true);
		////////////selectFile
		btnSelectFile = new JButton(Messages.getMessage("msg.selectFile"));
		//btnSelectFile.setIcon(new ImageIcon(ManualFile.class.getResource("/it/eng/client/applet/conferma.png")));
		btnSelectFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Chiamo la fine della chiamata
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				if(lastdirectory!=null){
					chooser.setCurrentDirectory(lastdirectory);
				}
				int option = chooser.showOpenDialog(ManualFile.this);
				if (option == JFileChooser.APPROVE_OPTION) {
					 File signerfile = chooser.getSelectedFile();
					 lastdirectory=chooser.getCurrentDirectory();
					boolean valid=false;
					//Effettuo un controllo sul file
					try {
						//sbustoe il file e verifico che l'hash sia pari a
						//a quello originale
						InputStream is=null;
						CadesDetector detector=new CadesDetector();
						is=detector.getContent(signerfile);
						byte[] calchash=DigestUtils.sha256(is);
						byte[] orihash=DigestUtils.sha256(FileUtils.openInputStream(originalFile));
						valid = Arrays.equals(calchash, orihash);
						if(valid){
							selectedFile.setText(signerfile.getAbsolutePath());
							selectedFile.setIcon(new ImageIcon(ManualFile.class.getResource("/it/eng/client/applet/conferma.png")));
						}else{
							JOptionPane.showMessageDialog((Component)ManualFile.this,"Il file associato non corrisponde al file originale!","Errore", JOptionPane.ERROR_MESSAGE);
							selectedFile.setIcon(null);
						}
					} catch (Exception ex) {
						 
						JOptionPane.showMessageDialog((Component)ManualFile.this,"Errore nella verifica della firma!","Errore", JOptionPane.ERROR_MESSAGE);
						selectedFile.setIcon(null);
					}finally{
						//Controllo lo stato del bottone
						btnConferma.setEnabled(valid);
					}
									
				}
				 
			}
		});
		btnSelectFile.setBounds(150, 80, 100, 23);
		contentPane.add(btnSelectFile);
		btnSelectFile.setEnabled(true);
	}
	
	
}