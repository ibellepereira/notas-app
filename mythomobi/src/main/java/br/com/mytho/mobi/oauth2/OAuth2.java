package br.com.mytho.mobi.oauth2;

import java.util.Properties;

/**
 * Created by leonardocordeiro on 22/09/16.
 */

public class OAuth2 {

    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String CLIENT_SECRET = "CLIENT_SECRET";
    public static final String BASE_URL = "BASE_URL";
    public static final String SCOPE = "SCOPE";

    private static Properties properties;

    public static void setProperties(Properties props) {
        properties = props;
    }

    public static Properties getProperties() {
        if(properties == null)
            throw new RuntimeException("did you set OAuth2.setProperties?");
        else return properties;
    }
}
