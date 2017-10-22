package com.j13.jax.event.dao;

import com.j13.jax.event.vo.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventDAO {


    @Autowired
    JdbcTemplate j;

    public int add(final EventVO eventVO) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into event" +
                "(family_id,user_id,title,content,createtime) values " +
                "(?,?,?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, eventVO.getFamilyId());
                pstmt.setInt(2, eventVO.getUserId());
                pstmt.setString(3, eventVO.getTitle());
                pstmt.setString(4, eventVO.getContent());
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }




}
