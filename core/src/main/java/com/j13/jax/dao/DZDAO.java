package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.vo.DZVO;
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
public class DZDAO {

    @Autowired
    JdbcTemplate j;


    public int add(final int userId, final int sourceId, final String sourceDZId, final String content, final String md5) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into dz " +
                "(user_id,source_id,source_dz_id,content,createtime,md5) values" +
                "(?,?,?,?,now(),?)";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, sourceId);
                pstmt.setString(3, sourceDZId);
                pstmt.setString(4, content);
                pstmt.setString(5, md5);
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }


    public boolean checkExisted(String md5) {
        String sql = "select count(1) from dz where md5=? and deleted=?";
        int count = j.queryForObject(sql, new Object[]{md5, Constants.DB.NOT_DELETED}, Integer.class);
        return count == 0 ? false : true;
    }

    public int getLeastId() {
        String sql = "select id from dz order by id limit 1;";
        return j.queryForObject(sql, new Object[]{}, Integer.class);
    }

    public List<DZVO> list(int fromCursorId, int i) {
        String sql = "select id,user_id,content,praise,share from dz where id>? and deleted =? order by id limit ?";
        return j.query(sql, new Object[]{fromCursorId, Constants.DB.NOT_DELETED, i}, new RowMapper<DZVO>() {
            @Override
            public DZVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                DZVO vo = new DZVO();
                vo.setDzId(rs.getInt(1));
                vo.setUserId(rs.getInt(2));
                vo.setContent(rs.getString(3));
                vo.setPraise(rs.getInt(4));
                vo.setShare(rs.getInt(5));
                return vo;
            }
        });
    }

    public DZVO get(int dzId) {
        String sql = "select id,user_id,content,praise,share from dz where deleted=? and id=?";
        return j.queryForObject(sql, new Object[]{Constants.DB.NOT_DELETED, dzId}, new RowMapper<DZVO>() {
            @Override
            public DZVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                DZVO vo = new DZVO();
                vo.setDzId(rs.getInt(1));
                vo.setUserId(rs.getInt(2));
                vo.setContent(rs.getString(3));
                vo.setPraise(rs.getInt(4));
                vo.setShare(rs.getInt(5));
                return vo;
            }
        });
    }
}
