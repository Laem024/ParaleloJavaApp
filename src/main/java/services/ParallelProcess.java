package services;

import db.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelProcess {

    public void runProcesses() {
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // Tarea 1: Consulta de nombres
        Runnable task1 = () -> {
            try (Connection connection = DatabaseConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT name FROM users");
                while (rs.next()) {
                    System.out.println("Nombre: " + rs.getString("name"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Tarea 2: Consulta de correos electrónicos
        Runnable task2 = () -> {
            try (Connection connection = DatabaseConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT email FROM users");
                while (rs.next()) {
                    System.out.println("Email: " + rs.getString("email"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Tarea 3: Contar el total de usuarios
        Runnable task3 = () -> {
            try (Connection connection = DatabaseConnection.getConnection();
                 Statement stmt = connection.createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM users");
                if (rs.next()) {
                    System.out.println("Total de usuarios: " + rs.getInt("total"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // Ejecutar tareas en paralelo
        executorService.submit(task1);
        executorService.submit(task2);
        executorService.submit(task3);

        // Apagar el executor después de terminar
        executorService.shutdown();
    }
}
