package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAO {


    @Autowired
    JdbcTemplate j;

    public boolean checkExist(int userId) {
        String sql = "select count(1) from user where id=? and deleted=?";
        int count = j.queryForObject(sql, new Object[]{userId, Constants.DB.NOT_DELETED}, Integer.class);
        return count == 0 ? false : true;
    }

    public UserVO getUserInfo(final int userId) {
        String sql = "select nick_name,img,is_machine from user where id=? and deleted=?";
        return j.queryForObject(sql, new Object[]{userId, Constants.DB.NOT_DELETED}, new RowMapper<UserVO>() {
            @Override
            public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserVO vo = new UserVO();
                vo.setId(userId);
                vo.setNickName(rs.getString(1));
                vo.setImg(rs.getString(2));
                vo.setIsMachine(rs.getInt(3));
                return vo;
            }
        });
    }

    public int randomUser() {
        String sql = "select id from user ORDER BY RAND() LIMIT 1";
        return j.queryForObject(sql, new Object[]{}, Integer.class);
    }


    public UserVO getUserNameAndImg(int userId) {
        String sql = "select nick_name,img from user where id=? and deleted=?";
        return j.queryForObject(sql, new Object[]{userId, Constants.DB.NOT_DELETED}, new RowMapper<UserVO>() {
            @Override
            public UserVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserVO vo = new UserVO();
                vo.setNickName(rs.getString(1));
                vo.setImg(rs.getString(2));
                return vo;
            }
        });
    }
}
