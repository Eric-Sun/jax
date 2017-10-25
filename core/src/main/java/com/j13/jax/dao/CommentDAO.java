package com.j13.jax.dao;

import com.j13.jax.vo.CommentVO;
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
public class CommentDAO {

    @Autowired
    JdbcTemplate j;

    public int add(final CommentVO commentVO) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into comment " +
                "(event_id,user_id,content,replay_cid,createtime) values " +
                "(?,?,?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, commentVO.getEventId());
                pstmt.setInt(2, commentVO.getUserId());
                pstmt.setString(3, commentVO.getContent());
                pstmt.setInt(4,commentVO.getReplyCId());
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }
}
