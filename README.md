# TP_Spark_SQL

````markdown
# 🚴‍♂️ Bike Sharing Analysis with Spark SQL

A distributed systems project using **Apache Spark** and **Java** to analyze public bike-sharing data. This project demonstrates Data Engineering workflows including data generation, DataFrame manipulation, SQL View creation, and complex aggregation queries.

## 📋 Project Overview

The goal of this project is to extract insights from a dataset of bike rental transactions. It uses Spark SQL to answer business questions such as:
* Identifying peak usage hours.
* Calculating total revenue.
* Analyzing user demographics (Age/Gender).
* Determining station popularity.

## 🛠️ Technologies & Tools

* **Language:** Java 17 (Required)
* **Framework:** Apache Spark SQL 3.5.0
* **Build Tool:** Maven
* **IDE:** IntelliJ IDEA

## 📂 Project Structure

```text
SparkBikeAnalysis/
│
├── src/main/java/org/example/
│   ├── DataGenerator.java       # Generates the mock 'bike_sharing.csv' dataset
│   └── BikeAnalysis.java        # Main Spark application with SQL queries
│
├── pom.xml                      # Maven dependencies (Spark SQL)
└── README.md                    # Project documentation
````

## 🚀 Setup & Installation

1.  **Clone the repository**:

    ```bash
    git clone [<your-repo-url>](https://github.com/bones0xab/TP_Spark_SQL.git)
    cd SparkBikeAnalysis
    ```

2.  **Install Dependencies**:
    Ensure Maven downloads the Spark libraries.

    ```bash
    mvn clean install
    ```

## ⚙️ How to Run

### Step 1: Generate the Data

Since the dataset is not committed to the repo (to save space), you must generate it first.

1.  Run the `DataGenerator` class.
2.  This will create a file named `bike_sharing.csv` in the project root.

### Step 2: Configure Java 17+ (Crucial\!)

Apache Spark 3.5 requires specific internal access to Java modules when running on Java 17 or newer. You must add the following **VM Options** to your Run Configuration in IntelliJ:

```text
--add-opens=java.base/java.lang=ALL-UNNAMED
--add-opens=java.base/java.lang.invoke=ALL-UNNAMED
--add-opens=java.base/java.lang.reflect=ALL-UNNAMED
--add-opens=java.base/java.io=ALL-UNNAMED
--add-opens=java.base/java.net=ALL-UNNAMED
--add-opens=java.base/java.nio=ALL-UNNAMED
--add-opens=java.base/java.util=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent=ALL-UNNAMED
--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED
--add-opens=java.base/sun.nio.ch=ALL-UNNAMED
--add-opens=java.base/sun.nio.cs=ALL-UNNAMED
--add-opens=java.base/sun.security.action=ALL-UNNAMED
--add-opens=java.base/sun.util.calendar=ALL-UNNAMED
--add-opens=java.security.jgss/sun.security.krb5=ALL-UNNAMED
```

*In IntelliJ:* `Run` -\> `Edit Configurations` -\> `Modify options` -\> `Add VM options`.

### Step 3: Run the Analysis

Run the `BikeAnalysis` class. The console will output the results of the following SQL queries:

  * Schema & Data Preview.
  * Rentals \> 30 minutes.
  * Total Revenue calculation.
  * Station Aggregates (Count & Average Duration).
  * Peak Hour Identification.
  * User Age Grouping.

## 📊 Sample Output

```text
--- 5.2 Peak Hours ---
+-----------+-----+
|rental_hour|count|
+-----------+-----+
|         17|   45|
|         18|   42|
|          8|   38|
+-----------+-----+

--- 6.3 Age Group Analysis ---
+---------+-----+
|age_group|count|
+---------+-----+
|    18-30|  150|
|      51+|  120|
+---------+-----+

<!-- end list -->

```
