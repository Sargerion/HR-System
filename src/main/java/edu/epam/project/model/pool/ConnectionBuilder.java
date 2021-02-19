package edu.epam.project.model.pool;

import com.mysql.cj.jdbc.Driver;
import edu.epam.project.exception.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionBuilder {

    private static final Logger logger = LogManager.getLogger();
    private static final String PROPERTIES_PATH = "/property/database.properties";
    private static final String URL = "url";
    private static final Properties properties = new Properties();
    private String URL_VALUE;

    ConnectionBuilder() {
        try (InputStream input = ConnectionBuilder.class.getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
            DriverManager.registerDriver(new Driver());
            URL_VALUE = (String) properties.get(URL);
        } catch (IOException | SQLException e) {
            logger.error(e);
        }
    }

    Connection create() throws ConnectionException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL_VALUE, properties);
        } catch (SQLException e) {
            logger.error(e);
            throw new ConnectionException(e);
        }
        return connection;
    }
}