package com.j13.jax.family.dao;

import com.j13.jax.family.vo.FamilyVO;
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
public class FamilyDAO {

    @Autowired
    JdbcTemplate j;

    public int add(final FamilyVO familyVO) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into family " +
                "(name,head_img,cover_img,brief,creator_user_id,owner_user_id,createtime) values " +
                "(?,?,?,?,?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, familyVO.getName());
                pstmt.setString(2,familyVO.getHeadImg());
                pstmt.setString(3,familyVO.getCoverImg());
                pstmt.setString(4,familyVO.getBrief());
                pstmt.setInt(5,familyVO.getCreatorUserId());
                pstmt.setInt(6,familyVO.getOwnerUserId());
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }







}
