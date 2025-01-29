package com.and.nativeService;

import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;

import java.util.List;
import java.util.Map;

import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

public class RaiseFaultNativeService extends NativeBaseService {

    @Inject
    NativeServices nativeServices ;


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

    private void raiseAFaultFromLobby(DataTable dataTable) {

        logger.info(STARTED + getCurrentMethodName());
        try {
            nativePageObjectFactory.getNativeTechnicianLobbyPagePageService().clickRaiseFaultLobbyIcon();
            nativePageObjectFactory.getNativeRaiseFaultService().enterAircraft("IFSAD-1524 (737-NG)");
            nativePageObjectFactory.getNativeRaiseFaultService().enterSystem("INTRODUCTION");
            nativePageObjectFactory.getNativeRaiseFaultService().enterFaultSource("AUTH");
            nativePageObjectFactory.getNativeRaiseFaultService().enterLogbookType("");
            nativePageObjectFactory.getNativeRaiseFaultService().enterLogbookReference("");
            nativePageObjectFactory.getNativeRaiseFaultService().enterDescription("DESC01");
            nativePageObjectFactory.getNativeRaiseFaultService().enterFoundDuringFlight("P5-1524");
            nativePageObjectFactory.getNativeRaiseFaultService().enterFaultSeverity("MEL");

           /* nativePageObjectFactory.getNativeRaiseFaultService().enterFaultCode("208E");
            nativePageObjectFactory.getNativeRaiseFaultService().clickButtonOK();
            nativePageObjectFactory.getNativeRaiseFaultService().enterPhaseOfFlight("ATB");
            nativePageObjectFactory.getNativeRaiseFaultService().enterFaultType("A");*/
        } catch (Exception e) {
            logger.error("Error raising fault from Lobby: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    private void raiseAFaultFromATurn(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            nativeServices.routeToMyAircraftTurnsPage(dataTable);
            nativePageObjectFactory.getNativeRaiseFaultService().clickRaiseFault();
            nativePageObjectFactory.getNativeRaiseFaultService().enterSystem("INTRODUCTION");
            nativePageObjectFactory.getNativeRaiseFaultService().enterFaultSource("AUTH");
            nativePageObjectFactory.getNativeRaiseFaultService().enterLogbookType("");
            nativePageObjectFactory.getNativeRaiseFaultService().enterLogbookReference("");
            nativePageObjectFactory.getNativeRaiseFaultService().enterDescription("DESC01");
            nativePageObjectFactory.getNativeRaiseFaultService().enterFaultSeverity("MEL");
            //nativePageObjectFactory.getNativeRaiseFaultService().enterFaultCode("208E");
            //nativePageObjectFactory.getNativeRaiseFaultService().clickButtonOK();
            //nativePageObjectFactory.getNativeRaiseFaultService().enterPhaseOfFlight("ATB");
            //nativePageObjectFactory.getNativeRaiseFaultService().enterFaultType("A");
            Thread.sleep(2000);
        } catch (Exception e) {
            logger.error("Error raising fault from a Turn: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }


}
