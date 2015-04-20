package com.ziqi.myweb.core.service;

import com.ziqi.myweb.BaseCase;
import com.ziqi.myweb.common.constants.TableConstants;
import com.ziqi.myweb.common.model.ThreadDTO;
import com.ziqi.myweb.common.query.ThreadQuery;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Description: ThreadServiceTest
 * User: qige
 * Date: 15/4/14
 * Time: 16:55
 */
public class ThreadServiceTest extends BaseCase {

    @Resource
    private ThreadService threadService;

    @Test
    public void testQuery() throws Exception {
        ThreadQuery threadQuery = new ThreadQuery();
        threadQuery.setPageIndex(1);
        threadQuery.setPageSize(20);
        threadQuery.setOrderField(TableConstants.Base.gmtCreate);
        assertTrue(threadService.query(threadQuery).isSuccess());

    }

    @Test
    public void testQueryById() throws Exception {

    }

    @Test
    public void testSave() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }
}