package it.eng.utility.oomanager;

import it.eng.utility.oomanager.config.OpenOfficeInstance;

import java.io.IOException;



public class OpenOfficeService extends Thread{

	private OpenOfficeInstance instance;
	
	public OpenOfficeService(OpenOfficeInstance instance) {
		this.instance = instance;
	}	
	
	@Override
	public void run() {
		
		//Riavvio del servizio
		try {
			Runtime.getRuntime().exec("sc stop "+instance.getServicename());
			
			Runtime.getRuntime().exec("sc start "+instance.getServicename());
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
	}
	
	
}
