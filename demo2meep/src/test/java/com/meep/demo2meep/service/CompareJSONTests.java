
package com.meep.demo2meep.service;

import java.io.IOException;

import com.meep.demo2meep.service.impl.JSONUtilsServiceImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CompareJSONTests {

    private static final String JSON_REMOTE = "[{\"id\":\"DriveNow_13673363\",\"name\":\"M-DX7790\",\"x\":-9.1462,\"y\":38.738,\"licencePlate\":\"M-DX7790\",\"range\":0,\"batteryLevel\":50,\"seats\":4,\"model\":\"BMW 1 Series\",\"resourceImageId\":\"vehicle_gen_dn_bmw_s1\",\"pricePerMinuteParking\":15.0,\"pricePerMinuteDriving\":29.0,\"realTimeData\":true,\"engineType\":\"DIESEL\",\"resourceType\":\"CAR\",\"companyZoneId\":545,\"helmets\":0,\"status\":\"EQUAL\"}]";
    private static final String JSON_SAVE = "[{\"id\":\"DriveNow_333333\",\"name\":\"M-02332\",\"x\":-9.1452,\"y\":38.736,\"licencePlate\":\"M-2342D0\",\"range\":0,\"batteryLevel\":50,\"seats\":4,\"model\":\"ACC 3 s\",\"resourceImageId\":\"vehicle_gen_dn_bmw_s1\",\"pricePerMinuteParking\":15.0,\"pricePerMinuteDriving\":29.0,\"realTimeData\":true,\"engineType\":\"DIESEL\",\"resourceType\":\"CAR\",\"companyZoneId\":545,\"helmets\":0,\"status\":\"EQUAL\"}]";

    @Autowired
    private JSONUtilsService jsonService;


    @Test
    public void nullsJSONs() throws IOException {
        jsonService.compareAndActualizeJSON(null, null);
        jsonService.compareAndActualizeJSON(null, JSON_SAVE);
        jsonService.compareAndActualizeJSON(JSON_REMOTE, null);
    }
    
    
    @Test
    public void testJSONsArrayCorrect() throws IOException {
        JSONUtilsServiceImpl jsonService = new JSONUtilsServiceImpl();
        jsonService.compareAndActualizeJSON(JSON_REMOTE, JSON_SAVE);
    }
    
    // TODO Compare two json and actualize
}