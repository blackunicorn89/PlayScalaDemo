package controllers



import models.TaskListInMemory

import javax.inject._
import play.api._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request, Action, _}
import play.api.db.Database

import java.sql.ResultSet
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.collection.mutable.ListBuffer


@Singleton
class login @Inject()(cc: ControllerComponents, db: Database, databaseExecutionContext: ExecutionContext) extends AbstractController(cc){

  implicit val ec: ExecutionContext = databaseExecutionContext

  //kokeille tehdä funktio, joka ottaa argumenttina nimen ja salasanan
  def testi(user: List[(String, String)]) = Action { implicit request: Request[AnyContent] =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      for ((p, u) <- resultDb) {
        if (p == username && u == password) {
          Redirect(routes.muumit.works()).withSession("username" -> username)
        }
        else {
          Redirect(routes.HomeController.index()).flashing("error" -> "Error. Invalid username or password. Please try againa ")
        }
      }
    }.getOrElse(Redirect(routes.HomeController.index()))

  }

  def validateLogin: Action[AnyContent] = Action async    { implicit request: Request[AnyContent] =>

    for {
      resultDb <- getUserFromDb()
    } yield{

      testi(resultDb)

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
