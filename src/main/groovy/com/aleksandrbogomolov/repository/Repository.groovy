package groovy.com.aleksandrbogomolov.repository

import groovy.sql.Sql

import java.util.logging.Logger

class Repository {

  private final logger = Logger.getLogger(Repository.class.name)

  private final Sql sql

  Repository(sql) {
    this.sql = sql
  }

  int tableRowCount() {
    def result = sql.firstRow("SELECT COUNT(*) AS cont FROM test").cont
    logger.info("Found $result rows in the table 'TEST'.")
    result
  }

  void clearTable() {
    def result = sql.executeUpdate("DELETE FROM test")
    logger.info("Deleted $result rows from table'TEST'.")
  }

  void insertRowsIntoTable(int numberOfRows) {
    sql.withTransaction {
      int[] batch = sql.withBatch { stmt ->
        (1..numberOfRows).each {
          stmt.addBatch("INSERT INTO test(field) VALUES($it)")
        }
      }
      logger.info("Inserted ${batch.length} rows into table 'TEST'.")
    }
  }

  List<Integer> getAllRowsFromTable() {
    def result = []
    sql.eachRow("SELECT * FROM test") {
      result << (int) it.field
    }
    logger.info("Selected ${result.size()} rows from table 'TEST'.")
    result
  }

  void closeSql() {
    sql.close()
  }
}
