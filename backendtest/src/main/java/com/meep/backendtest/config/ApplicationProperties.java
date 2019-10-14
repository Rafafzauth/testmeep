package com.meep.backendtest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Backendtest.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String meepurl;
    private String lowerLeftLatLon;
    private String upperRightLatLon;
    private String companyZoneIds;

    private long connectionTimeout;
    private long readTimeout;

    public String getMeepurl() {
        return meepurl;
    }

    public void setMeepurl(String meepurl) {
        this.meepurl = meepurl;
    }

    public String getCompanyZoneIds() {
        return companyZoneIds;
    }

    public void setCompanyZoneIds(String companyZoneIds) {
        this.companyZoneIds = companyZoneIds;
    }

    public String getLowerLeftLatLon() {
        return lowerLeftLatLon;
    }

    public void setLowerLeftLatLon(String lowerLeftLatLon) {
        this.lowerLeftLatLon = lowerLeftLatLon;
    }

    public String getUpperRightLatLon() {
        return upperRightLatLon;
    }

    public void setUpperRightLatLon(String upperRightLatLon) {
        this.upperRightLatLon = upperRightLatLon;
    }

    public long getConnectionTimeout() {
        return connectionTimeout;
    }
 
    public void setConnectionTimeout(long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
 
    public long getReadTimeout() {
        return readTimeout;
    }
 
    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

}
