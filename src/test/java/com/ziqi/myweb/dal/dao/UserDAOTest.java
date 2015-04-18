package com.ziqi.myweb.dal.dao;

import com.ziqi.myweb.BaseCase;
import com.ziqi.myweb.dal.query.QueryMap;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Description: UserDAOTest
 * User: qige
 * Date: 15/4/12
 * Time: 18:55
 */
public class UserDAOTest extends BaseCase {

    @Resource
    private UserDAO userDAO;

    @Test
    public void testSelect() throws Exception {
        userDAO.select(new QueryMap());
    }

    @Test
    public void testSelectById() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}