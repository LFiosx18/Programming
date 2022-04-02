package database;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnect extends DBParamConnect{
    public DBConnect() {
        try {
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            JSch jsch = new JSch();
            Session session = jsch.getSession(SV_LOGIN, SV_ADDR, SSH_PORT);
            session.setPassword(SV_PASS);
            session.setConfig(config);
            session.connect();
            session.setPortForwardingL(FORWARDING_PORT, DB_HOST, DB_PORT);
            System.out.println("ok");
        } catch (Exception e) {
            System.err.println("Wrong format of env, please follow this format:\n" +
                    "For ssh tunnel:\n" +
                    "ssh -p SSH_PORT SV_LOGIN@SV_ADDR\n" +
                    "Server login: SV_LOGIN\nServer password: SV_PASS\n" +
                    "For database connection:\n" +
                    "DB_BASE + DB_HOST + \":\" + FORWARDING_PORT + \"/\" + DB_NAME\n" +
                    "FORWARDING_PORT - FORWARDING PORT"
            );
        }
    }

    public Connection connect() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(DB_BASE + "localhost:" + FORWARDING_PORT + "/" + DB_NAME, SV_LOGIN, SV_PASS);
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found");
        } catch (SQLException e) {
            System.err.println("Connection to database failed");
        }
        return null;
    }
}
