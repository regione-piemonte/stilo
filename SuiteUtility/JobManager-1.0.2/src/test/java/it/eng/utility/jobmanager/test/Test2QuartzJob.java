package it.eng.utility.jobmanager.test;

import it.eng.utility.jobmanager.quartz.AbstractJob;
import it.eng.utility.jobmanager.quartz.Job;

import java.util.ArrayList;
import java.util.List;

@Job(type="Test2")
public class Test2QuartzJob extends AbstractJob<String> {

	@Override
	public List<String> load() {
		List<String> strs = new ArrayList<String>();
		for(int i=0;i<20;i++){
			strs.add("Valore_"+i);
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return strs;
	}

	@Override
	public void execute(String obj) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void end(String obj) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}