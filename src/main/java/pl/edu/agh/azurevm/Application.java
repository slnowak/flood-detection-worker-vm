package pl.edu.agh.azurevm;

import com.microsoft.azure.eventprocessorhost.EventProcessorHost;
import com.microsoft.azure.eventprocessorhost.EventProcessorOptions;

public class Application {

    public static void main(String[] args) throws Exception {
        final EventProcessorHost host = EventProcessorHostFactory.create();
        final EventProcessorOptions options = new EventProcessorOptions();
        options.setExceptionNotification(new ErrorNotificationHandler());
        host.registerEventProcessor(EventProcessor.class, options).get();
    }

}
