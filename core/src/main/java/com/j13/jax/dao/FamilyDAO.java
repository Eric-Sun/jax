package com.j13.jax.dao;

import com.j13.jax.core.Constants;
import com.j13.jax.vo.FamilyVO;
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
public class FamilyDAO {

    @Autowired
    JdbcTemplate j;

    public int add(final String name, final String headImgId, final String coverImgId,
                   final String brief, final int creatorUserId, final int ownerUserId) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into family " +
                "(name,head_img_id,cover_img_id,brief,creator_user_id,owner_user_id,createtime) values " +
                "(?,?,?,?,?,?,now())";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setString(1, name);
                pstmt.setString(2, headImgId);
                pstmt.setString(3, coverImgId);
                pstmt.setString(4, brief);
                pstmt.setInt(5, creatorUserId);
                pstmt.setInt(6, ownerUserId);
                return pstmt;
            }
        }, holder);
        return holder.getKey().intValue();
    }


    public int delete(int uid, int familyId) {
        String sql = "update family set deleted=? where owner_user_id=? and id=?";
        return j.update(sql, new Object[]{Constants.DB.DELETED, uid, familyId});
    }


    public FamilyVO get(int familyId) {
        String sql = "select id,name,head_img_id,cover_img_id,brief,creator_user_id,createtime,members,topics from family" +
                " where id=? and deleted=?";
        return j.queryForObject(sql, new Object[]{familyId, Constants.DB.NOT_DELETED}, new RowMapper<FamilyVO>() {
            @Override
            public FamilyVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                FamilyVO vo = new FamilyVO();
                vo.setId(rs.getInt(1));
                vo.setName(rs.getString(2));
                vo.setHeadImgId(rs.getInt(3));
                vo.setCoverImgId(rs.getInt(4));
                vo.setBrief(rs.getString(5));
                vo.setCreatorUserId(rs.getInt(6));
                vo.setCreatetime(rs.getDate(7).getTime());
                vo.setMemberCount(rs.getInt(8));
                vo.setTopicCount(rs.getInt(9));
                return vo;
            }
        });
    }


    public List<FamilyVO> createdList(int userId, int pageNum, int sizePerPage) {
        String sql = "select id,name,head_img_id,cover_img_id,brief,creator_user_id,createtime,members,topics from family" +
                " where creator_user_id=? and deleted=? limit ?,?";
        return j.query(sql, new Object[]{userId, Constants.DB.NOT_DELETED, pageNum * sizePerPage, sizePerPage}, new RowMapper<FamilyVO>() {
            @Override
            public FamilyVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                FamilyVO vo = new FamilyVO();
                vo.setId(rs.getInt(1));
                vo.setName(rs.getString(2));
                vo.setHeadImgId(rs.getInt(3));
                vo.setCoverImgId(rs.getInt(4));
                vo.setBrief(rs.getString(5));
                vo.setCreatorUserId(rs.getInt(6));
                vo.setCreatetime(rs.getDate(7).getTime());
                vo.setMemberCount(rs.getInt(8));
                vo.setTopicCount(rs.getInt(9));
                return vo;
            }
        });
    }


    public long changeMemberCount(int familyId, int count) {
        String sql = "update family set members=members+? where id=? and deleted=?";
        return j.update(sql, new Object[]{count, familyId, Constants.DB.NOT_DELETED});
    }

    public long changeTopicCount(int familyId, int count) {
        String sql = "update family set topics=topics+? where id=? and deleted=?";
        return j.update(sql, new Object[]{count, familyId, Constants.DB.NOT_DELETED});
    }
}
