package jp_zipcode_search.snippet

import scala.xml._
import net.liftweb.http._
import net.liftweb.util._
import S._
import Helpers._

class Application {
  
  def updatedAt = <span>{jp_zipcode_search.model.Log.updatedAt}</span>
  
}
