package controllers

import play.api.db.Database
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
import java.sql.ResultSet
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.collection.mutable.ListBuffer

/**
 * This controller gets the list of the moomin mugs and their prices from the database,
 * extracts the values from the tuple List[(String, Int)] and puts
 * them to the separate lists.
 */
class moominMugs @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext

  /**
   * Extracts moomin mugs and their prices from the tuple List[(String, Int)] and puts
   * them to the separate lists.
   */
  def moominMugsToList: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>
    var cup = ListBuffer[String]()
    var price = ListBuffer[String]()

    for {
      resultDb <- getMoominMugsFromDatabase()
    }
    yield {
      /**
       * c=cup, p=price
       * Extracts mugs and their prices to the separate lists.
       */

        for ((c, p) <- resultDb){
          cup += c
          price += p.toString
        }

      /**
       * Had to create values because view moominMugs didn't accept variables.
       */
        val cups = cup.toList
        val prices = price.toList

        Ok(views.html.moominMugs(cups, prices))
      }
    }

  /**
   *Gets the moomin mugs and their prices from the database.
   */
  def getMoominMugsFromDatabase()(implicit databaseExecutionContext: ExecutionContext): Future[List[(String, Int)]] = {
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