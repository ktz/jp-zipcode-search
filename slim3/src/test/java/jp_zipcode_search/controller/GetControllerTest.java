package jp_zipcode_search.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.Map;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slim3.tester.ControllerTester;
import net.arnx.jsonic.JSON;
import jp_zipcode_search.service.LogServiceTest;

public class GetControllerTest {

  ControllerTester tester;

  @Before
  public void setUp() throws Exception {
    tester = new ControllerTester(this.getClass());
    tester.setUp();
  }

  @After
  public void tearDown() throws Exception {
    tester.tearDown();
  }

  
  @Test
  public void test() throws NullPointerException, IllegalArgumentException, IOException, ServletException {
    tester.start("/get");
    assertThat(tester.getController(), is(instanceOf(GetController.class)));
    assertThat(tester.response.getStatus(), is(equalTo(HttpServletResponse.SC_OK)));
    String output = tester.response.getOutputAsString();
    Map<String, String> json = (Map<String, String>)JSON.decode(output);
    assertThat(json.get("status"), is(equalTo("SUCCESS")));
    assertThat(json.get("body"), is(notNullValue()));
  }

}
