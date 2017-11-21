package com.j13.jax.user.req;

import com.j13.poppy.anno.Parameter;

public class UserMobileRegisterReq {
    @Parameter(desc = "")
    private String mobile;
    @Parameter(desc = "")
    private String passwordAfterMD5;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPasswordAfterMD5() {
        return passwordAfterMD5;
    }

    public void setPasswordAfterMD5(String passwordAfterMD5) {
        this.passwordAfterMD5 = passwordAfterMD5;
    }
}
