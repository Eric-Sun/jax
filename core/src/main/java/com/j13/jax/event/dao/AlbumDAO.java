package com.j13.jax.event.dao;

import com.j13.jax.fetcher.vo.AlbumInfo;
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
public class AlbumDAO {


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
        String sql = "select id from album where id>? limit ?";
        return j.query(sql, new Object[]{}, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt(1);
                return id;
            }
        });
    }


    public int addAlbum(final AlbumInfo info) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into album(source_id,remote_album_id,tag_id,title,createtime,updatetime) values " +
                "(?,?,?,?,now(),now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, info.getSourceId());
                pstmt.setInt(2, info.getRemoteAlbumId());
                pstmt.setInt(3, info.getTagId());
                pstmt.setString(4, info.getTitle());
                return pstmt;
            }
        }, holder);

        return holder.getKey().intValue();
    }


    public boolean checkAlbumExist(int remoteAlbumId) {
        String sql = "select count(1) from album where remote_album_id=?";
        int i = j.queryForInt(sql, new Object[]{remoteAlbumId});
        return i == 0 ? false : true;
    }

    public int getAlbumId(int remoteAlbumId) {
        String sql = "select id from album where remote_album_id = ?";
        return j.queryForInt(sql, new Object[]{remoteAlbumId});
    }

}
