package models


import play.api.db.Database
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}

import java.sql.ResultSet
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.collection.mutable.ListBuffer

import collection.mutable


















class database  @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {

  implicit val ec: ExecutionContext = databaseExecutionContext



  def handleUserData: Action[AnyContent] = Action async { implicit request: Request[AnyContent] =>



    for {

      resultDb <- getUserFromDb()

    }


    yield {
      var user = ListBuffer[String]()
      var password = ListBuffer[String]()

      for ((a, b ) <- resultDb){
        user += a
        password += b
      }

      val usernames = user.toString()
      val passwords = password.toString()

      def validateUser(username: String, password: String): Boolean = {
        users.get(username).map(_ == password).getOrElse(false)






      //val result = resultDb.unapply(p)

      //val resultJsonObj = Json.toJson(resultDb)
      //val muki = resultDb.ni




      //Ok(resultJsonObj)

    }



  }





  def getUserFromDb()(implicit databaseExecutionContext: ExecutionContext): Future[List[(String, String)]] = {
    Future {
      db.withConnection { conn =>
        val resultSet:ResultSet = conn.prepareStatement("select username, password from users").executeQuery()
        new Iterator[(String, String)] {
          def hasNext: Boolean = resultSet.next()
          def next(): (String, String) = {
            (resultSet.getString("username"),resultSet.getString("password"))

          }



        }.toList
      }
    }(databaseExecutionContext)
  }








}



