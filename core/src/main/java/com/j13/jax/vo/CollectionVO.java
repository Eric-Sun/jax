package com.j13.jax.vo;

public class CollectionVO {
    private int id;
    private int userId;
    private int type;
    private int resourceId;
    private long createtime;
    private Object resourceObj;

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public Object getResourceObj() {
        return resourceObj;
    }

    public void setResourceObj(Object resourceObj) {
        this.resourceObj = resourceObj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
