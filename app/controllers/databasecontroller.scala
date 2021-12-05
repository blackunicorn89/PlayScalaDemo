import anorm._

database.withConnection { implicit c =>
  val test: String = SQL("select user from users where userid = 1").executeQuery()
}