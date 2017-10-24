package com.j13.jax.event.dao;

import com.j13.jax.event.vo.ImgVO;
import com.j13.jax.fetcher.vo.RemoteImgInfo;
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
public class ImgDAO {


    @Autowired
    JdbcTemplate j;


    public int add(final RemoteImgInfo rii) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into img (album_id,remote_img_url,remote_img_id,relation_local_path,createtime,updatetime) " +
                "values (?,?,?,?,now(),now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, rii.getAlbumId());
                pstmt.setString(2, rii.getRemoteImgUrl());
                pstmt.setInt(3, rii.getRemoteImgId());
                pstmt.setString(4, rii.getRelationLocalPath());
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }

    public boolean checkImgExist(String remoteImgUrl) {
        String sql = "select count(1) from img where remote_img_url=?";
        int count = j.queryForObject(sql, new Object[]{remoteImgUrl}, Integer.class);

        return count == 0 ? false : true;
    }

    public List<ImgVO> list(int albumId) {
        String sql = "select id,album_id,remote_img_id from img where album_id=?";
        return j.query(sql, new Object[]{albumId}, new RowMapper<ImgVO>() {
            @Override
            public ImgVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ImgVO vo = new ImgVO();
                vo.setId(rs.getInt(1));
                vo.setAlbumId(rs.getInt(2));
                vo.setRemoteImgId(rs.getInt(3));
                return vo;
            }
        });
    }
}
