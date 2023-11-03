package com.sagar.ccetmobileapp.network.models;

import java.util.ArrayList;
import java.util.List;

public enum Branches {
    CSE(1),
    ELE(4),
    ETAndT(3),
    Mech(2),
    Other(5);

    private final int branchId;

    Branches(int branchId) {
        this.branchId = branchId;
    }

    public static Branches getBranch(int id) {
        for (Branches branch : Branches.values()) {
            if (branch.getBranchId() == id) {
                return branch;
            }
        }
        return Other;
    }

    public int getBranchId() {
        return this.branchId;
    }

    public static Branches getBranch(String branchString) {
        for (Branches branch : Branches.values()) {
            if (branch.toString().equalsIgnoreCase(branchString)) {
                return branch;
            }
        }
        return Other;
    }

    public String getBranch() {
        return this.toString().toUpperCase();
    }

    public static List<String> getList() {
        List<String> strings = new ArrayList<>();
        for (Branches branch : Branches.values()) {
            strings.add(branch.toString());
        }
        return strings;
    }
}
