package hk.com.mtr.pcis.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class EnvironmentUtil {

    public static String EJB_SERVER_TYPE_JBOSS = "JBoss";

    public static String EJB_SERVER_TYPE_WEBSPHERE = "Websphere";

    public static String DATABASE_TYPE = "Oracle";

    public static String EJB_SERVER_TYPE = "Websphere";

    public static String EJB_JNDI_PATTERN;

    public static String EJB_JNDI_PATTERN_DAO;

    static {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("env.properties");
        Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        EJB_SERVER_TYPE = p.getProperty("ejb.server.type");
        DATABASE_TYPE = p.getProperty("database.type");
        EJB_JNDI_PATTERN = p.getProperty("ejb.jndi.pattern");
        EJB_JNDI_PATTERN_DAO = p.getProperty("ejb.jndi.pattern.dao");
    }

    public static boolean isJBossServer() {
        return EJB_SERVER_TYPE_JBOSS.equalsIgnoreCase(EJB_SERVER_TYPE);
    }

    public static boolean isWebsphere() {
        return EJB_SERVER_TYPE_WEBSPHERE.equalsIgnoreCase(EJB_SERVER_TYPE);
    }

}
