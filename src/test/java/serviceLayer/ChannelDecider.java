package serviceLayer;

import io.cucumber.datatable.DataTable;
import stepDefinition.WebDriverProvider;

import java.util.List;
import java.util.Map;

public class ChannelDecider extends BaseService {

    public ChannelDecider() {
        super(WebDriverProvider.getChromeDriver());
    }
    public String findPlatform (DataTable dataTable){

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> mapValues = data.get(0);
        String channel = mapValues.get("channel");

        return  channel;
    }


}
