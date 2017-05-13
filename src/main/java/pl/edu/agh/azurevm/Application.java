package pl.edu.agh.azurevm;

import com.microsoft.azure.eventprocessorhost.EventProcessorHost;
import com.microsoft.azure.eventprocessorhost.EventProcessorOptions;

import java.util.Map;

public class Application {

    public static void main(String[] args) throws Exception {

        listEnvVariables();

        final EventProcessorHost host = EventProcessorHostFactory.create();
        final EventProcessorOptions options = new EventProcessorOptions();
        options.setExceptionNotification(new ErrorNotificationHandler());
        host.registerEventProcessor(EventProcessor.class, options).get();
    }

    private static void listEnvVariables() {
        for (Map.Entry<String, String> envVariable : System.getenv().entrySet()) {
            System.out.println(String.format("%s = %s", envVariable.getKey(), envVariable.getValue()));
        }
    }

}
