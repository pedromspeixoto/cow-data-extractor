package com.cde.utils.models;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class PageDetails {
    
    private Integer limit;
    private Integer offset;
    private Long totalElements;

    public PageDetails() {
    }

    public PageDetails(Integer limit, Integer offset, Long totalElements) {
        this.limit = limit;
        this.offset = offset;
        this.totalElements = totalElements;
    }

    public Integer getLimit() {
        return this.limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return this.offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
