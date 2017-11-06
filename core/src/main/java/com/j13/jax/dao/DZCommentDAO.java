package com.j13.jax.dao;

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
public class DZCommentDAO {

    @Autowired
    JdbcTemplate j;


    public int add(final int userId, final int dzId, final String content) {
        final String sql = "insert into dz_comment " +
                "(user_id,dz_id,content,createtime) values (?,?,?,now())" +
                "";

        KeyHolder holder = new GeneratedKeyHolder();
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, dzId);
                pstmt.setString(3, content);
                return pstmt;
            }
        }, holder);

        return holder.getKey().intValue();
    }

}
