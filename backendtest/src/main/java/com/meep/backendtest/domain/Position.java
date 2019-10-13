package com.meep.backendtest.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * A Position.
 */
@Entity
@Table(name = "position")
public class Position implements Serializable {

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

    @Column(name = "result")
    private String result;

    @Column(name = "jhi_update")
    private LocalDate update;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLowerLeftLatLon() {
        return lowerLeftLatLon;
    }

    public Position lowerLeftLatLon(String lowerLeftLatLon) {
        this.lowerLeftLatLon = lowerLeftLatLon;
        return this;
    }

    public void setLowerLeftLatLon(String lowerLeftLatLon) {
        this.lowerLeftLatLon = lowerLeftLatLon;
    }

    public String getUpperRightLatLon() {
        return upperRightLatLon;
    }

    public Position upperRightLatLon(String upperRightLatLon) {
        this.upperRightLatLon = upperRightLatLon;
        return this;
    }

    public void setUpperRightLatLon(String upperRightLatLon) {
        this.upperRightLatLon = upperRightLatLon;
    }

    public String getCompanyZoneIds() {
        return companyZoneIds;
    }

    public Position companyZoneIds(String companyZoneIds) {
        this.companyZoneIds = companyZoneIds;
        return this;
    }

    public void setCompanyZoneIds(String companyZoneIds) {
        this.companyZoneIds = companyZoneIds;
    }

    public String getResult() {
        return result;
    }

    public Position result(String result) {
        this.result = result;
        return this;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDate getUpdate() {
        return update;
    }

    public Position update(LocalDate update) {
        this.update = update;
        return this;
    }

    public void setUpdate(LocalDate update) {
        this.update = update;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position)) {
            return false;
        }
        return id != null && id.equals(((Position) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Position{" +
            "id=" + getId() +
            ", lowerLeftLatLon='" + getLowerLeftLatLon() + "'" +
            ", upperRightLatLon='" + getUpperRightLatLon() + "'" +
            ", companyZoneIds='" + getCompanyZoneIds() + "'" +
            ", result='" + getResult() + "'" +
            ", update='" + getUpdate() + "'" +
            "}";
    }
}
