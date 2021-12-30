package controllers

import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._

import java.lang.ProcessBuilder
import scala.concurrent._
import javax.inject.Singleton;

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Status(statusCode)("A client error occurred: " + message)
    )
  }

  def SQLSQLTransientConnectionException(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      Redirect(routes.HomeController.index())

    )
  }



  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      Redirect(routes.HomeController.index()).flashing("dberror" -> "A database connection doesn't exist. Please check the database connection settings on the application config file")
    )
  }
}
