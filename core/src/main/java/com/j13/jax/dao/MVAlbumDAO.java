package com.j13.jax.dao;

import com.j13.jax.vo.MVAlbumVO;
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
public class MVAlbumDAO {


    @Autowired
    JdbcTemplate j;

    /**
     * 获取固定条数后面的N条记录的id
     *
     * @param fromId
     * @param size
     * @return
     */
    public List<Integer> idList(int fromId, int size) {
        String sql = "select id from mv_album where id>? limit ?";
        return j.query(sql, new Object[]{}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt(1);
                return id;
            }
        });
    }


    public int addMVAlbum(final MVAlbumVO info) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into mv_album(source_id,remote_album_id,tag_id,title,createtime,updatetime,user_id) values " +
                "(?,?,?,?,now(),now(),?)";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, info.getSourceId());
                pstmt.setInt(2, info.getRemoteAlbumId());
                pstmt.setInt(3, info.getTagId());
                pstmt.setString(4, info.getTitle());
                pstmt.setInt(5, info.getUserId());
                return pstmt;
            }
        }, holder);

        return holder.getKey().intValue();
    }


    public boolean checkMVAlbumExist(int remoteAlbumId) {
        String sql = "select count(1) from mv_album where remote_album_id=?";
        int i = j.queryForObject(sql, new Object[]{remoteAlbumId}, Integer.class);
        return i == 0 ? false : true;
    }

    public int getMVAlbumId(int remoteAlbumId) {
        String sql = "select id from mv_album where remote_album_id = ?";
        return j.queryForObject(sql, new Object[]{remoteAlbumId}, Integer.class);
    }

    public MVAlbumVO getMVAlbum(int mvAlbumId) {
        String sql = "select a.source_id,fs.name,a.remote_album_id,a.tag_id,`at`.name,a.title,a.user_id from mv_album a " +
                "left outer join fetch_source fs on fs.id=a.source_id " +
                "left outer join mv_album_tag `at` on `at`.id=a.tag_id " +
                "where a.id=?";
        return j.queryForObject(sql, new Object[]{mvAlbumId}, new RowMapper<MVAlbumVO>() {
            @Override
            public MVAlbumVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MVAlbumVO ai = new MVAlbumVO();
                ai.setSourceId(rs.getInt(1));
                ai.setSourceName(rs.getString(2));
                ai.setRemoteAlbumId(rs.getInt(3));
                ai.setTagId(rs.getInt(4));
                ai.setTagName(rs.getString(5));
                ai.setTitle(rs.getString(6));
                ai.setUserId(rs.getInt(7));
                return ai;
            }
        });
    }


    public String getAlbumTitle(int mbAlbumId) {
        String sql = "select title from mv_album where id=?";
        return j.queryForObject(sql, new Object[]{mbAlbumId}, String.class);
    }


    public List<MVAlbumVO> MEINVIdList(int fromCursorId, int i) {
        String sql = "select id,user_id,title,praise,share,remote_album_id from mv_album where id>? order by id limit ?;";
        return j.query(sql, new Object[]{fromCursorId, i}, new RowMapper<MVAlbumVO>() {
            @Override
            public MVAlbumVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MVAlbumVO vo = new MVAlbumVO();
                vo.setId(rs.getInt(1));
                vo.setUserId(rs.getInt(2));
                vo.setTitle(rs.getString(3));
                vo.setPraise(rs.getInt(4));
                vo.setShare(rs.getInt(5));
                vo.setRemoteAlbumId(rs.getInt(6));
                return vo;
            }
        });

    }
}
