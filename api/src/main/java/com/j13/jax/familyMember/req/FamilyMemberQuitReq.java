package com.j13.jax.familyMember.req;

import com.j13.poppy.anno.Parameter;

public class FamilyMemberQuitReq {
    @Parameter(desc = "")
    private int familyMemberId;

    public int getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(int familyMemberId) {
        this.familyMemberId = familyMemberId;
    }
}
