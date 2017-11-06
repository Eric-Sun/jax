package com.j13.jax.dao;

import com.j13.jax.vo.MVImgVO;
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
public class MVImgDAO {


    @Autowired
    JdbcTemplate j;
    private int leastMEINVId;


    public int add(final MVImgVO rii) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into mv_img (mv_album_id,remote_img_url,remote_img_id,relation_local_path,createtime,updatetime) " +
                "values (?,?,?,?,now(),now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, rii.getMvAlbumId());
                pstmt.setString(2, rii.getRemoteImgUrl());
                pstmt.setInt(3, rii.getRemoteImgId());
                pstmt.setString(4, rii.getRelationLocalPath());
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    public boolean checkImgExist(String remoteImgUrl) {
        String sql = "select count(1) from mv_img where remote_img_url=?";
        int count = j.queryForObject(sql, new Object[]{remoteImgUrl}, Integer.class);

        return count == 0 ? false : true;
    }

    public List<MVImgVO> list(int mvAlbumId) {
        String sql = "select id,mv_album_id,remote_img_id from mv_img where mv_album_id=?";
        return j.query(sql, new Object[]{mvAlbumId}, new RowMapper<MVImgVO>() {
            @Override
            public MVImgVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                MVImgVO vo = new MVImgVO();
                vo.setId(rs.getInt(1));
                vo.setMvAlbumId(rs.getInt(2));
                vo.setRemoteImgId(rs.getInt(3));
                return vo;
            }
        });
    }

    public int getLeastMEINVId() {
        String sql = "select id from mv_album order by id limit 1;";
        return j.queryForObject(sql, new Object[]{}, Integer.class);
    }
}
