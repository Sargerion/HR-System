package edu.epam.project.model.pool;

import com.mysql.cj.jdbc.Driver;
import edu.epam.project.exception.ConnectionException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Class ConnectionBuilder, used to register mysql jdbc driver and load database properties.
 * @author Sargerion.
 */
class ConnectionBuilder {

    private static final Logger logger = LogManager.getLogger();
    private static final String PROPERTIES_PATH = "/property/database.properties";
    private static final String URL = "url";
    private static final Properties properties = new Properties();
    private final String urlValue;

    /**
     * Instantiates a new Connection builder.
     */
    ConnectionBuilder() {
        try (InputStream input = ConnectionBuilder.class.getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
            DriverManager.registerDriver(new Driver());
            urlValue = (String) properties.get(URL);
        } catch (IOException | SQLException e) {
            logger.fatal(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Create connection.
     *
     * @return the created connection.
     * @throws ConnectionException if database throws SQLException.
     */
    Connection create() throws ConnectionException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(urlValue, properties);
        } catch (SQLException e) {
            logger.error(e);
            throw new ConnectionException(e);
        }
        return connection;
    }
}