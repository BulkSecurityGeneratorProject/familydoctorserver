package com.winning.familydoctor.service.dto;

import java.io.Serializable;
import java.util.Objects;
import com.winning.familydoctor.domain.enumeration.ServiceType;
import com.winning.familydoctor.domain.enumeration.AppraiseType;
import com.winning.familydoctor.domain.enumeration.Status;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the BasicService entity. This class is used in BasicServiceResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /basic-services?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BasicServiceCriteria implements Serializable {
    /**
     * Class for filtering ServiceType
     */
    public static class ServiceTypeFilter extends Filter<ServiceType> {
    }
    /**
     * Class for filtering AppraiseType
     */
    public static class AppraiseTypeFilter extends Filter<AppraiseType> {
    }
    /**
     * Class for filtering Status
     */
    public static class StatusFilter extends Filter<Status> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter searchNo;

    private ServiceTypeFilter serviceType;

    private AppraiseTypeFilter appraiseType;

    private IntegerFilter appraiseValue;

    private DoubleFilter referencePrice;

    private DoubleFilter subsidyPrice;

    private IntegerFilter serviceCount;

    private BooleanFilter canAppointment;

    private IntegerFilter order;

    private StatusFilter status;

    private StringFilter desc;

    public BasicServiceCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getSearchNo() {
        return searchNo;
    }

    public void setSearchNo(StringFilter searchNo) {
        this.searchNo = searchNo;
    }

    public ServiceTypeFilter getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceTypeFilter serviceType) {
        this.serviceType = serviceType;
    }

    public AppraiseTypeFilter getAppraiseType() {
        return appraiseType;
    }

    public void setAppraiseType(AppraiseTypeFilter appraiseType) {
        this.appraiseType = appraiseType;
    }

    public IntegerFilter getAppraiseValue() {
        return appraiseValue;
    }

    public void setAppraiseValue(IntegerFilter appraiseValue) {
        this.appraiseValue = appraiseValue;
    }

    public DoubleFilter getReferencePrice() {
        return referencePrice;
    }

    public void setReferencePrice(DoubleFilter referencePrice) {
        this.referencePrice = referencePrice;
    }

    public DoubleFilter getSubsidyPrice() {
        return subsidyPrice;
    }

    public void setSubsidyPrice(DoubleFilter subsidyPrice) {
        this.subsidyPrice = subsidyPrice;
    }

    public IntegerFilter getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(IntegerFilter serviceCount) {
        this.serviceCount = serviceCount;
    }

    public BooleanFilter getCanAppointment() {
        return canAppointment;
    }

    public void setCanAppointment(BooleanFilter canAppointment) {
        this.canAppointment = canAppointment;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
    }

    public StatusFilter getStatus() {
        return status;
    }

    public void setStatus(StatusFilter status) {
        this.status = status;
    }

    public StringFilter getDesc() {
        return desc;
    }

    public void setDesc(StringFilter desc) {
        this.desc = desc;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BasicServiceCriteria that = (BasicServiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(searchNo, that.searchNo) &&
            Objects.equals(serviceType, that.serviceType) &&
            Objects.equals(appraiseType, that.appraiseType) &&
            Objects.equals(appraiseValue, that.appraiseValue) &&
            Objects.equals(referencePrice, that.referencePrice) &&
            Objects.equals(subsidyPrice, that.subsidyPrice) &&
            Objects.equals(serviceCount, that.serviceCount) &&
            Objects.equals(canAppointment, that.canAppointment) &&
            Objects.equals(order, that.order) &&
            Objects.equals(status, that.status) &&
            Objects.equals(desc, that.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        searchNo,
        serviceType,
        appraiseType,
        appraiseValue,
        referencePrice,
        subsidyPrice,
        serviceCount,
        canAppointment,
        order,
        status,
        desc
        );
    }

    @Override
    public String toString() {
        return "BasicServiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (searchNo != null ? "searchNo=" + searchNo + ", " : "") +
                (serviceType != null ? "serviceType=" + serviceType + ", " : "") +
                (appraiseType != null ? "appraiseType=" + appraiseType + ", " : "") +
                (appraiseValue != null ? "appraiseValue=" + appraiseValue + ", " : "") +
                (referencePrice != null ? "referencePrice=" + referencePrice + ", " : "") +
                (subsidyPrice != null ? "subsidyPrice=" + subsidyPrice + ", " : "") +
                (serviceCount != null ? "serviceCount=" + serviceCount + ", " : "") +
                (canAppointment != null ? "canAppointment=" + canAppointment + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (desc != null ? "desc=" + desc + ", " : "") +
            "}";
    }

}
