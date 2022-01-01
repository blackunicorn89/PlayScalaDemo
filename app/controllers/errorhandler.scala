package controllers

import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._

import java.lang.ProcessBuilder
import scala.concurrent._
import javax.inject.Singleton

/**
 * This error handler handles some general errors like database connection doesn't exist, page not found etc.
 */

@Singleton
class ErrorHandler extends HttpErrorHandler {

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = {
    Future.successful(
      Redirect(routes.HomeController.index()).flashing("generror" -> "Oops! Something went wrong")
    )
  }

  def SQLSQLTransientConnectionException(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      Redirect(routes.HomeController.index()).flashing("dberror" -> "A database connection doesn't exist. Please check the database connection settings on the application config file")
    )
  }

  def onServerError(request: RequestHeader, exception: Throwable) = {
    Future.successful(
      Redirect(routes.HomeController.index()).flashing("generror" -> "Oops! Something went wrong")
    )
  }
}
