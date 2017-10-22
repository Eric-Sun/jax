package com.j13.jax.family.req;

import com.j13.poppy.anno.Parameter;

public class FamilyDeleteReq {
    @Parameter(desc="拥有者用户id")
    private int ownerUserId;
    @Parameter(desc="家族id")
    private int familyId;

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(int ownerUserId) {
        this.ownerUserId = ownerUserId;
    }
}
