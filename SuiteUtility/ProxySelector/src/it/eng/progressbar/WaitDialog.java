package it.eng.progressbar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class WaitDialog {

	Thread controller;
	ControllerThread lControllerThread;
	
	SuccessWaitAction successWaitAction;
	AbortWaitAction abortWaitAction;
	private JFrame dialog;
	private JProgressBar progressBar;
	private JFrame owner;
	private String completeMessage;

	public WaitDialog(JFrame owner, String dialogTitle, String progressTitle, String completeMessage, 
			SuccessWaitAction lSuccessWaitAction, AbortWaitAction lAbortWaitAction){
		successWaitAction = lSuccessWaitAction;
		abortWaitAction = lAbortWaitAction;
		dialog = new JFrame(dialogTitle);
		dialog.setResizable(false);
		dialog.setUndecorated(true);
		dialog.setLayout(new BorderLayout());
		dialog.setAlwaysOnTop(true);
		progressBar = new JProgressBar(0, 100);
		progressBar.setIndeterminate(true);
		this.owner = owner;
		this.completeMessage = completeMessage;
		dialog.add(BorderLayout.CENTER, progressBar);
		dialog.add(BorderLayout.NORTH, new JLabel(progressTitle));
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowListener() {

			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			@Override
			public void windowClosing(WindowEvent e) {
//				lControllerThread.setWait(true);
				System.out.println("Sei sicuro di voler chiudere?");
				int response = JOptionPane.showConfirmDialog(progressBar, "Sei sicuro di voler chiudere", "Attenzione", JOptionPane.YES_NO_OPTION);
				System.out.println(response);
				if (response == 0){
					progressBar.setVisible(false);
					dialog.dispose();
					if (abortWaitAction!=null) abortWaitAction.abort();
				} else lControllerThread.setWait(false);
			}
			@Override
			public void windowClosed(WindowEvent e) {
			}
			@Override
			public void windowActivated(WindowEvent e) {
			}
			@Override
			public void windowOpened(WindowEvent e) {
			}
		});
		dialog.setSize(300, 75);
		dialog.setLocationRelativeTo(owner);
		
//		lControllerThread = new ControllerThread(dialog, progressBar, completeMessage, abortWaitAction, successWaitAction, owner);
//		controller = new Thread(lControllerThread);
	}

	public void start(){
//		controller.start();
		dialog.setVisible(true);
	}

	
	public void error(){
		progressBar.setVisible(false);
		dialog.setVisible(false);
		if (abortWaitAction!=null) abortWaitAction.error();
		dialog.dispose();
	}

	public void stop(){
//		lControllerThread.stop();
		progressBar.setVisible(false);
		JOptionPane.showMessageDialog(dialog, completeMessage);
		dialog.setVisible(false);
		if (successWaitAction!=null) successWaitAction.success();
		dialog.dispose();
		
	}

	public class ControllerThread implements Runnable{

		private JDialog mDialog;
		private JProgressBar mProgressBar;
		private String mCompleteMessage;
		private AbortWaitAction abortAction;
		private SuccessWaitAction successAction;
		private boolean wait;
		private JFrame parent;
		public ControllerThread(JDialog dialog, JProgressBar progressBar, String completeMessage,
				AbortWaitAction lAbortWaitAction, SuccessWaitAction lSuccessWaitAction, JFrame parent) {
			mDialog = dialog;
			mProgressBar = progressBar;
			mCompleteMessage = completeMessage;
			wait = false;
			abortAction = lAbortWaitAction;
			successAction = lSuccessWaitAction;
			this.parent = parent;
		}
		@Override
		public void run() {
			mDialog.setVisible(true);
		}
		public void stop() {
			while (wait){
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			mProgressBar.setVisible(false);
			mDialog.setVisible(false);
			if (successAction!=null) successAction.success();
			mDialog.dispose();
			JOptionPane.showMessageDialog(parent, mCompleteMessage);
		}
		
		public void error() {
			while (wait){
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			mProgressBar.setVisible(false);
			mDialog.setVisible(false);
			if (successAction!=null) abortAction.error();
			mDialog.dispose();
		}
		public void setWait(boolean wait) {
			this.wait = wait;
		}
		public boolean isWait() {
			return wait;
		}

	}
	
	public Component getDialog(){
		return dialog;
	}
}



