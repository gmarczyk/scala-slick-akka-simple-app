package config

import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors

object ApiConfig {
  implicit val system = ActorSystem(Behaviors.empty, "AkkaHttpJson")
}
