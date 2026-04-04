package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseCleaner {

    private DatabaseCleaner() {}

    private static final String DB_URL = System.getProperty(
            "db.url", "jdbc:postgresql://localhost:5433/Triage"
    );
    private static final String DB_USER = System.getProperty(
            "db.user", "postgres"
    );
    private static final String DB_PASSWORD = System.getProperty(
            "db.password", "your_password_here"
    );

    public static void cleanAll() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {
            stmt.execute("TRUNCATE TABLE public.constantes_vitales RESTART IDENTITY CASCADE");
            stmt.execute("TRUNCATE TABLE public.pacientes RESTART IDENTITY CASCADE");
        }
    }
}
