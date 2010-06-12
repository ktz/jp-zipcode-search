package jp_zipcode_search.snippet

import scala.xml._
import net.liftweb.common._
import net.liftweb.http._
import net.liftweb.util._
import S._

class Analytics {
    
  def url = S.request match {
    case Full(req) if req.request.scheme == "https" => "https://ssl.google-analytics.com/ga.js"
    case _ => "http://www.google-analytics.com/ga.js"
  }
  
  def code = 
    Props.productionMode match {
    case true =>
      <script type="text/javascript" src={url}></script>
      <script type="text/javascript"><![CDATA[
        try {
          var pageTracker = _gat._getTracker('UA-5822976-3');
          pageTracker._trackPageview();
        } catch(err) {}
      ]]></script>
    case false => <!--analytics code here -->
  }
  
}