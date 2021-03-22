# Databases
**Easily create databases**

Installation **➞** [Installation](#installation)\
Usage preparation **➞** [Usage Preparation](#usage-preparation)\
Usage Examples **➞** [Usage Examples](#usage-examples)

# Installation

Maven:
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
```xml
<dependencies>
  <dependency>
      <groupId>com.github.jarnoboy404</groupId>
      <artifactId>Databases</artifactId>
      <version>2.0</version>
  </dependency>
</dependencies>
```
Gradle:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
```gradle
dependencies {
        implementation 'com.github.jarnoboy404:Databases:2.0'
}
```
# Usage preparation

1: Create your databaseConnection
```java
private DatabaseConnection databaseConnection;

@Override
public void onEnable() {
    try {
        databaseConnection = new DatabaseConnection("localhost", 3306, "MyDatabase", "root", "password", 4);
    }catch (HikariPool.PoolInitializationException e) {
        Bukkit.getConsoleSender().sendMessage("Failed to connect to localhost database");
        Bukkit.getPluginManager().disablePlugin(this);
        return;
    }
}
```
2: Create your database class
```java
public class TestDatabase extends DatabaseConnection {

    public TestDatabase(DatabaseConnection databaseConnection) {
        super(databaseConnection);
    }

}
```
3: Lets make your database class accessible
```java
public static TestDatabase testDatabase;
private DatabaseConnection databaseConnection;

@Override
public void onEnable() {
    try {
        databaseConnection = new DatabaseConnection("localhost", 3306, "MyDatabase", "root", "password", 4);
    }catch (HikariPool.PoolInitializationException e) {
        Bukkit.getConsoleSender().sendMessage("Failed to connect to localhost database");
        Bukkit.getPluginManager().disablePlugin(this);
        return;
    }
    testDatabase = new TestDatabase(databaseConnection);
}
```
# Usage Examples
NOTE: All your methods with database query's needs to be in your database class (in my case "TestDatabase")

Select:
```java
public String getUser(UUID uuid) {
    QueryParam queryParam = new QueryParam();
    queryParam.addQueryParam("uuid", uuid);

    DatabaseResult result = executeQuery("SELECT * FROM `playerdata`" + queryParam.getWhereQuery());

    String user = null;
    if(result.getResult()) user = result.getString("user");

    result.endResult();
    return user;
}
```
Select async method 1:
```java
public CompletableFuture<String> getUser(UUID uuid) {
    return CompletableFuture.supplyAsync(() -> {
        QueryParam queryParam = new QueryParam();
        queryParam.addQueryParam("uuid", uuid);
        
        DatabaseResult result = executeQuery("SELECT * FROM `playerdata`" + queryParam.getWhereQuery());

        String user = null;
        if(result.getResult()) user = result.getString("user");

        result.endResult();
        return user;
    });
}
```
Select async method 2
```java
public CompletableFuture<DatabaseResult> getUser(UUID uuid) {
    QueryParam queryParam = new QueryParam();
    queryParam.addQueryParam("uuid", uuid);

    return executeQueryAsync("SELECT * FROM `playerdata`" + queryParam.getWhereQuery());
}
```
Update:
```java
public void updateUser(UUID uuid) {
    QueryParam updateQuery = new QueryParam();
    updateQuery.addQueryParam("uuid", uuid);

    QueryParam whereQuery = new QueryParam();
    whereQuery.addQueryParam("uuid", uuid);

    executeUpdate(updateQuery.getUpdateQuery("playerdata", whereQuery));
}
```
Update async:
```java
public CompletableFuture<Void> updateUser(UUID uuid) {
    QueryParam updateQuery = new QueryParam();
    updateQuery.addQueryParam("uuid", uuid);

    QueryParam whereQuery = new QueryParam();
    whereQuery.addQueryParam("uuid", uuid);

    return executeUpdateAsync(updateQuery.getUpdateQuery("playerdata", whereQuery));
}
```
Insert:
```java
public void createUser(UUID uuid, String user) {
    QueryParam queryParam = new QueryParam();
    queryParam.addQueryParam("uuid", uuid);
    queryParam.addQueryParam("user", user);

    executeUpdate(queryParam.getInsertQuery("playerdata"));
}
```
Insert async:
```java
public CompletableFuture<Void> createUser(UUID uuid, String user) {
    QueryParam queryParam = new QueryParam();
    queryParam.addQueryParam("uuid", uuid);
    queryParam.addQueryParam("user", user);

    return executeUpdateAsync(queryParam.getInsertQuery("playerdata"));
}
```

Good luck!
