package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.swing.tree.RowMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class FamilyMemberDAO {

    @Autowired
    JdbcTemplate j;

    public int add(int userId, int familyId) {
        KeyHolder holder = new GeneratedKeyHolder();
        String sql = "insert into family_member " +
                "(user_id,family_id,createtime) " +
                "values " +
                "(?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, familyId);
                return pstmt;
            }
        }, holder);

        return holder.getKey().intValue();
    }



    public int delete(int userId, int familyMemberId) {
        String sql = "update family_member set deleted=? where user_id=? and id=?";
        return j.update(sql, new Object[]{Constants.DB.DELETED, userId, familyMemberId});
    }


    public boolean checkExist(int userId, int familyId) {
        String sql = "select count(1) from family_member where user_id=? and family_id=? and deleted=?";
        return j.queryForObject(sql, new Object[]{userId, familyId, Constants.DB.NOT_DELETED}, Integer.class) == 0 ? false : true;
    }


}
