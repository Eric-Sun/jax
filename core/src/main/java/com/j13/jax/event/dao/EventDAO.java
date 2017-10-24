package com.j13.jax.event.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.event.vo.EventVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EventDAO {


    @Autowired
    JdbcTemplate j;

    public int add(final EventVO eventVO) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into event" +
                "(family_id,user_id,title,content,createtime,`type`) values " +
                "(?,?,?,?,now(),?)";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, eventVO.getFamilyId());
                pstmt.setInt(2, eventVO.getUserId());
                pstmt.setString(3, eventVO.getTitle());
                pstmt.setString(4, eventVO.getContent());
                pstmt.setInt(5, eventVO.getType());
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }


    public int getLeastMEINVId() {
        String sql = "select id from event where family_id=-1 order by id limit 1;";
        return j.queryForObject(sql, new Object[]{}, Integer.class);
    }

    public List<EventVO> MEINVIdList(int fromCursorId, int i) {
        String sql = "select id,content from event where family_id=-1 and id>? order by id limit ?;";
        return j.query(sql, new Object[]{fromCursorId, i}, new RowMapper<EventVO>() {
            @Override
            public EventVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                EventVO vo = new EventVO();
                vo.setContent(rs.getString(2));
                vo.setId(rs.getInt(1));
                return vo;
            }
        });

    }

    public EventVO get(int eventId) {
        String sql = "select user_id,type,title,content,createtime from event where id=? and deleted=?";
        return j.queryForObject(sql, new Object[]{eventId, Constants.DB.NOT_DELETED}, new RowMapper<EventVO>() {
            @Override
            public EventVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                EventVO vo = new EventVO();
                vo.setUserId(rs.getInt(1));
                vo.setType(rs.getInt(2));
                vo.setTitle(rs.getString(3));
                vo.setContent(rs.getString(4));
                vo.setCreatetime(rs.getDate(5).getTime());
                return vo;
            }
        });
    }
}
