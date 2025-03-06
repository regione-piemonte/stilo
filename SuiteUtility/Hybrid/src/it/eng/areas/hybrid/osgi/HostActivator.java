package it.eng.areas.hybrid.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import it.eng.areas.hybrid.module.IClientModuleManager;

public class HostActivator implements BundleActivator
{
	private IClientModuleManager moduleManager;
    private ServiceRegistration<?> reg = null;

    public HostActivator(IClientModuleManager moduleManager) {
    	this.moduleManager = moduleManager;
    }

    public void start(BundleContext context) {
        //Pubblico i servizi host
        reg = context.registerService(IClientModuleManager.class.getName(), moduleManager, null);
    }

    public void stop(BundleContext context) {
        reg.unregister();
    }
}