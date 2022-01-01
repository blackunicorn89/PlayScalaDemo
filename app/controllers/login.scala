package controllers

import javax.inject._
import play.api.mvc._
import play.api.db.Database
import java.sql.ResultSet
import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller gets user information from the database and validates the login.
 */
@Singleton
class login @Inject()(val controllerComponents: ControllerComponents, db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext

  /**
   * Validates the login.
   */
  def validateLogin: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>

    var input_username = ""
    var input_password = ""
    var db_username = ""
    var db_password = ""

    for {
      resultDb <- getUserFromDb()
    } yield {

      /**
       * Gets the the login data sent via post from the index.scala.html page
       */
      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        input_username = args("username").head
        input_password = args("password").head
      }.getOrElse(Redirect(routes.HomeController.index()))

      /**
       * Extracts the user data from Tuple List[(String, String)]. The database contains only one user.
       */
      for ((u, p) <- resultDb) {
        db_username = u
        db_password = p
      }

      /**
       * Validates the login. If the user information given on index.scala.html page matches the user data
       * in the database, the list of the moomin cups is shown. Otherwise the user gets an error message and
       * is redirected back to the home page.
       */
      if (db_username == input_username && db_password == input_password) {
        Redirect(routes.moominMugs.moominMugsToList()).withSession("username" -> input_username)
      } else
        Redirect(routes.HomeController.index()).flashing("error" -> "Error. Invalid username or password. Please try again ")
    }
  }

  /**
   * Gets the username and password from the database.
   */
  def getUserFromDb()(implicit databaseExecutionContext: ExecutionContext): Future[List[(String, String)]] = {

    Future {
      db.withConnection { conn =>
        val resultSet: ResultSet = conn.prepareStatement("select username, password from users").executeQuery()
        new Iterator[(String, String)] {
          def hasNext: Boolean = resultSet.next()
          def next(): (String, String) = {
            (resultSet.getString("username"), resultSet.getString("password"))
          }
        }.toList
      }
    }(databaseExecutionContext)
  }

  /**
   * Defines the logout method.
   */
  def logout = Action  {
    Redirect(routes.HomeController.index()).withNewSession
  }
}
