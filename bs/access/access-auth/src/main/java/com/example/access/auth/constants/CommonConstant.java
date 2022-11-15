package com.example.access.auth.constants;

public class CommonConstant {
    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String SCOPE = "all";
    public static final String[] AUTHORIZED_GRANT_TYPES = {
            "client_credentials",
            "password",
            "implicit",
            "authorization_code",
            "refresh_token"};
}
