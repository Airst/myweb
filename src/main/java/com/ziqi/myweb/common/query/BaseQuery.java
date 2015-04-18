package com.ziqi.myweb.common.query;

import com.ziqi.myweb.common.model.BaseBean;
import com.ziqi.myweb.common.model.Pagination;

import java.util.Date;

/**
 * Description: BaseQuery
 * User: qige
 * Date: 15/4/14
 * Time: 15:36
 */
public class BaseQuery extends Pagination {

    private Integer id;

    private String feature;

    private Integer options;

    private Integer isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer version;

    public String orderField;

    public String groupField;

    public Date fromCreate;
    public Date toCreate;

    public Date fromModified;
    public Date toModified;

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getGroupField() {
        return groupField;
    }

    public void setGroupField(String groupField) {
        this.groupField = groupField;
    }

    public Date getFromCreate() {
        return fromCreate;
    }

    public void setFromCreate(Date fromCreate) {
        this.fromCreate = fromCreate;
    }

    public Date getToCreate() {
        return toCreate;
    }

    public void setToCreate(Date toCreate) {
        this.toCreate = toCreate;
    }

    public Date getFromModified() {
        return fromModified;
    }

    public void setFromModified(Date fromModified) {
        this.fromModified = fromModified;
    }

    public Date getToModified() {
        return toModified;
    }

    public void setToModified(Date toModified) {
        this.toModified = toModified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public Integer getOptions() {
        return options;
    }

    public void setOptions(Integer options) {
        this.options = options;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

}
