package com.j13.jax.fetcher.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class FetchIndexDAO {

    @Autowired
    JdbcTemplate j;


    public int insert(final int sourceId, final int lastIndex) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into fetch_index (source_id,last_index,createtime) values (?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, sourceId);
                pstmt.setInt(2, lastIndex);
                return null;
            }
        }, holder);
        return holder.getKey().intValue();
    }


    public int getLastIndex(int sourceId) {
        String sql = "select last_index from fetch_index where source=? order by id desc limit 1";
        return j.queryForObject(sql, new Object[]{sourceId}, Integer.class);
    }


    public boolean checkExist(int sourceId) {
        String sql = "select count(1) from fetch_index where source_id=?";
        int count = j.queryForObject(sql, new Object[]{sourceId}, Integer.class);
        return count == 0 ? false : true;
    }
}
