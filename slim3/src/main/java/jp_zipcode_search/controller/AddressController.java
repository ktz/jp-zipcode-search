package jp_zipcode_search.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import org.slim3.controller.Navigation;
import org.slim3.util.RequestMap;
import jp_zipcode_search.model.Zip;
import jp_zipcode_search.service.ZipService;

public class AddressController extends JsonController {

  @Override
  protected Navigation run() throws Exception {
    if (isBlank(param("pref")) && isBlank(param("city")) && isBlank(param("address"))) {
      json.put("status", "MISSING REQUIRED PARAMETER");
      json.put("body", "At least one of the 'pref' or 'city' or 'address' parameter is required.");
    } else {
      List<Zip> data = ZipService.getAddressesByAddress(new RequestMap(request));
      if (data.size() > 0) {
        List<Map> body = new ArrayList();
        for (Zip zip : data) {
          HashMap<String, String> addr = new HashMap<String, String>();
          addr.put("zipcode", zip.getZipcode());
          addr.put("pref", zip.getPref());
          addr.put("city", zip.getCity());
          addr.put("address", zip.getAddress());
          addr.put("pref_phonetic", zip.getPref_phonetic());
          addr.put("city_phonetic", zip.getCity_phonetic());
          addr.put("address_phonetic", zip.getAddress_phonetic());
          body.add(addr);
        }
        json.put("status", "SUCCESS");
        json.put("body", body);
      } else {
        json.put("status", "NOT FOUND");
        json.put("body", "Addresses not found.");
      }
    }
    jsonResponse(json);
    return null;
  }

}
