package com.j13.jax.familyMember.req;

import com.j13.poppy.anno.Parameter;

public class FamilyMemberQuitReq {
    @Parameter(desc = "")
    private int familyMemberId;
    @Parameter(desc="")
    private int familyId;

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }
}
