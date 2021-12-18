package controllers

import play.api.db.Database
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import java.sql.ResultSet
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

/*case class Product(
                    nimi: String,
                    price: Int)*/


class muumimukit  @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext

  def notworks = Action { implicit request: Request[AnyContent] =>

    val mukit = List("Joo", "Testi", "pöö")
    val hah = List("hah", "hah2", "pöö")

    Ok(views.html.muumimukit(mukit, hah))

  }

  /*def works: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>
    for {
      resultDb <- doSomething()

    } yield {

     Ok(views.html.muumimukit(resultDb))
      val resultJsonObj = Json.toJson(resultDb)
      Ok(resultJsonObj)
    }

  }


  def doSomething()(implicit databaseExecutionContext: ExecutionContext): Future[List[(String, Int)]] = {
    Future {
      db.withConnection { conn =>
        val resultSet:ResultSet = conn.prepareStatement("select nimi, hinta from muumimukit").executeQuery()
        new Iterator[(String, Int)] {
          def hasNext: Boolean = resultSet.next()
          def next(): (Int, String) = (resultSet.getInt("numero"),resultSet.getString("nimi"))
        }.toList
      }
    }(databaseExecutionContext)
  }*/








}



