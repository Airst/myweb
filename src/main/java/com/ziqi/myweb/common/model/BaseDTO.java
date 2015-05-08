package com.ziqi.myweb.common.model;

import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: BaseDO
 * User: qige
 * Date: 15/4/11
 * Time: 01:22
 */
public class BaseDTO extends BaseBean {

    private Integer id;

    private String feature = "";

    private Integer options;

    private Integer isDeleted;

    private Date gmtCreate;

    private Date gmtModified;

    private Integer version;

    private Map<String, String> featureMap;

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

    public void addFeature(String key, String value) {
        buildFeatureMap();
        featureMap.put(key, value);
        flushFeature();
    }

    public String getFeature(String key) {
        buildFeatureMap();
        return featureMap.get(key);
    }

    private void buildFeatureMap() {
        featureMap = new HashMap<String, String>();
        if (StringUtils.isNotBlank(feature)) {
            String[] lines = feature.split(";");
            for(String line : lines) {
                String[] items = line.split(":");
                featureMap.put(items[0], items[1]);
            }
        }
    }

    private void flushFeature() {
        if(featureMap == null || featureMap.isEmpty()) return;
        feature = "";
        for(Map.Entry<String, String> entrySet : featureMap.entrySet()) {
            feature += entrySet.getKey() + ":" + entrySet.getValue() + ";";
        }
    }
}
