package com.j13.jax.event.dao;

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
public class SystemFamilyCursorDAO {

    @Autowired
    JdbcTemplate j;


    /**
     * 检测cursor是否存在
     *
     * @param userId
     * @param familyId
     * @return 如果true则是存在，如果false则为不存在
     */
    public boolean checkExisted(int userId, int familyId) {
        String sql = "select count(1) from system_family_cursor where user_id=? and family_id=?";
        int id = j.queryForObject(sql, new Object[]{userId, familyId}, Integer.class);
        return id == 0 ? false : true;
    }


    /**
     * 更新cursor
     *
     * @param userId
     * @param familyId
     * @param targetCursorId 对应f的已经完成展示的cursor
     */
    public void update(int userId, int familyId, int targetCursorId) {
        String sql = "update system_family_cursor set cursor_id=? where user_id=? and family_id=?";
        j.update(sql, new Object[]{targetCursorId, userId, familyId});
    }


    /**
     * 当用户从来没有生成cursor的时候插入一个新的
     *
     * @param userId
     * @param familyId
     * @param firstCursorId
     * @return
     */
    public int insert(final int userId, final int familyId, final int firstCursorId) {
        KeyHolder holder = new GeneratedKeyHolder();
        final String sql = "insert into system_family_cursor " +
                "(user_id,family_id,cursor_id) " +
                "values " +
                "(?,?,?)";
        j.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                pstmt.setInt(1, userId);
                pstmt.setInt(2, familyId);
                pstmt.setInt(3, firstCursorId);
                return pstmt;
            }
        }, holder);

        return holder.getKey().intValue();
    }


    /**
     * 查询对应的cursor_id，需要确保这条记录一定存在
     * @param userId
     * @param familyId
     * @return
     */
    public int getCursorId(int userId, int familyId) {
        String sql = "select cursor_id from system_family_cursor where user_id=? and family_id=?";
        return j.queryForObject(sql, new Object[]{userId, familyId}, Integer.class);
    }
}
