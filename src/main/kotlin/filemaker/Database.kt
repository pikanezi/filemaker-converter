package filemaker

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException

class Database(
    private val host: String,
    private val database: String,
    private val username: String,
    private val password: String
) {
    private fun getConnection(): Connection {
        val url = "jdbc:filemaker://$host/$database?JDBCOptions=ssl=false"
        val urls = listOf(
            "jdbc:filemaker://$host/$database?JDBCOptions=ssl=true;sslCipherSuite=TLS_AES_256_GCM_SHA384",
            "jdbc:filemaker://$host/$database?JDBCOptions=ssl=true;verifyCertificates=false",
            "jdbc:filemaker://$host/$database?JDBCOptions=ssl=false"
        )
        for (urll in urls) {
            try {
                println("Connecting to database $url")
                val connection = DriverManager.getConnection(urll, username, password)
                println("??")
                val result = connection.metaData.getTables(null, null, null, null)
                while (result.next()) {
                    println(result.toString())
                }
                result.close()
            } catch (e: SQLException) {
                println("Connection failed: ${e.message}")
            }
        }


        try {
            return DriverManager.getConnection(url, username, password)
        } catch (e: SQLException) {
            println(e)
            throw e
        }
    }

    fun executeQuery(query: String): ResultSet {
        val connection = getConnection()
        println(connection.metaData.getTables(null, null, null, null))
        val statement = connection.createStatement()
        return statement.executeQuery(query)
    }

    fun getTables() {
        val connection = getConnection()
        val result = connection.metaData.getTables(null, null, null, null)
        while (result.next()) {
            println(result.toString())
        }
        result.close()
    }

    fun example() {
        try {
            // Example query
            println("Executing query")
            val resultSet = executeQuery("SELECT * FROM YourTable")
            println("Query executed")
            while (resultSet.next()) {
                // Access columns by name or index
                val id = resultSet.getInt("ID")
                val name = resultSet.getString("Name")
                println("ID: $id, Name: $name")
            }

            resultSet.close()
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}