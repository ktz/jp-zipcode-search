package jp_zipcode_search.model

import java.util._
import scala.collection.jcl.Conversions._
import net.liftweb.util._
import com.google.appengine.api.datastore._
import Query._
import SortDirection._
import FetchOptions.Builder._

class Log(entity:Entity) {
  
  def this() = this(new Entity("Log"))
  
  def date = {
    try {
      entity.getProperty("date").asInstanceOf[java.util.Date]
    } catch {
      case e:NullPointerException => ""
    }
  }
    
}

object Log {
  
  val ds:DatastoreService = DatastoreServiceFactory.getDatastoreService
  
  def create(entity:Entity) = new Log(entity)
  
  def updatedAt = {
    var query = new Query("Log")
    query.addSort("date", DESCENDING)
    var res = ds.prepare(query).asList(withOffset(0).limit(1))
    if (res.length > 0) {
      val f = new Formatter
      f.format("%1$ta, %1$te %1$tb %1$tY %1$tH:%1$tM:%1$tS %1$tz", res.toList.map(create)(0).date).toString
    } else {
      ""
    }
  }
  
}