package controllers

import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import java.sql.ResultSet
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

case class Product(
                    nimi: String,
                    price: Int)


class muumimukit  @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext

  def worksnot() = Action { implicit request: Request[AnyContent] =>

    val mukit = List("Joo", "Testi")
    val hah = List("hah", "hah2")

    Ok(views.html.muumimukit(mukit))
  }

  def works: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>
    for {
      resultDb <- doSomething()
    } yield {
      Ok(views.html.muumimukit(resultDb))
    }

  }


  def doSomething()(implicit databaseExecutionContext: ExecutionContext): Future[List[Int]] = {
    Future {
      db.withConnection { conn =>
        val resultSet:ResultSet = conn.prepareStatement("select, userid, nimi, hinta from muumimukit").executeQuery()
        new Iterator[Int] {
          Product(nimi = resultSet.getString("nimi"), price = resultSet.getInt("hinta"))
          def hasNext = resultSet.next()
          def next() = resultSet.getInt("userid")
        }.toList
      }
    }(databaseExecutionContext)
  }







}



