package vn.dangdnh.definition;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class URIs {
    public static final String ROOT = "/iam";
    public static final String AUTH = ROOT + "/auth";
    public static final String HOME = ROOT + "/home";
    public static final String USER = AUTH + "/users";
    public static final String TOKEN = USER + "/tokens";
    public static final String ROLE = ROOT + "/roles";
}
