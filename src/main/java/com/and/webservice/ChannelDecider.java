package serviceLayer;

import io.cucumber.datatable.DataTable;
import stepDefinition.WebDriverProvider;
import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

public class ChannelDecider extends BaseService {

    private static final Logger logger = LogManager.getLogger(ChannelDecider.class);

    public ChannelDecider() {
        super(WebDriverProvider.getChromeDriver());
    }

    public String findPlatform(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            Map<String, String> mapValues = data.get(0);
            String channel = mapValues.get("channel");
            logger.info(ENDED + getCurrentMethodName());
            return channel;
        } catch (Exception e) {
            logger.error("Error failed to select channel: {}", e.getMessage(), e);
            return null;
        }
    }
}
