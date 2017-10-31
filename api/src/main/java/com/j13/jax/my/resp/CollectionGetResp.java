package com.j13.jax.my.resp;

public class CollectionGetResp {
    private int id;
    private int type;
    private Object resourceObj;
    private long createtime;

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
}
