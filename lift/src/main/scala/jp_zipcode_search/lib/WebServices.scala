package jp_zipcode_search.lib

import _root_.net.liftweb._
import common._
import http._
import util._
import js._
import JE._
import JsCmds._
import jp_zipcode_search.model._

object WebServices {

  def init() {
    LiftRules.dispatch.append {
      case Req("address" :: address, _, GetRequest) => () => {
        Full(addressSearch(address))
      }
      case Req("zipcode" :: zipcode :: Nil, _, GetRequest) => () => {
        Full(zipcodeSearch(zipcode))
      }
    }
  }

  def addressSearch(address:List[String]) = createResponse(Zip.find(address))
  
  def zipcodeSearch(zipcode:String) = createResponse(Zip.findByZipcode(zipcode))
  
  private def createResponse(zips:List[Zip]):LiftResponse = {
    val res = if (zips.length > 0) {
      JsArray(zips.map(i => JsObj(
        ("zipcode", i.zipcode),
        ("pref", i.pref),
        ("city", i.city),
        ("address", i.address),
        ("pref_phonetic", i.prefPhonetic),
        ("city_phonetic", i.cityPhonetic),
        ("address_phonetic", i.addressPhonetic)
      )):_*)
    } else {
      JsObj(("error", "Not found"), ("request", S.hostAndPath + S.uri))
    }
    val callback = S.param("callback").openOr(null)
    val rsp = if (callback != null) {
      (callback + "(" + res.toJsCmd.toString + ")").getBytes("UTF-8")
    } else {
      res.toJsCmd.toString.getBytes("UTF-8")
    }
    InMemoryResponse(rsp, List("Content-Length" -> rsp.length.toString, "Content-Type" -> "application/json"), Nil, 200)
  }
  
}
