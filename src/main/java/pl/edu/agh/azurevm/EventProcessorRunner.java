package pl.edu.agh.azurevm;

import com.microsoft.azure.eventprocessorhost.EventProcessorHost;
import com.microsoft.azure.eventprocessorhost.EventProcessorOptions;
import com.microsoft.azure.servicebus.ConnectionStringBuilder;

import java.util.concurrent.ExecutionException;

public class EventProcessorRunner {

    public static void run() {
        final String consumerGroupName = Configuration.getString("eventhub.consumergroupname");
        final String namespaceName = Configuration.getString("eventhub.namespacename");
        final String eventHubName = Configuration.getString("eventhub.eventhubname");
        final String sasKeyName = Configuration.getString("eventhub.saskeyname");
        final String sasKey = Configuration.getString("eventhub.saskey");

        final String storageAccountName = Configuration.getString("storageaccount.name");
        final String storageAccountKey = Configuration.getString("storageaccount.key");
        final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=" + storageAccountName + ";AccountKey=" + storageAccountKey;

        ConnectionStringBuilder eventHubConnectionString = new ConnectionStringBuilder(namespaceName, eventHubName, sasKeyName, sasKey);

        EventProcessorHost host = new EventProcessorHost(eventHubName, consumerGroupName, eventHubConnectionString.toString(), storageConnectionString);

        System.out.println("Registering host named " + host.getHostName());
        EventProcessorOptions options = new EventProcessorOptions();
        options.setExceptionNotification(new ErrorNotificationHandler());
        try
        {
            host.registerEventProcessor(EventProcessor.class, options).get();
        }
        catch (Exception e)
        {
            System.out.print("Failure while registering: ");
            if (e instanceof ExecutionException)
            {
                Throwable inner = e.getCause();
                System.out.println(inner.toString());
            }
            else
            {
                System.out.println(e.toString());
            }
        }

        System.out.println("Press enter to stop");
        try
        {
            System.in.read();
            host.unregisterEventProcessor();

            System.out.println("Calling forceExecutorShutdown");
            EventProcessorHost.forceExecutorShutdown(120);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        System.out.println("End of sample");
    }

}
