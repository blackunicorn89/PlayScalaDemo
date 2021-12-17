//package models
//
//import play.api.db.Database
//import play.api.libs.json.Json
//import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents, Request}
//import anorm.{SqlQuery, _}
//
//import javax.inject.Inject
//import scala.concurrent.{ExecutionContext, Future}
//
//
//
//class database  @Inject() (val controllerComponents: ControllerComponents,db: Database, databaseExecutionContext: ExecutionContext) extends BaseController {
//
//  implicit val ec: ExecutionContext = databaseExecutionContext
//
//  def validateUser(username: String, password: String): Boolean = {
//    users.get(username).map(_ == password).getOrElse(false)
//  }
//
//  // Create an SQL query
//  val selectCountries = SQL("Select * from Country")
//
//  // Transform the resulting Stream[Row] to a List[(String,String)]
//  val countries = selectCountries().map(row =>
//    row[String]("code") -> row[String]("name")
//  ).toList
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
