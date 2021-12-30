package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.db.Database

import java.sql.{ResultSet, SQLTransientConnectionException}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents, db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  implicit val ec: ExecutionContext = databaseExecutionContext


  def validateLogin: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>

    var input_username = ""
    var input_password = ""
    var db_username = ""
    var db_password = ""


    for {
      resultDb <- getUserFromDb()
    } yield {

      val postVals = request.body.asFormUrlEncoded
      postVals.map { args =>
        input_username = args("username").head
        input_password = args("password").head
      }.getOrElse(Redirect(routes.HomeController.index()))

      for ((u, p) <- resultDb) {
        db_username = u
        db_password = p

      }
      if (db_username == input_username && db_password == input_password) {
        Redirect(routes.muumit.works()) withSession ("username" -> input_username)
      } else
        Redirect(routes.HomeController.index()).flashing("error" -> "Error. Invalid username or password. Please try again ")

    }

  }



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


  def logout = Action  {
    Redirect(routes.HomeController.index()).withNewSession
  }
}
