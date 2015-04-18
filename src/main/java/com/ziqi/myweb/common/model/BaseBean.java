package com.ziqi.myweb.common.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Description: BaseBean
 * User: qige
 * Date: 15/4/14
 * Time: 23:07
 */
public class BaseBean implements Serializable {

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
