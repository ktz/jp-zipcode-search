package jp_zipcode_search.service;

import java.util.logging.Logger;
import org.slim3.datastore.Datastore;
import jp_zipcode_search.meta.LogMeta;
import jp_zipcode_search.model.Log;

public class LogService {

  static final Logger logger = Logger.getLogger(ZipService.class.getName());

  static final LogMeta meta = LogMeta.get();

  public static Log get() {
    return Datastore.query(meta).sort(meta.date.desc).offset(0).limit(1).asSingle();
  }

}

