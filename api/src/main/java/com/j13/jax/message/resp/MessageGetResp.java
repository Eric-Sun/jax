package com.j13.jax.message.resp;

import com.j13.poppy.anno.Parameter;

public class MessageGetResp {
    @Parameter(desc="message id")
    private int messageId;
    @Parameter(desc="发送者用户id")
    private int fromUserId;
    @Parameter(desc="发送者用户名称")
    private String fromUserName;
    @Parameter(desc="发送者用户头像地址")
    private String fromUserHeadImgUrl;
    @Parameter(desc="接受者用户id")
    private int toUserId;
    @Parameter(desc="接受者用户名称")
    private String toUserName;
    @Parameter(desc="接受者用户头像地址")
    private String toUserHeadImgUrl;
    @Parameter(desc="内容")
    private String content;
    @Parameter(desc="发送时间，long")
    private long createtime;
    @Parameter(desc="是否已经被读")
    private int read;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getFromUserHeadImgUrl() {
        return fromUserHeadImgUrl;
    }

    public void setFromUserHeadImgUrl(String fromUserHeadImgUrl) {
        this.fromUserHeadImgUrl = fromUserHeadImgUrl;
    }

    public int getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(int fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public String getToUserHeadImgUrl() {
        return toUserHeadImgUrl;
    }

    public void setToUserHeadImgUrl(String toUserHeadImgUrl) {
        this.toUserHeadImgUrl = toUserHeadImgUrl;
    }

    public int getToUserId() {
        return toUserId;
    }

    public void setToUserId(int toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }
}
