package com.j13.jax.user.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.user.vo.UserVO;
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
                vo.setImgUrl(rs.getString(2));
                vo.setIsMachine(rs.getInt(3));
                return vo;
            }
        });
    }

}
