package com.and.webservice;

import com.and.utility.DBCallExecuter;
import io.cucumber.datatable.DataTable;
import com.and.utility.DriverProvider;
import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class RaiseFaultWebService extends BaseService {

    String description;
    WebServices webServices = new WebServices();

    public RaiseFaultWebService() {
        super(DriverProvider.getChromeDriver());
        setupPageFactory();
    }

    public void raiseAFaultFromATurn(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            webServices.routeToMyAircraftTurnsPage(dataTable);
            webPageObjectFactory.getRaiseFaultService().clickButtonRaiseFault();
            webPageObjectFactory.getRaiseFaultService().enterSystem("INTRODUCTION");
            webPageObjectFactory.getRaiseFaultService().enterFaultSource("AUTH");
            webPageObjectFactory.getRaiseFaultService().enterLogbookType("");
            webPageObjectFactory.getRaiseFaultService().enterLogbookReference("");
            webPageObjectFactory.getRaiseFaultService().enterDescription("DESC01");
            webPageObjectFactory.getRaiseFaultService().enterPhaseOfFlight("ATB");
            webPageObjectFactory.getRaiseFaultService().enterFaultType("A");
            webPageObjectFactory.getRaiseFaultService().enterFaultSeverity("MEL");
            webPageObjectFactory.getRaiseFaultService().enterFaultCode("208E");
            webPageObjectFactory.getRaiseFaultService().clickButtonOK();
            //ResultSet rx  = webPageObjectFactory.getDbCallExecuterService().executeQuery("");
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error("Error raising fault from a Turn: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public void raiseAFaultFromLobby(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            webServices.routeToTechnicianLobbyPage(dataTable);
            webPageObjectFactory.getTechnicianLobbyPagePageService().clickRaiseFault();
            webPageObjectFactory.getRaiseFaultService().enterAircraft("IFSAD-1524 (737-NG)");
            webPageObjectFactory.getRaiseFaultService().enterSystem("INTRODUCTION");
            webPageObjectFactory.getRaiseFaultService().enterFaultSource("AUTH");
            webPageObjectFactory.getRaiseFaultService().enterLogbookType("");
            webPageObjectFactory.getRaiseFaultService().enterLogbookReference("");
            webPageObjectFactory.getRaiseFaultService().enterDescription("DESC01");
            webPageObjectFactory.getRaiseFaultService().enterFoundDuringFlight("P5-1524");
            webPageObjectFactory.getRaiseFaultService().enterPhaseOfFlight("ATB");
            webPageObjectFactory.getRaiseFaultService().enterFaultType("A");
            webPageObjectFactory.getRaiseFaultService().enterFaultSeverity("MEL");
            webPageObjectFactory.getRaiseFaultService().enterFaultCode("208E");
            webPageObjectFactory.getRaiseFaultService().clickButtonOK();
            Thread.sleep(6000);
        } catch (Exception e) {
            logger.error("Error raising fault from Lobby: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public void raiseAFault(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            for (Map<String, String> row : data) {
                String page = row.get("page");

                if (page.equals("turn")) {
                    raiseAFaultFromATurn(dataTable);
                }
                if (page.equals("lobby")) {
                    raiseAFaultFromLobby(dataTable);
                }
            }
        } catch (Exception e) {
            logger.error("Error raising fault from selector: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public String assertRaisedFaultFromTurn() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            webServices.routeToTaskDetailsPage();
            webServices.putTaskDetailsIntoAMap();
            description = webServices.getTaskDetailsFromMap();
            logger.info(ENDED + getCurrentMethodName());
            return description;
        } catch (Exception e) {
            logger.error("Error asserting the raised fault: {}", e.getMessage(), e);
            return null;
        }

    }
    public String navigateFaultdDetailsPageFromRasiseFault(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            webPageObjectFactory.getRaiseFaultService().clickButtonFaultSearch();
            webPageObjectFactory.getfaultSearchPageService().clickFilterIcon();
            webPageObjectFactory.getfaultSearchPageService().clickFaultDescriptionMenuItem("Fault Description");
            webPageObjectFactory.getfaultSearchPageService().clickThreeDotButton();
            webPageObjectFactory.getfaultSearchPageService().clickDetailsButton();
            webPageObjectFactory.getFaultDetailsPageService().putDescriptionValueToMap();
            description = webPageObjectFactory.getFaultDetailsPageService().getDescriptionValueFromMap();
            logger.info(ENDED + getCurrentMethodName());
            return  description;

        } catch (Exception e) {
            logger.error("Error asserting the raised fault: {}", e.getMessage(), e);
            return null;
        }

    }
    public String navigateFaultdDetailsPageFromLobby(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            webServices.routeToTechnicianLobbyPage(dataTable);
            webPageObjectFactory.getTechnicianLobbyPagePageService().faultSearchTitle();
            webPageObjectFactory.getfaultSearchPageService().clickFilterIcon();
            webPageObjectFactory.getfaultSearchPageService().clickFaultDescriptionMenuItem("Fault Description");
            webPageObjectFactory.getfaultSearchPageService().clickThreeDotButton();
            webPageObjectFactory.getfaultSearchPageService().clickDetailsButton();
            webPageObjectFactory.getFaultDetailsPageService().putDescriptionValueToMap();
            description = webPageObjectFactory.getFaultDetailsPageService().getDescriptionValueFromMap();
            logger.info(ENDED + getCurrentMethodName());
            return  description;

        } catch (Exception e) {
            logger.error("Error asserting the raised fault: {}", e.getMessage(), e);
            return null;
        }

    }
    public String routeFaultDetails(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            for (Map<String, String> row : data) {
                String page = row.get("page");

                if (page.equals("fault")) {
                    description = navigateFaultdDetailsPageFromRasiseFault(dataTable);
                }
                if (page.equals("lobby")) {

                    description = navigateFaultdDetailsPageFromLobby(dataTable);
                }
            }
        } catch (Exception e) {
            logger.error("Error raising fault from selector: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
        return description;
    }
}
