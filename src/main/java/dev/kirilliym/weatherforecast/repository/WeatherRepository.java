package dev.kirilliym.weatherforecast.repository;

import dev.kirilliym.weatherforecast.model.DatabaseConnection;

import java.sql.*;
import java.util.Random;

public class WeatherRepository {

    private final Random random = new Random();

    public int getTemperature(String city) throws SQLException {
        int cityId = getCityId(city);

        if (cityId != -1) {
            return getCityTemperature(cityId);
        }

        return newCityTemperature(city);
    }

    private int getCityId(String city) throws SQLException {
        String sql = "SELECT id FROM City WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return -1;
    }

    private int getCityTemperature(int cityId) throws SQLException {
        String sql = "SELECT temperature FROM Weather WHERE city_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cityId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("temperature");
            }
        }
        return -1;
    }

    private int newCityTemperature(String city) throws SQLException {
        int cityId = addCity(city);
        int newTemperature = random.nextInt(60) - 30;
        addWeatherData(cityId, newTemperature);
        return newTemperature;
    }

    private int addCity(String city) throws SQLException {
        String sql = "INSERT INTO City(name) VALUES (?) RETURNING id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, city);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        throw new SQLException("не удалось добавить город в базу данных");
    }

    private void addWeatherData(int cityId, int temperature) throws SQLException {
        String sql = "INSERT INTO Weather(city_id, temperature) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cityId);
            stmt.setInt(2, temperature);
            stmt.executeUpdate();
        }
    }
}
