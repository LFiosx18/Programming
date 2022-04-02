package serialPack.collection;

import java.io.Serializable;

public enum OrganizationType implements Serializable {
    COMMERCIAL("commercial"),
    PUBLIC("public"),
    TRUST("trust"),
    PRIVATE_LIMITED_COMPANY("private_limited_company");

    private String type;
    OrganizationType(String type) { this.type = type; }

    public static void outType() {
        for (OrganizationType i:values())
            System.out.println(i);
    }
}
