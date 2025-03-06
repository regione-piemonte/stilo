package it.eng.areas.hybrid.module.util;

import org.osgi.framework.BundleActivator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import it.eng.areas.hybrid.module.IClientModuleManager;

public abstract class HybridActivator implements BundleActivator {
	
	private ServiceTracker<IClientModuleManager,?> moduleManagerTracker;
	protected String bundleName;
	protected String bundleVersion;

	@Override
	public void start(final BundleContext context) throws Exception {
		bundleName = context.getBundle().getSymbolicName();
		bundleVersion = context.getBundle().getVersion().toString();
		
		moduleManagerTracker = new ServiceTracker<IClientModuleManager,IClientModuleManager>(context, IClientModuleManager.class.getName(), null) {
			@Override
			public IClientModuleManager addingService(ServiceReference<IClientModuleManager> reference) {
				IClientModuleManager moduleManager = context.getService(reference);
				addModuleManager(moduleManager);
				
				return moduleManager;
			}
			
			@Override
			public void removedService(ServiceReference<IClientModuleManager> reference, IClientModuleManager service) {
				removeModuleManager();
				super.removedService(reference, service);
			}
			
			
		};
		moduleManagerTracker.open();
	}
	
	public String getBundleName() {
		return bundleName;
	}
	
	public String getBundleVersion() {
		return bundleVersion;
	}
	
	protected abstract void addModuleManager(IClientModuleManager moduleManager);
	
	protected void removeModuleManager() {
		//void
	}
	

	@Override
	public void stop(BundleContext context) throws Exception {
		moduleManagerTracker.close();
	}

}
