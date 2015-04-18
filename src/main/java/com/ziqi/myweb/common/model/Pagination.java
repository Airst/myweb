package com.ziqi.myweb.common.model;

/**
 * Description: Pagination
 * User: qige
 * Date: 15/4/13
 * Time: 11:13
 */
public class Pagination extends BaseBean {

    private Integer pageSize = 20;

    private Integer pageIndex = 1;

    private Integer totalPage;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public void setStartLimitSize(Integer start, Integer limit, Integer size) {
        if(start != null && limit != null) {
            pageSize = limit;
            pageIndex = (start / limit) + 1;
            if(size != null) {
                totalPage = (size / limit) + 1;
            }
        }
    }

    public Integer getStart() {
        return pageSize * (pageIndex - 1);
    }

    public Integer getLimit() {
        return pageSize;
    }
}
