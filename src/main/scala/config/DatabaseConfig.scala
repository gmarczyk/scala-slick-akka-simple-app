package config

import slick.jdbc.JdbcBackend.Database

object DatabaseConfig {
  val postgresDb = Database.forConfig("postgres")
}
