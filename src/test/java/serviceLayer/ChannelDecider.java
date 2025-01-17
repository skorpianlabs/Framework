package serviceLayer;

import io.cucumber.datatable.DataTable;
import stepDefinition.WebDriverProvider;

import java.util.List;
import java.util.Map;

public class ChannelDecider extends BaseService {

    public ChannelDecider() {
        super(WebDriverProvider.getChromeDriver());
    }
    public String findPlatform(DataTable dataTable) {
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            Map<String, String> mapValues = data.get(0);
            String channel = mapValues.get("channel");

            return channel;
        } catch (Exception e) {
            System.out.println("Error Failed to select channel : " + e.getMessage());
            return null;  // You can return null or handle the error as needed
        }
    }
}
