package com.j13.jax.message.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.message.MessageConstant;
import com.j13.jax.message.vo.MessageVO;
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
public class MessageDAO {
    @Autowired
    JdbcTemplate j;


    public int add(final MessageVO message) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into message (from_user_id,to_user_id,content,createtime,updatetime) values " +
                "(?,?,?,now(),now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, message.getFromUserId());
                pstmt.setInt(2, message.getToUserId());
                pstmt.setString(3, message.getContent());
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }


    public int read(int toUserId, int messageId) {
        String sql = "update message set `read`=?,updatetime=now() where id=? and to_user_id=? and deleted=?";
        return j.update(sql, new Object[]{MessageConstant.READ, messageId, toUserId, Constants.DB.NOT_DELETED});
    }


    public int delete(int toUserId, int messageId) {
        String sql = "update message set deleted=?,updatetime=now() where id=? and to_user_id=?";
        return j.update(sql, new Object[]{Constants.DB.DELETED, messageId, toUserId});
    }


    public List<MessageVO> search(final int toUserId, int pageNum, int sizePerPage) {
        String sql = "select from_user_id,content,createtime,`read`,id from message where deleted=? and to_user_id=? " +
                "limit ?,?";
        return j.query(sql, new Object[]{Constants.DB.NOT_DELETED, toUserId, pageNum * sizePerPage, sizePerPage}, new RowMapper<MessageVO>() {
            @Override
            public MessageVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MessageVO vo = new MessageVO();
                vo.setFromUserId(rs.getInt(1));
                vo.setToUserId(toUserId);
                vo.setCreatetime(rs.getDate(3));
                vo.setContent(rs.getString(2));
                vo.setRead(rs.getInt(4));
                vo.setId(rs.getInt(5));
                return vo;
            }
        });
    }


}
