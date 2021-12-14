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

          Redirect(routes.muumimukit.works())
      } else
        Redirect(routes.HomeController.index())

    }.getOrElse(Redirect(routes.HomeController.index()))
  }


}
