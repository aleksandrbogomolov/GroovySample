package groovy.com.aleksandrbogomolov.configuration

import groovy.sql.Sql

class DataBaseConfiguration {

  String dataBaseUrl

  String dataBaseName

  String userName

  String password

  String driver

  int numberOfRecord

  Sql sql

  @Override
  String toString() {
    return "DataBaseConfiguration{" +
        "dataBaseUrl='" + dataBaseUrl + '\'' +
        ", dataBaseName='" + dataBaseName + '\'' +
        ", userName='" + userName + '\'' +
        ", password='" + password + '\'' +
        ", driver='" + driver + '\'' +
        ", numberOfRecord=" + numberOfRecord +
        '}'
  }
}
