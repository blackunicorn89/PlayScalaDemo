package controllers

import akka.stream.impl.fusing.Map
import play.api.db.Database
import play.api.libs.json.{JsArray, JsBoolean, JsNull, JsNumber, JsObject, JsString, Json}
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import java.sql.ResultSet
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


case class mukit  (
  nimi: String,
  hinta: Int
)

object muki {
  implicit val jsonFormat = Json.format[mukit]
  implicit val joo = List[mukit("nimi")]
  implicit val pa = List[mukit("hinta")]




}





class muumit  @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext

  def works: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>

    for {
      resultDb <- doSomething()

    } yield {

      val resultJsonObj = Json.toJson(resultDb)
      val muki = resultDb.ni


      Ok(resultJsonObj)

    }



    }



    def doSomething()(implicit databaseExecutionContext: ExecutionContext): Future[List[(mukit)]] = {
    Future {
      db.withConnection { conn =>
        val resultSet:ResultSet = conn.prepareStatement("select nimi, hinta from muumimukit").executeQuery()
        new Iterator[(mukit)] {
          def hasNext: Boolean = resultSet.next()
          def next(): (mukit) = {
            mukit(resultSet.getString("nimi"),resultSet.getInt("hinta"))

          }



        }.toList
      }
    }(databaseExecutionContext)
  }








}



