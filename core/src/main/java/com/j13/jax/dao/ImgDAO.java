package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.vo.ImgVO;
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

/**
 * img为整个v3系统中用户上传的img的存储表
 */
@Repository
public class ImgDAO {

    @Autowired
    JdbcTemplate j;

    /**
     * 存储
     *
     * @param type     img的类型（来自于什么用户动作）
     * @param fileName 存储的文件名
     * @return
     */
    public int add(final int type, final String fileName) {
        final String sql = "insert into img " +
                "(`type`,file_name,createtime) values " +
                "(?,?,now())";
        KeyHolder holder = new GeneratedKeyHolder();
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, type);
                pstmt.setString(2, fileName);
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }


    public int delete(final int imgId) {
        String sql = "update img set deleted=? where id=?";
        return j.update(sql, new Object[]{Constants.DB.DELETED, imgId});
    }


    public ImgVO get(int id) {
        String sql = "select `type`,file_name from img where id=? and deleted=?";
        return j.queryForObject(sql, new Object[]{id, Constants.DB.NOT_DELETED}, new RowMapper<ImgVO>() {
            @Override
            public ImgVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ImgVO vo = new ImgVO();
                vo.setType(rs.getInt(1));
                vo.setFileName(rs.getString(2));
                return vo;
            }
        });
    }

}
