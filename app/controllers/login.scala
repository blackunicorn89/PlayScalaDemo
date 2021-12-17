package controllers



import models.TaskListInMemory

import javax.inject._
import play.api._
import play.api.mvc._

@Singleton
class login @Inject()(cc: ControllerComponents) extends AbstractController(cc){

  def validateLogin = Action { request =>

    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val username = args("username").head
      val password = args("password").head
      if (TaskListInMemory.validateUser(username, password)) {
          Redirect(routes.muumimukit.works()).withSession("username" -> username)
      } else
        Redirect(routes.HomeController.index()).flashing("error" -> "Error. Invalid username or password. Please try againa ")

    }.getOrElse(Redirect(routes.HomeController.index()))
  }

  def logout = Action  {
    Redirect(routes.HomeController.index()).withNewSession
  }


}
