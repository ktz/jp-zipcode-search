package bootstrap.liftweb

import _root_.net.liftweb.common._
import _root_.net.liftweb.util._
import _root_.net.liftweb.http._
import _root_.net.liftweb.sitemap._
import _root_.net.liftweb.sitemap.Loc._
import Helpers._
import jp_zipcode_search.lib._

class Boot {
  
  def boot {
    LiftRules.addToPackages("jp_zipcode_search")
    LiftRules.passNotFoundToChain = true
    LiftRules.autoIncludeAjax = _ => false
    LiftRules.enableLiftGC = false
    LiftRules.useXhtmlMimeType = false
    
    ResponseInfo.docType = {
      case _ if S.getDocType._1 => S.getDocType._2
      case _ => Full(DocType.xhtmlStrict)
    }

    val entries = Menu(Loc("Home", List("index"), "Home")) :: Nil
    LiftRules.setSiteMap(SiteMap(entries:_*))
    
    WebServices.init()
  }
  
}

