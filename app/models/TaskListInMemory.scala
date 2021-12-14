package models

import collection.mutable

object TaskListInMemory {

  private val users = mutable.Map[String, String]("Shit" -> "Fuck")


  def validateUser(username: String, password: String): Boolean = {
    users.get(username).map(_ == password).getOrElse(false)
  }

}

