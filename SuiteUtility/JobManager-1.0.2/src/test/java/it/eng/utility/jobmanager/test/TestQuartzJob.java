package it.eng.utility.jobmanager.test;

import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;

import java.util.ArrayList;
import java.util.List;

@Job(type="Test")
public class TestQuartzJob extends AbstractJob<String> {

	@Override
	public List<String> load() {
		List<String> strs = new ArrayList<String>();
		for(int i=0;i<5;i++){
			strs.add("Valore_"+i);
		}
		return strs;
	}

	@Override
	public void execute(String obj) {
		/*
		try {
			Thread.sleep(1000);			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/	
		System.out.println("execute: " + obj);
	}

	@Override
	public void end(String obj) {
		/*
		try {
			Thread.sleep(1000);			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/	
		System.out.println("end: " + obj);
	}	
	
}