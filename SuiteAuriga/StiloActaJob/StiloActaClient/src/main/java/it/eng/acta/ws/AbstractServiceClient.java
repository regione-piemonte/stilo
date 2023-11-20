/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */

import it.eng.acta.ws.handler.AbstractLogHandler;
import it.eng.stilo.util.file.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractServiceClient {

    private static final String RESOURCE_FILE_URL = "/urls.properties";
    private static final String RESOURCE_FILE_HANDLER = "/handler.properties";
    protected final Logger logger = LoggerFactory.getLogger(AbstractServiceClient.class);

    protected AbstractServiceClient(final String serviceName) {
        printInfo(serviceName, "create");
    }

    public static void main(String[] args) {
        System.out.println(AbstractServiceClient.class.getPackage().getName());
    }

    protected abstract void initService();

    protected URL getServiceURL(final String serviceName) {
        printInfo(serviceName, "get-service-url");
        URL serviceURL = null;
        try {
            final Optional<String> resolvedURL = FileHandler.getInstance().getPropertyValue(serviceName,
                    RESOURCE_FILE_URL);
            printInfo(serviceName, "resolved-url");
            serviceURL = new URL(resolvedURL.get());
        } catch (IOException e) {
            printError(serviceName, "get-service-url", e.getMessage());
        }

        return serviceURL;
    }

    protected void printInfo(final String service, final String action) {
        logger.info(String.format("[Service:%s][Action:%s]", service, action));
    }

    protected void printError(final String service, final String action, final String message) {
        logger.error(String.format("[Service:%s][Action:%s][Error:%s]", service, action, message));
    }

    protected void printError(final String service, final String action, final Exception exception) {
        logger.error(String.format("[Service:%s][Action:%s][Error:%s]", service, action, exception.getMessage()),
                exception);
    }

    protected void setHandler(final Binding binding) {
        final List<Handler> handlerList = binding.getHandlerChain();
        final Optional<String> resolvedClassName = FileHandler.getInstance().getPropertyValue("handler-class",
                RESOURCE_FILE_HANDLER);
        try {
            final Class<AbstractLogHandler> handlerClass =
                    (Class<AbstractLogHandler>) Class.forName(resolvedClassName.get());
            handlerList.add(handlerClass.newInstance());
            binding.setHandlerChain(handlerList);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.error("[ErrorSettingHandler]", e);
        }
    }

    protected void setServiceIdentifier(final BindingProvider bindingProvider, final Map<String, Object> paramMap) {
        bindingProvider.getRequestContext().put(AbstractLogHandler.KEY_FLOW_ID,
                paramMap.get(AbstractLogHandler.KEY_FLOW_ID));
        if (paramMap.containsKey(AbstractLogHandler.KEY_DOC_REF)) {
            bindingProvider.getRequestContext().put(AbstractLogHandler.KEY_DOC_REF,
                    paramMap.get(AbstractLogHandler.KEY_DOC_REF));
        } else {
            bindingProvider.getRequestContext().remove(AbstractLogHandler.KEY_DOC_REF);
        }
        
        if (paramMap.containsKey(AbstractLogHandler.KEY_DOCALL_REF)) {
            bindingProvider.getRequestContext().put(AbstractLogHandler.KEY_DOCALL_REF,
                    paramMap.get(AbstractLogHandler.KEY_DOCALL_REF));
        } else {
            bindingProvider.getRequestContext().remove(AbstractLogHandler.KEY_DOCALL_REF);
        }
    }

}
