package jp_zipcode_search.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slim3.tester.AppEngineTester;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.slim3.datastore.Datastore;
import jp_zipcode_search.meta.ZipMeta;
import jp_zipcode_search.model.Zip;
import org.slim3.util.BeanUtil;

public class ZipServiceTest {

  AppEngineTester tester;

  private ZipService service = new ZipService();

  @Before
  public void setUp() throws Exception {
    tester = new AppEngineTester();
    tester.setUp();
    insertTestData();
  }

  @After
  public void tearDown() throws Exception {
    tester.tearDown();
  }

  public static void insertTestData(){
    Zip z1 = new Zip();
    z1.setZipcode("1000001");
    z1.setPref("Tokyo");
    z1.setCity("Chiyodaku");
    z1.setAddress("Chiyoda");
    z1.setPref_phonetic("tokyo");
    z1.setCity_phonetic("chiyodaku");
    z1.setAddress_phonetic("chiyoda");
    Zip z2 = new Zip();
    z2.setZipcode("1000002");
    z2.setPref("Tokyo");
    z2.setCity("Chiyodaku");
    z2.setAddress("KokyoGaien");
    z2.setPref_phonetic("tokyo");
    z2.setCity_phonetic("chiyodaku");
    z2.setAddress_phonetic("kokyogaien");
    Datastore.put(z1, z2);
  }

  @Test
  public void getAddressesByZipcode() {
    List<Zip> list = ZipService.getAddressesByZipcode("1000001");
    assertThat(list.size(), is(equalTo(1)));
    assertThat(list.get(0).getAddress(), is(equalTo("Chiyoda")));
  }

  @Test
  public void getAddressesByFullAddress() {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("pref", "Tokyo");
    param.put("city", "Chiyodaku");
    param.put("address", "Chiyoda");
    List<Zip> list = ZipService.getAddressesByAddress(param);
    assertThat(list.size(), is(equalTo(1)));
    assertThat(list.get(0).getAddress(), is(equalTo("Chiyoda")));
  }

  @Test
  public void getAddressesByPref() {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("pref", "Tokyo");
    List<Zip> list = ZipService.getAddressesByAddress(param);
    assertThat(list.size(), is(equalTo(2)));
    assertThat(list.get(0).getAddress(), is(equalTo("Chiyoda")));
    assertThat(list.get(1).getAddress(), is(equalTo("KokyoGaien")));
  }
  
  @Test
  public void getAddressesByCity() {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("city", "Chiyodaku");
    List<Zip> list = ZipService.getAddressesByAddress(param);
    assertThat(list.size(), is(equalTo(2)));
    assertThat(list.get(0).getAddress(), is(equalTo("Chiyoda")));
    assertThat(list.get(1).getAddress(), is(equalTo("KokyoGaien")));
  }

  @Test
  public void getAddressesByAddress() {
    Map<String, Object> param = new HashMap<String, Object>();
    param.put("address", "Chiyo");
    List<Zip> list = ZipService.getAddressesByAddress(param);
    assertThat(list.size(), is(equalTo(1)));
    assertThat(list.get(0).getAddress(), is(equalTo("Chiyoda")));
  }

}
