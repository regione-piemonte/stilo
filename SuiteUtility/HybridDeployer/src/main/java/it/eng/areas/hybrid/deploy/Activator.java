package it.eng.areas.hybrid.deploy;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.http.HttpService;
import org.osgi.util.tracker.ServiceTracker;

import it.eng.areas.hybrid.deploy.servlet.HybridServlet;

public class Activator implements BundleActivator {

	@SuppressWarnings("rawtypes")
	private ServiceTracker httpTracker;
	private ServiceRegistration<HybridConfig> hybridConfigService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void start(BundleContext context) throws Exception {
		httpTracker = new ServiceTracker(context, HttpService.class.getName(), null) {

			public void removedService(ServiceReference reference, Object service) {
				try {
					((HttpService) service).unregister("/hybrid");
				} catch (IllegalArgumentException exception) {
					// Ignore; servlet registration probably failed earlier on...
				}
			}

			public Object addingService(ServiceReference reference) {
				HttpService httpService = (HttpService) this.context.getService(reference);
				try {
					httpService.registerServlet("/hybrid", new HybridServlet(), null, null);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
				return httpService;
			}
		};
		// start tracking all HTTP services...
		httpTracker.open();

		hybridConfigService = context.registerService(HybridConfig.class, HybridConfig.getInstance(), new java.util.Hashtable<String, Object>());
	}

	@Override
	public void stop(BundleContext arg0) throws Exception {
		httpTracker.close();
		hybridConfigService.unregister();
	}

}
