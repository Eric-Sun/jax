package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class FamilyDAO {

    @Autowired
    JdbcTemplate j;

    public int add(final String name, final String headImg, final String coverImg,
                   final String brief, final int creatorUserId, final int ownerUserId) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into family " +
                "(name,head_img,cover_img,brief,creator_user_id,owner_user_id,createtime) values " +
                "(?,?,?,?,?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, name);
                pstmt.setString(2, headImg);
                pstmt.setString(3, coverImg);
                pstmt.setString(4, brief);
                pstmt.setInt(5, creatorUserId);
                pstmt.setInt(6, ownerUserId);
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }


    public int delete(int uid, int familyId) {
        String sql = "update family set deleted=? where owner_user_id=? and id=?";
        return j.update(sql, new Object[]{Constants.DB.DELETED, uid, familyId});
    }



}
