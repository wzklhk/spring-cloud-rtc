package com.example.access.auth.constant;

public class CommonConstant {
    public static final String CLIENT_ID = "client-id";
    public static final String CLIENT_SECRET = "client-secret";
    public static final String SCOPE = "all";
    public static final String[] AUTHORIZED_GRANT_TYPES = {
            "client_credentials",
            "password",
            "implicit",
            "authorization_code",
            "refresh_token"
    };
}
