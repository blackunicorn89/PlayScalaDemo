package controllers


import play.api.db.Database
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import java.sql.ResultSet
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.collection.mutable.ListBuffer


















class muumit  @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext



  def works: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>
    var joo = ListBuffer[String]()
    var hinta = ListBuffer[String]()


    for {

      resultDb <- doSomething()

    }


  yield {

      for ((a, b ) <- resultDb){
        joo += a
        hinta += b.toString
      }

      val nimet = joo.toList
      val hinnat = hinta.toList

      Ok(views.html.muumimukit(nimet, hinnat))



      //val result = resultDb.unapply(p)

      //val resultJsonObj = Json.toJson(resultDb)
      //val muki = resultDb.ni




      //Ok(resultJsonObj)

    }



    }



    def doSomething()(implicit databaseExecutionContext: ExecutionContext): Future[List[(String, Int)]] = {
    Future {
      db.withConnection { conn =>
        val resultSet:ResultSet = conn.prepareStatement("select nimi, hinta from muumimukit").executeQuery()
        new Iterator[(String, Int)] {
          def hasNext: Boolean = resultSet.next()
          def next(): (String, Int) = {
            (resultSet.getString("nimi"),resultSet.getInt("hinta"))

          }



        }.toList
      }
    }(databaseExecutionContext)
  }








}



