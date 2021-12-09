package controllers

import javax.inject._
import play.api._
import play.api.mvc.ControllerHelpers.TODO
import play.api.mvc._
import anorm._

class Muumit {
  def result = TODO
  
  database.withConnection { implicit c =>
  val _: Boolean = SQL("Select 1").execute()
}
}
