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
public class DZDAO {

    @Autowired
    JdbcTemplate j;


    public int add(final int userId,final  int sourceId,final  int sourceDZId,final  String content) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into dz " +
                "(user_id,source_id,source_dz_id,content,createtime) values" +
                "(?,?,?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, sourceId);
                pstmt.setInt(3, sourceDZId);
                pstmt.setString(4, content);
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }


}
