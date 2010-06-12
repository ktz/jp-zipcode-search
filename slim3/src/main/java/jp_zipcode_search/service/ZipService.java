package jp_zipcode_search.service;

import java.util.logging.*;
import java.util.List;
import java.util.Map;
import org.slim3.datastore.Datastore;
import org.slim3.datastore.ModelQuery;
import org.slim3.util.BeanUtil;
import com.google.appengine.api.datastore.Transaction;
import jp_zipcode_search.meta.ZipMeta;
import jp_zipcode_search.model.Zip;

public class ZipService {

  static final Logger logger = Logger.getLogger(ZipService.class.getName());

  static final ZipMeta meta = ZipMeta.get();

  public static List<Zip> getAddressesByZipcode(String zipcode) {
    return Datastore.query(meta)
        .filter(meta.zipcode.equal(zipcode))
        .sort(meta.city_phonetic.asc, meta.address_phonetic.asc)
        .asList();
  }

  public static List<Zip> getAddressesByAddress(Map params) {
    ModelQuery<Zip> q = Datastore.query(meta);
    if (params.get("pref") != null) {
      q.filter(meta.pref.equal((String)params.get("pref")));
    }
    if (params.get("city") != null) {
      q.filter(meta.city.equal((String)params.get("city")));
    }
    if (params.get("address") != null) {
      q.filter(meta.address.startsWith((String)params.get("address")));
      q.sort(meta.address.asc);
    }
    q.sort(meta.city_phonetic.asc, meta.address_phonetic.asc);
    return q.asList();
  }

}

