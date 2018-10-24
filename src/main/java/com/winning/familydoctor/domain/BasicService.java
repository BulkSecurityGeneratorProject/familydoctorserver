package com.winning.familydoctor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.winning.familydoctor.domain.enumeration.ServiceType;

import com.winning.familydoctor.domain.enumeration.AppraiseType;

import com.winning.familydoctor.domain.enumeration.Status;

/**
 * A BasicService.
 */
@Entity
@Table(name = "basic_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BasicService implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "name", length = 20, nullable = false)
    private String name;

    @Size(min = 3, max = 10)
    @Column(name = "search_no", length = 10)
    private String searchNo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private ServiceType serviceType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "appraise_type", nullable = false)
    private AppraiseType appraiseType;

    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "appraise_value")
    private Integer appraiseValue;

    @DecimalMin(value = "0")
    @Column(name = "reference_price")
    private Double referencePrice;

    @DecimalMin(value = "0")
    @Column(name = "subsidy_price")
    private Double subsidyPrice;

    @Min(value = 0)
    @Column(name = "service_count")
    private Integer serviceCount;

    @Column(name = "can_appointment")
    private Boolean canAppointment;

    @Min(value = 0)
    @Column(name = "jhi_order")
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Size(max = 200)
    @Column(name = "jhi_desc", length = 200)
    private String desc;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BasicService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchNo() {
        return searchNo;
    }

    public BasicService searchNo(String searchNo) {
        this.searchNo = searchNo;
        return this;
    }

    public void setSearchNo(String searchNo) {
        this.searchNo = searchNo;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public BasicService serviceType(ServiceType serviceType) {
        this.serviceType = serviceType;
        return this;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public AppraiseType getAppraiseType() {
        return appraiseType;
    }

    public BasicService appraiseType(AppraiseType appraiseType) {
        this.appraiseType = appraiseType;
        return this;
    }

    public void setAppraiseType(AppraiseType appraiseType) {
        this.appraiseType = appraiseType;
    }

    public Integer getAppraiseValue() {
        return appraiseValue;
    }

    public BasicService appraiseValue(Integer appraiseValue) {
        this.appraiseValue = appraiseValue;
        return this;
    }

    public void setAppraiseValue(Integer appraiseValue) {
        this.appraiseValue = appraiseValue;
    }

    public Double getReferencePrice() {
        return referencePrice;
    }

    public BasicService referencePrice(Double referencePrice) {
        this.referencePrice = referencePrice;
        return this;
    }

    public void setReferencePrice(Double referencePrice) {
        this.referencePrice = referencePrice;
    }

    public Double getSubsidyPrice() {
        return subsidyPrice;
    }

    public BasicService subsidyPrice(Double subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
        return this;
    }

    public void setSubsidyPrice(Double subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
    }

    public Integer getServiceCount() {
        return serviceCount;
    }

    public BasicService serviceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
        return this;
    }

    public void setServiceCount(Integer serviceCount) {
        this.serviceCount = serviceCount;
    }

    public Boolean isCanAppointment() {
        return canAppointment;
    }

    public BasicService canAppointment(Boolean canAppointment) {
        this.canAppointment = canAppointment;
        return this;
    }

    public void setCanAppointment(Boolean canAppointment) {
        this.canAppointment = canAppointment;
    }

    public Integer getOrder() {
        return order;
    }

    public BasicService order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Status getStatus() {
        return status;
    }

    public BasicService status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public BasicService desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BasicService basicService = (BasicService) o;
        if (basicService.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), basicService.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BasicService{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", searchNo='" + getSearchNo() + "'" +
            ", serviceType='" + getServiceType() + "'" +
            ", appraiseType='" + getAppraiseType() + "'" +
            ", appraiseValue=" + getAppraiseValue() +
            ", referencePrice=" + getReferencePrice() +
            ", subsidyPrice=" + getSubsidyPrice() +
            ", serviceCount=" + getServiceCount() +
            ", canAppointment='" + isCanAppointment() + "'" +
            ", order=" + getOrder() +
            ", status='" + getStatus() + "'" +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
