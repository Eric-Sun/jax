package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.vo.EventVO;
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


    public EventVO get(final int eventId, final int familyId) {
        String sql = "select user_id,type,title,content,createtime from event where id=? and family_id=? and deleted=?";
        return j.queryForObject(sql, new Object[]{eventId, familyId, Constants.DB.NOT_DELETED}, new RowMapper<EventVO>() {
            @Override
            public EventVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                EventVO vo = new EventVO();
                vo.setUserId(rs.getInt(1));
                vo.setType(rs.getInt(2));
                vo.setTitle(rs.getString(3));
                vo.setContent(rs.getString(4));
                vo.setCreatetime(rs.getDate(5).getTime());
                vo.setId(eventId);
                vo.setFamilyId(familyId);
                return vo;
            }
        });
    }

    public void praise(int eventId) {
        String sql = "update event set praise=praise+1 where id=? and deleted=?";
        j.update(sql, new Object[]{eventId, Constants.DB.NOT_DELETED});
    }

    public void share(int eventId) {
        String sql = "update event set share=share+1 where id=? and deleted=?";
        j.update(sql, new Object[]{eventId, Constants.DB.NOT_DELETED});
    }

    public List<EventVO> list(int familyid, int pageNum, int sizePerPage) {
        String sql = "select id,user_id,type,title,content,createtime from event where family_id=? and deleted=? limit ?,?";
        return j.query(sql,
                new Object[]{familyid, Constants.DB.NOT_DELETED, pageNum * sizePerPage, sizePerPage},
                new RowMapper<EventVO>() {
                    @Override
                    public EventVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                        EventVO vo = new EventVO();
                        vo.setId(rs.getInt(1));
                        vo.setUserId(rs.getInt(2));
                        vo.setType(rs.getInt(3));
                        vo.setTitle(rs.getString(4));
                        vo.setContent(rs.getString(5));
                        vo.setCreatetime(rs.getTime(6).getTime());
                        return vo;
                    }
                });
    }
}
