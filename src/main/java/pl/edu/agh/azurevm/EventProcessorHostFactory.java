package pl.edu.agh.azurevm;

import com.microsoft.azure.eventprocessorhost.EventProcessorHost;
import com.microsoft.azure.servicebus.ConnectionStringBuilder;

class EventProcessorHostFactory {

    static EventProcessorHost create() throws Exception {
        return new EventProcessorHost(Configuration.getString("eventhub.eventhubname"), Configuration.getString("eventhub.consumergroupname"), eventHubConnectionString(), storageConnectionString());
    }

    private static String storageConnectionString() {
        return "DefaultEndpointsProtocol=https;AccountName=" + Configuration.getString("storageaccount.name") + ";AccountKey=" + Configuration.getString("storageaccount.key");
    }

    private static String eventHubConnectionString() {
        return new ConnectionStringBuilder(
                Configuration.getString("eventhub.namespacename"),
                Configuration.getString("eventhub.eventhubname"),
                Configuration.getString("eventhub.saskeyname"),
                Configuration.getString("eventhub.saskey")
        ).toString();
    }

}
