package org.example;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BikeDataGenerator {
    public static void main(String[] args) {
        String filePath = "data/bike_sharing.csv";
        String[] stations = {"Station A", "Station B", "Station C", "Station D", "Station E"};
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try (FileWriter writer = new FileWriter(filePath)) {
            // Write Header
            writer.append("rental_id,user_id,age,gender,start_time,end_time,start_station,end_station,duration_minutes,price\n");

            // Generate 500 rows
            for (int i = 1; i <= 500; i++) {
                int userId = 1000 + random.nextInt(9000);
                int age = 18 + random.nextInt(48); // Age 18-65
                String gender = random.nextBoolean() ? "M" : "F";

                // Time generation
                LocalDateTime startTime = LocalDateTime.of(2025, 5, 1, 6, 0).plusMinutes(random.nextInt(43200));
                int duration = 5 + random.nextInt(115);
                LocalDateTime endTime = startTime.plusMinutes(duration);

                String startStation = stations[random.nextInt(stations.length)];
                String endStation = stations[random.nextInt(stations.length)];

                double price = 2.0 + (duration * 0.10); // Base $2.00 + $0.10/min

                writer.append(String.format("%d,%d,%d,%s,%s,%s,%s,%s,%d,%.2f\n",
                        i, userId, age, gender,
                        startTime.format(formatter), endTime.format(formatter),
                        startStation, endStation, duration, price));
            }
            System.out.println("CSV generated successfully at: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}