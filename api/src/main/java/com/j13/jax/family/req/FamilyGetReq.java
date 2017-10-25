package com.j13.jax.family.req;

import com.j13.poppy.anno.Parameter;

public class FamilyGetReq {
    @Parameter(desc="")
    private int familyId;

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }
}
