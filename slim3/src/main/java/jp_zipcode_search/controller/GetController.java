package jp_zipcode_search.controller;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import jp_zipcode_search.model.Log;
import jp_zipcode_search.service.LogService;

public class GetController extends JsonController {

  @Override
  protected Navigation run() throws Exception {
    json.put("status", "SUCCESS");
    Log log = LogService.get();
    if (log != null) {
      json.put("body", "Updated at " + log.getDate());
    } else {
      Date date = new Date();
      json.put("body", "Updated at " + date.toString());
    }
    jsonResponse(json);
    return null;
  }

}
