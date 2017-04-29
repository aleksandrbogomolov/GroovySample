package groovy.com.aleksandrbogomolov

import groovy.com.aleksandrbogomolov.configuration.DataBaseConfiguration
import groovy.com.aleksandrbogomolov.helper.Helper
import groovy.com.aleksandrbogomolov.repository.Repository
import groovy.sql.Sql
import org.codehaus.groovy.runtime.InvokerHelper

import java.util.logging.Logger

class Main extends Script {

  private logger = Logger.getLogger(Main.class.name)

  private properties = Helper.readProperties()

  @Override
  def run() {
    def configuration = initDataBaseConfiguration()
    def repository = new Repository(configuration.sql)
    if (repository.tableRowCount() > 0) {
      repository.clearTable()
    }
    repository.insertRowsIntoTable(configuration.numberOfRecord)
    def rows = repository.getAllRowsFromTable()
    repository.closeSql()

    def firstXmlPath = properties.getProperty("firstxmlpath")
    def secondXmlPath = properties.getProperty("secondxmlpath")
    def stylesheetpath = properties.getProperty("stylesheetpath")

    Helper.createXml(rows, firstXmlPath)
    Helper.transformXml(stylesheetpath, firstXmlPath, secondXmlPath)
    logger.info("Result = ${Helper.parseXml(secondXmlPath)}.")
  }

  DataBaseConfiguration initDataBaseConfiguration() {
    def conf = new DataBaseConfiguration()
    conf.dataBaseUrl = properties.getProperty("url")
    conf.dataBaseName = properties.getProperty("database")
    conf.userName = properties.getProperty("user")
    conf.password = properties.getProperty("password")
    conf.driver = properties.getProperty("driver")
    conf.numberOfRecord = properties.getProperty("recordsnumber") as int
    conf.sql = Sql.newInstance(conf.dataBaseUrl + conf.dataBaseName, conf.userName, conf.password, conf.driver)
    conf
  }

  static void main(String[] args) {
    InvokerHelper.runScript(Main, args)
  }
}
