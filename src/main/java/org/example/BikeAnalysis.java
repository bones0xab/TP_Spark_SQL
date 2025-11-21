package org.example;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import static org.apache.spark.sql.functions.*;

public class BikeAnalysis {
    public static void main(String[] args) {

        // --- 1. Data Loading & Exploration --- [cite: 20]
        SparkSession spark = SparkSession.builder()
                .appName("BikeSharingAnalysis")
                .master("local[*]") // Use local cores
                .getOrCreate();

        // Set log level to WARN to avoid clutter
        spark.sparkContext().setLogLevel("WARN");

        // Load the CSV file [cite: 21]
        Dataset<Row> df = spark.read()
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("data/bike_sharing.csv");

        System.out.println("--- Schema ---");
        df.printSchema(); // [cite: 25]

        System.out.println("--- First 5 Rows ---");
        df.show(5); // [cite: 26]

        System.out.println("Total Rentals: " + df.count()); // [cite: 27]

        // --- 2. Create a Temporary View --- [cite: 28]
        df.createOrReplaceTempView("bike_rentals_view"); // [cite: 29]

        // --- 3. Basic SQL Queries --- [cite: 30]
        System.out.println("\n--- 3.1 Rentals > 30 mins ---");
        spark.sql("SELECT * FROM bike_rentals_view WHERE duration_minutes > 30").show(5); // [cite: 31]

        System.out.println("--- 3.2 Start Station A ---");
        spark.sql("SELECT * FROM bike_rentals_view WHERE start_station = 'Station A'").show(5); // [cite: 32]

        System.out.println("--- 3.3 Total Revenue ---");
        spark.sql("SELECT SUM(price) as total_revenue FROM bike_rentals_view").show(); // [cite: 33]

        // --- 4. Aggregation Queries --- [cite: 34]
        System.out.println("--- 4.1 Count per Start Station ---");
        spark.sql("SELECT start_station, COUNT(*) as count FROM bike_rentals_view GROUP BY start_station").show(); // [cite: 35]

        System.out.println("--- 4.2 Average Duration per Station ---");
        spark.sql("SELECT start_station, AVG(duration_minutes) as avg_duration FROM bike_rentals_view GROUP BY start_station").show(); // [cite: 36]

        // --- 5. Time-Based Analysis --- [cite: 38]
        System.out.println("--- 5.2 Peak Hours ---");
        // Extract hour and count [cite: 39, 40]
        spark.sql("SELECT hour(start_time) as rental_hour, COUNT(*) as count " +
                "FROM bike_rentals_view " +
                "GROUP BY rental_hour " +
                "ORDER BY count DESC").show(5);

        System.out.println("--- 5.3 Most Popular Station (Morning 7-12) ---");
        spark.sql("SELECT start_station, COUNT(*) as count " +
                "FROM bike_rentals_view " +
                "WHERE hour(start_time) BETWEEN 7 AND 12 " +
                "GROUP BY start_station " +
                "ORDER BY count DESC " +
                "LIMIT 1").show(); // [cite: 41]

        // --- 6. User Behavior Analysis --- [cite: 42]
        System.out.println("--- 6.3 Age Group Analysis ---");
        // Using SQL CASE WHEN to bucket the ages as requested in the PDF [cite: 45, 46, 47, 48, 51, 52]
        spark.sql("SELECT " +
                "  CASE " +
                "    WHEN age BETWEEN 18 AND 30 THEN '18-30' " +
                "    WHEN age BETWEEN 31 AND 40 THEN '31-40' " +
                "    WHEN age BETWEEN 41 AND 50 THEN '41-50' " +
                "    ELSE '51+' " +
                "  END as age_group, " +
                "  COUNT(*) as count " +
                "FROM bike_rentals_view " +
                "GROUP BY age_group " +
                "ORDER BY count DESC").show();

        spark.stop();
    }
}
