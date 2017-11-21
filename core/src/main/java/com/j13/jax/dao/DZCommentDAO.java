package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.vo.DZCommentVO;
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
public class DZCommentDAO {

    @Autowired
    JdbcTemplate j;


    public int add(final int userId, final int dzId, final String content, final int isHot) {
        final String sql = "insert into dz_comment " +
                "(user_id,dz_id,content,createtime,is_hot) values (?,?,?,now(),?)" +
                "";

        KeyHolder holder = new GeneratedKeyHolder();
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, dzId);
                pstmt.setString(3, content);
                pstmt.setInt(4, isHot);
                return pstmt;
            }
        }, holder);

        return holder.getKey().intValue();
    }

    public List<DZCommentVO> list(int dzId, int isHot, int i) {
        String sql = "select id,user_id,dz_id,content,createtime,praise,share from dz_comment where dz_id=? and is_hot=? " +
                "and deleted=? order by id desc limit ?";
        return j.query(sql, new Object[]{dzId, isHot, Constants.DB.NOT_DELETED, i}, new RowMapper<DZCommentVO>() {
            @Override
            public DZCommentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                DZCommentVO vo = new DZCommentVO();
                vo.setDzCommentId(rs.getInt(1));
                vo.setUserId(rs.getInt(2));
                vo.setDzId(rs.getInt(3));
                vo.setContent(rs.getString(4));
                vo.setCreatetime(rs.getDate(5).getTime());
                vo.setPraise(rs.getInt(6));
                vo.setShare(rs.getInt(7));
                return vo;
            }
        });
    }



}
