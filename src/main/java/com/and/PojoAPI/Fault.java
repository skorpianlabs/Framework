package com.and.PojoAPI;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Fault {

    @JsonProperty("AircraftId")
    private int aircraftId;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("FailureTypeCode")
    private String failureTypeCode;

    @JsonProperty("FaultSeverityCode")
    private String faultSeverityCode;

    @JsonProperty("FaultSourceCode")
    private String faultSourceCode;

    @JsonProperty("FoundOnId")
    private int foundOnId;

    @JsonProperty("LogbookReference")
    private String logbookReference;

    @JsonProperty("LogbookTypeCode")
    private String logbookTypeCode;

    @JsonProperty("PhaseOfFlightCode")
    private String phaseOfFlightCode;

    @JsonProperty("SubSystemId")
    private int subSystemId;

    // Default constructor
    public Fault() {
    }

    // Getter and setter methods
    public int getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(int aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFailureTypeCode() {
        return failureTypeCode;
    }

    public void setFailureTypeCode(String failureTypeCode) {
        this.failureTypeCode = failureTypeCode;
    }

    public String getFaultSeverityCode() {
        return faultSeverityCode;
    }

    public void setFaultSeverityCode(String faultSeverityCode) {
        this.faultSeverityCode = faultSeverityCode;
    }

    public String getFaultSourceCode() {
        return faultSourceCode;
    }

    public void setFaultSourceCode(String faultSourceCode) {
        this.faultSourceCode = faultSourceCode;
    }

    public int getFoundOnId() {
        return foundOnId;
    }

    public void setFoundOnId(int foundOnId) {
        this.foundOnId = foundOnId;
    }

    public String getLogbookReference() {
        return logbookReference;
    }

    public void setLogbookReference(String logbookReference) {
        this.logbookReference = logbookReference;
    }

    public String getLogbookTypeCode() {
        return logbookTypeCode;
    }

    public void setLogbookTypeCode(String logbookTypeCode) {
        this.logbookTypeCode = logbookTypeCode;
    }

    public String getPhaseOfFlightCode() {
        return phaseOfFlightCode;
    }

    public void setPhaseOfFlightCode(String phaseOfFlightCode) {
        this.phaseOfFlightCode = phaseOfFlightCode;
    }

    public int getSubSystemId() {
        return subSystemId;
    }

    public void setSubSystemId(int subSystemId) {
        this.subSystemId = subSystemId;
    }

    // Optional: Override toString for easy logging
    @Override
    public String toString() {
        return "Fault{" +
                "aircraftId=" + aircraftId +
                ", description='" + description + '\'' +
                ", failureTypeCode='" + failureTypeCode + '\'' +
                ", faultSeverityCode='" + faultSeverityCode + '\'' +
                ", faultSourceCode='" + faultSourceCode + '\'' +
                ", foundOnId=" + foundOnId +
                ", logbookReference='" + logbookReference + '\'' +
                ", logbookTypeCode='" + logbookTypeCode + '\'' +
                ", phaseOfFlightCode='" + phaseOfFlightCode + '\'' +
                ", subSystemId=" + subSystemId +
                '}';
    }
}
