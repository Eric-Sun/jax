package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.vo.CollectionVO;
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
public class CollectionDAO {

    @Autowired
    JdbcTemplate j;

    public int add(final int userId, final int type, final int resourceId) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into collection " +
                "(user_id,type,resource_id,createtime) " +
                "values" +
                "(?,?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, type);
                pstmt.setInt(3, resourceId);
                return pstmt;
            }
        }, holder);

        return holder.getKey().intValue();
    }


    public int delete(int userId, int collectionId) {
        String sql = "update collection set deleted=? where user_id=? and id=?";
        return j.update(sql, new Object[]{Constants.DB.DELETED}, userId, collectionId);
    }


    public List<CollectionVO> list(int userId, int pageNum, int sizePerPage) {
        String sql = "select id,`type`,resource_id,createtime from collection where user_id=? order by createtime desc limit ?,?";
        return j.query(sql, new Object[]{userId, pageNum * sizePerPage, sizePerPage}, new RowMapper<CollectionVO>() {

            @Override
            public CollectionVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CollectionVO vo = new CollectionVO();
                vo.setId(rs.getInt(1));
                vo.setType(rs.getInt(2));
                vo.setResourceId(rs.getInt(3));
                vo.setCreatetime(rs.getDate(4).getTime());
                return vo;
            }
        });
    }


    public boolean checkExists(int userId, int resourceId, int type) {
        String sql = "select count(1) from collection where user_id=? and resource_id=? and type=?";
        return j.queryForObject(sql, new Object[]{userId, resourceId, type}, Integer.class) == 0 ? false : true;
    }
}
