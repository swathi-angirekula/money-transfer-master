package com.mycompany.revolutmoneytransfer.db;

import com.mycompany.revolutmoneytransfer.exceptions.ImpossibleOperationExecution;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class H2DataSource {
    private static final Logger log = LoggerFactory.getLogger(H2DataSource.class);

    private static final HikariDataSource ds;

    static {
        ds = new HikariDataSource();
        //initializing the in-memry H2 database and initialize it by the schema and some initial data
        ds.setJdbcUrl("jdbc:h2:mem:test;" +
                "INIT=RUNSCRIPT FROM 'classpath:db_schema/schema.sql'\\;RUNSCRIPT FROM 'classpath:db_schema/init_data.sql';" +
                "TRACE_LEVEL_FILE=4");
        //TODO login and password should be provided trough system variables
        ds.setUsername("sa");
        ds.setPassword("sa");

        ds.setAutoCommit(false);

        log.info("The database has been initialized");
    }

    private H2DataSource() {}

    public static Connection getConnection() throws ImpossibleOperationExecution {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new ImpossibleOperationExecution(e);
        }

    }
}
