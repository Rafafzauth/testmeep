package com.meep.demo2meep.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity that we use as cache
 */
@Entity
@Table(name = "MeepPosition")
public class MeepPosition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lower_left_lat_lon")
    private String lowerLeftLatLon;

    @Column(name = "upper_right_lat_lon")
    private String upperRightLatLon;

    @Column(name = "company_zone_ids")
    private String companyZoneIds;

    @Column(name = "result", length=300000)
    private String result;

    @Column(name = "date_update")
    private Timestamp dateUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLowerLeftLatLon() {
        return lowerLeftLatLon;
    }

    public MeepPosition lowerLeftLatLon(String lowerLeftLatLon) {
        this.lowerLeftLatLon = lowerLeftLatLon;
        return this;
    }

    public void setLowerLeftLatLon(String lowerLeftLatLon) {
        this.lowerLeftLatLon = lowerLeftLatLon;
    }

    public String getUpperRightLatLon() {
        return upperRightLatLon;
    }

    public MeepPosition upperRightLatLon(String upperRightLatLon) {
        this.upperRightLatLon = upperRightLatLon;
        return this;
    }

    public void setUpperRightLatLon(String upperRightLatLon) {
        this.upperRightLatLon = upperRightLatLon;
    }

    public String getCompanyZoneIds() {
        return companyZoneIds;
    }

    public MeepPosition companyZoneIds(String companyZoneIds) {
        this.companyZoneIds = companyZoneIds;
        return this;
    }

    public void setCompanyZoneIds(String companyZoneIds) {
        this.companyZoneIds = companyZoneIds;
    }

    public String getResult() {
        return result;
    }

    public MeepPosition result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Timestamp getDateUpdate() {
        return dateUpdate;
    }
    
    public void setDateUpdate(Timestamp dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeepPosition)) {
            return false;
        }
        return id != null && id.equals(((MeepPosition) o).id);
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + getId() +
            ", lowerLeftLatLon='" + getLowerLeftLatLon() + "'" +
            ", upperRightLatLon='" + getUpperRightLatLon() + "'" +
            ", companyZoneIds='" + getCompanyZoneIds() + "'" +
            ", result='" + getResult() + "'" +
            ", dateUpdate='" + getDateUpdate() + "'" +
            "}";
    }
}
