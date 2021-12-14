package controllers

import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class muumimukit  @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext

  def works() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.muumimukit())
  }

  def result: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>
    for {
      resultDb <- listMukit()
    } yield {
      Ok(Json.parse(s"""{"result":"$resultDb"}"""))
    }

  }

  def listMukit()(implicit databaseExecutionContext: ExecutionContext): Future[String] = {
    Future {
      db.withConnection { conn =>
        conn.prepareStatement("select nimi from muumimukit").executeQuery().getString("nimi")
      }
    }(databaseExecutionContext)
  }




}



