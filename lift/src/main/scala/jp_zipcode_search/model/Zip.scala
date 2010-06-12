package jp_zipcode_search.model

import net.liftweb.util._
import java.util._
import scala.collection.jcl.Conversions._
import com.google.appengine.api.datastore._
import Query._
import FilterOperator._
import SortDirection._
import FetchOptions.Builder._

class Zip(entity:Entity) {
  
  def this() = this(new Entity("Zip"))
  
  def zipcode = entity.getProperty("zipcode").toString
  
  def prefPhonetic = entity.getProperty("pref_phonetic").toString
  
  def cityPhonetic = entity.getProperty("city_phonetic").toString
  
  def addressPhonetic = entity.getProperty("address_phonetic").toString
  
  def pref = entity.getProperty("pref").toString
  
  def city = entity.getProperty("city").toString
  
  def address = entity.getProperty("address").toString

}

object Zip {
  
  val ds:DatastoreService = DatastoreServiceFactory.getDatastoreService
  
  def create(entity:Entity) = new Zip(entity)
  
  def find(address:scala.List[String]) = {
    var query = new Query("Zip")
    if (address.length > 0) {
      query.addFilter("pref", EQUAL, address(0))
    }
    if (address.length > 1) {
      query.addFilter("city", EQUAL, address(1))
    }
    if (address.length > 2) {
      query.addFilter("address", GREATER_THAN_OR_EQUAL, address(2))
      query.addFilter("address", LESS_THAN, address(2) + "\ufffd")
      query.addSort("address", ASCENDING)
    }
    query.addSort("city_phonetic", ASCENDING)
    query.addSort("address_phonetic", ASCENDING)
    ds.prepare(query).asList(withOffset(0).limit(1000)).toList.map(create)
  }
  
  def findByZipcode(zipcode:String) = {
    var query = new Query("Zip")
    query.addSort("city_phonetic", ASCENDING)
    query.addSort("address_phonetic", ASCENDING)
    query.addFilter("zipcode", EQUAL, zipcode)
    ds.prepare(query).asList(withOffset(0)).toList.map(create)
  }
  
}