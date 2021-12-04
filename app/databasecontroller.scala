import play.db.anorm._

// Create an SQL query
val selectUser = SQL("Select * from user")

// Transform the resulting Stream[Row] as a List[(String,String)]
val users = selectUser().map(row =>
  row[String]("code") -> row[String]("name")
).toList