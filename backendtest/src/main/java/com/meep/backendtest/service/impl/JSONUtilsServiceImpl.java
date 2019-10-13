package com.meep.backendtest.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meep.backendtest.service.JSONUtilsService;

import org.springframework.stereotype.Service;

@Service
public class JSONUtilsServiceImpl implements JSONUtilsService {

    private final Logger log = LoggerFactory.getLogger(JSONUtilsServiceImpl.class);

    // Compare two object
    public String compareJSON(String jsonRemote, String jsonSave) throws  IOException {
        ObjectMapper mapper = new ObjectMapper();

        List<MeepNode> lstMeepNodeRemote = mapper.readValue(jsonRemote, new TypeReference<List<MeepNode>>() {});
        List<MeepNode> lstMeepNodeSave = mapper.readValue(jsonSave, new TypeReference<List<MeepNode>>() {});

        for (int index=0; index<lstMeepNodeSave.size();index++) {
            MeepNode meepNode = lstMeepNodeSave.get(index);
            Optional<MeepNode> result = lstMeepNodeRemote.stream()
                .filter(item -> item.getId().trim().compareTo(meepNode.getId())==0).findFirst();
            if (result.isPresent()) {
                if (lstMeepNodeSave.get(index).getX() == result.get().getX() &&
                    lstMeepNodeSave.get(index).getY() == result.get().getY()) {
                    lstMeepNodeSave.get(index).setX(result.get().getX());
                    lstMeepNodeSave.get(index).setY(result.get().getY());
                    lstMeepNodeSave.get(index).setStatus(MeepNodeStatus.EQUAL);
                    lstMeepNodeSave.set(index, lstMeepNodeSave.get(index));
                } else {
                    lstMeepNodeSave.get(index).setX(result.get().getX());
                    lstMeepNodeSave.get(index).setY(result.get().getY());
                    lstMeepNodeSave.get(index).setStatus(MeepNodeStatus.CHANGED);
                    lstMeepNodeSave.set(index, lstMeepNodeSave.get(index));
                }
            } else {
                lstMeepNodeSave.get(index).setStatus(MeepNodeStatus.REMOVED);
            }
        }
            
        lstMeepNodeRemote.retainAll(lstMeepNodeSave);
        
        for (int index=0; index<lstMeepNodeRemote.size(); index++) {
                lstMeepNodeRemote.get(index).setStatus(MeepNodeStatus.NEW);
                lstMeepNodeRemote.set(index, lstMeepNodeRemote.get(index));
            };
        lstMeepNodeSave.addAll(lstMeepNodeRemote);
        
        return mapper.writeValueAsString(lstMeepNodeSave);
    }
}

class MeepNode {
    private String id;
    private String name;
    private double x;
    private double y;
    private String licencePlate;
    private int range;
    private int batteryLevel;
    private int seats;
    private String model;
    private String resourceImageId;
    private float pricePerMinuteParking;
    private float pricePerMinuteDriving;
    private boolean realTimeData;
    private String engineType;
    private String resourceType;
    private int companyZoneId;
    private int helmets;

    private MeepNodeStatus status; 

    /**
     * @return the batteryLevel
     */
    public int getBatteryLevel() {
        return batteryLevel;
    }
    /**
     * @param batteryLevel the batteryLevel to set
     */
    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
    /**
     * @return the companyZoneId
     */
    public int getCompanyZoneId() {
        return companyZoneId;
    }
    /**
     * @param companyZoneId the companyZoneId to set
     */
    public void setCompanyZoneId(int companyZoneId) {
        this.companyZoneId = companyZoneId;
    }
    /**
     * @return the engineType
     */
    public String getEngineType() {
        return engineType;
    }
    /**
     * @param engineType the engineType to set
     */
    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the licencePlate
     */
    public String getLicencePlate() {
        return licencePlate;
    }
    /**
     * @param licencePlate the licencePlate to set
     */
    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }
    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }
    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the pricePerMinuteDriving
     */
    public float getPricePerMinuteDriving() {
        return pricePerMinuteDriving;
    }
    /**
     * @param pricePerMinuteDriving the pricePerMinuteDriving to set
     */
    public void setPricePerMinuteDriving(float pricePerMinuteDriving) {
        this.pricePerMinuteDriving = pricePerMinuteDriving;
    }
    /**
     * @return the pricePerMinuteParking
     */
    public float getPricePerMinuteParking() {
        return pricePerMinuteParking;
    }
    /**
     * @param pricePerMinuteParking the pricePerMinuteParking to set
     */
    public void setPricePerMinuteParking(float pricePerMinuteParking) {
        this.pricePerMinuteParking = pricePerMinuteParking;
    }
    /**
     * @return the range
     */
    public int getRange() {
        return range;
    }
    /**
     * @param range the range to set
     */
    public void setRange(int range) {
        this.range = range;
    }
    /**
     * @return the resourceImageId
     */
    public String getResourceImageId() {
        return resourceImageId;
    }
    /**
     * @param realTimeData the realTimeData to set
     */
    public void setRealTimeData(boolean realTimeData) {
        this.realTimeData = realTimeData;
    }

    /**
     * @return the resourceType
     */
    public boolean getRealTimeData() {
        return realTimeData;
    }
    /**
     * @return the resourceType
     */
    public String getResourceType() {
        return resourceType;
    }
    /**
     * @param resourceImageId the resourceImageId to set
     */
    public void setResourceImageId(String resourceImageId) {
        this.resourceImageId = resourceImageId;
    }
    public int getSeats() {
        return seats;
    }
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    public double getX() {
        return x;
    }
    public void setSeats(int seats) {
        this.seats = seats;
    }
    public double getY() {
        return y;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public int getHelmets() {
        return helmets;
    }
    public void setHelmets(int helmets) {
        this.helmets = helmets;
    }
    public MeepNodeStatus getStatus() {
        return status;
    }
    public void setStatus(MeepNodeStatus status) {
        this.status = status;
    }
}

enum MeepNodeStatus {
    NEW, CHANGED, REMOVED, EQUAL
}
