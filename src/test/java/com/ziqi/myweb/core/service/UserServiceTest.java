package com.ziqi.myweb.core.service;

import com.ziqi.myweb.BaseCase;
import com.ziqi.myweb.common.model.ResultDTO;
import com.ziqi.myweb.common.model.UserDTO;
import com.ziqi.myweb.common.query.UserQuery;
import com.ziqi.myweb.dal.model.UserDO;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

/**
 * Description: UserServiceTest
 * User: qige
 * Date: 15/4/14
 * Time: 16:55
 */
public class UserServiceTest extends BaseCase {

    @Resource
    private UserService userService;

    @Test
    public void testQuery() throws Exception {
        UserQuery userQuery = new UserQuery();
        userQuery.setAccount("ziqi.gzq");
        System.err.println(userService.query(userQuery));
    }

    @Test
    public void testQueryById() throws Exception {
        System.err.println(userService.queryById(9));
    }

    @Test
    public void testSave() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount("ziqi.gzq");
        userDTO.setAge(21);
        userDTO.setGender(1);
        ResultDTO<Integer> resultDTO = userService.save(userDTO);
        assertTrue(resultDTO.isSuccess());
        assertNotNull(resultDTO.getResult());
        assertTrue(resultDTO.getResult() > 0);
        System.err.println(resultDTO);
    }

    @Test
    public void testDelete() throws Exception {
        System.err.println(userService.delete(9));
    }

    @Test
    public void testUpdate() throws Exception {

    }
}