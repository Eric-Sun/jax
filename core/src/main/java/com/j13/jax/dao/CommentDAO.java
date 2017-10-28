package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.vo.CommentVO;
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
public class CommentDAO {

    @Autowired
    JdbcTemplate j;

    public int add(final int eventId, final int userId, final String content, final int replyCId) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into comment " +
                "(event_id,user_id,content,replay_cid,createtime) values " +
                "(?,?,?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, eventId);
                pstmt.setInt(2, userId);
                pstmt.setString(3, content);
                pstmt.setInt(4, replyCId);
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }


    public int delete(int userId, int cid) {
        String sql = "update comment set deleted=? where cid=? and user_id=?";
        return j.update(sql, new Object[]{Constants.DB.DELETED, cid, userId});
    }


    public List<CommentVO> list(int eventId) {
        String sql = "select user_id,content,reply_cid,is_hot,is_top,createtime from comment " +
                "where event_id=? and deleted=?";
        return j.query(sql, new Object[]{eventId, Constants.DB.NOT_DELETED}, new RowMapper<CommentVO>() {
            @Override
            public CommentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CommentVO vo = new CommentVO();
                vo.setUserId(rs.getInt(1));
                vo.setContent(rs.getString(2));
                vo.setReplyCId(rs.getInt(3));
                vo.setIsHot(rs.getInt(4));
                vo.setIsTop(rs.getInt(5));
                vo.setCreatetime(rs.getDate(6));
                return vo;
            }
        });
    }

    public CommentVO get(int cid) {
        String sql = "select user_id,content,reply_cid,is_hot,is_top,createtime from comment " +
                "where id=? and deleted=?";
        return j.queryForObject(sql, new Object[]{cid, Constants.DB.NOT_DELETED}, new RowMapper<CommentVO>() {
            @Override
            public CommentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CommentVO vo = new CommentVO();
                vo.setUserId(rs.getInt(1));
                vo.setContent(rs.getString(2));
                vo.setReplyCId(rs.getInt(3));
                vo.setIsHot(rs.getInt(4));
                vo.setIsTop(rs.getInt(5));
                vo.setCreatetime(rs.getDate(6));
                return vo;
            }
        });
    }


    public void praise(int cid) {
        String sql = "update comment set praise=praise+1 where id=? ";
        j.update(sql, new Object[]{cid});
    }

    public void share(int cid) {
        String sql = "update comment set share=share+1 where id=?";
        j.update(sql, new Object[]{cid});
    }

}

