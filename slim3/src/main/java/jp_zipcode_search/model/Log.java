package jp_zipcode_search.model;

import java.util.Date;
import com.google.appengine.api.datastore.Key;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

@Model
public class Log {

  @Attribute(primaryKey = true)
  private Key key;

  private Date date = new Date();

  public Key getKey() {
    return key;
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

}
