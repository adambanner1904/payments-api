package controllers

import play.api.mvc.*
import javax.inject.Inject

class HealthController @Inject() (cc: ControllerComponents) 
  extends AbstractController(cc):
    def ok() = Action:
      Ok("All good!")