package com.sparta.engineering50;

import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TimeSimulatorTest {
    @Test
    void checkTimerRuns() throws InterruptedException {
        TimeSimulator timeSim = new TimeSimulator();
        timeSim.initialiseTimeSimulator(6);
        Thread.currentThread().join(6000);
        assertEquals(6, timeSim.getCount());
    }
    @Test
    void checkFieldTickWorks() throws InterruptedException {
        TimeSimulator timeSimulator = new TimeSimulator();

        Statement statement = null;
        ResultSet results = null;
        int total = 0;

        Database.connectToDb();
        Database.clearDatabase();
        Database.createInitialRabbits();

        try {
            statement = Database.connection.createStatement();
            results = statement.executeQuery("SELECT COUNT(ID) FROM rabbit");

            results.next();
            total = results.getInt(1);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            try { if (results != null) results.close(); } catch (Exception e) {};
            try { if (statement != null) statement.close(); } catch (Exception e) {};
        }

        timeSimulator.initialiseTimeSimulator(30);
        Thread.currentThread().join(30000);
        assertTrue(total>10);
    }
}