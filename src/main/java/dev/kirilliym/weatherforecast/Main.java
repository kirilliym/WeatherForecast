package dev.kirilliym.weatherforecast;

import dev.kirilliym.weatherforecast.exception.InvalidCityNameException;
import dev.kirilliym.weatherforecast.service.WeatherService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        WeatherService weatherService = new WeatherService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Weather Forecast Service");
        System.out.println("Enter city name (only Russian letters, or 'exit' to quit):");

        while (true) {
            try {
                System.out.print("> ");
                String city = scanner.nextLine().trim();

                if (city.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }

                int temperature = weatherService.getCityTemperature(city);
                System.out.printf("Temperature in %s: %d°C\n", city, temperature);
            } catch (InvalidCityNameException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }

        scanner.close();
    }
}