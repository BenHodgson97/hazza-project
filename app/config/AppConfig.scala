package config

import com.google.inject.Inject
import play.api.Configuration

class AppConfig @Inject()(configuration: Configuration){
  val isSecure: Boolean = configuration.get[Boolean]("isSecure")
}
