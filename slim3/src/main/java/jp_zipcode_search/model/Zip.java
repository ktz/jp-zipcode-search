package jp_zipcode_search.model;

import com.google.appengine.api.datastore.Key;
import org.slim3.datastore.Attribute;
import org.slim3.datastore.Model;

@Model
public class Zip {

  @Attribute(primaryKey = true)
  private Key key;

  private String zipcode;

  private String pref;

  private String city;

  private String address;

  private String pref_phonetic;

  private String city_phonetic;

  private String address_phonetic;

  public Key getKey() {
    return key;
  }

  public void setKey(Key key) {
    this.key = key;
  }

  public String getZipcode() {
    return zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getPref() {
    return pref;
  }

  public void setPref(String pref) {
    this.pref = pref;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPref_phonetic() {
    return pref_phonetic;
  }

  public void setPref_phonetic(String pref_phonetic) {
    this.pref_phonetic = pref_phonetic;
  }

  public String getCity_phonetic() {
    return city_phonetic;
  }

  public void setCity_phonetic(String city_phonetic) {
    this.city_phonetic = city_phonetic;
  }

  public String getAddress_phonetic() {
    return address_phonetic;
  }

  public void setAddress_phonetic(String address_phonetic) {
    this.address_phonetic = address_phonetic;
  }

}
