package jp_zipcode_search.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slim3.tester.AppEngineTester;
import java.util.List;
import java.util.Calendar;
import org.slim3.datastore.Datastore;
import jp_zipcode_search.meta.LogMeta;
import jp_zipcode_search.model.Log;
import org.slim3.util.BeanUtil;

public class LogServiceTest {

  AppEngineTester tester;

  private LogService service = new LogService();

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
    Calendar cal = Calendar.getInstance();
    cal.set(2010, 1, 1);
    Log l1 = new Log();
    l1.setDate(cal.getTime());
    cal.add(Calendar.DATE, 1);
    Log l2 = new Log();
    l2.setDate(cal.getTime());
    Datastore.put(l1, l2);
  }


  @Test
  public void get() {
    Calendar cal = Calendar.getInstance();
    cal.set(2010, 1, 2);
    Log log = LogService.get();
    assertThat(log.getDate().toString(), is(equalTo(cal.getTime().toString())));
  }

}
