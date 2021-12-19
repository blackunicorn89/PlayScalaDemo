package controllers

import akka.stream.impl.fusing.Map
import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import java.sql.ResultSet
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import anorm.{Row, SQL, SimpleSql, SqlQuery, ~}
import anorm.SqlParser.get

class muumit  @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext
  def works: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>
    for {
      resultDb <- doSomething()

    } yield {

     Ok(views.html.muumimukit(resultDb))
    }

  }

  def doSomething()(implicit databaseExecutionContext: ExecutionContext): Future[List[(String)]] = {
    Future {
      db.withConnection { conn =>
        val resultSet:ResultSet = conn.prepareStatement("select nimi, hinta from muumimukit").executeQuery()
        new Iterator[(String)] {
          def hasNext: Boolean = resultSet.next()
          def next(): (String) = resultSet.getString("nimi")
        }.toList
      }
    }(databaseExecutionContext)
  }








}



