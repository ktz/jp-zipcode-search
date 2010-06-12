package jp_zipcode_search.controller;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slim3.tester.ControllerTester;
import org.slim3.tester.MockHttpServletRequest;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import net.arnx.jsonic.JSON;
import jp_zipcode_search.service.ZipServiceTest;
import java.util.List;

public class ZipcodeControllerTest {

  ControllerTester tester;

  @Before
  public void setUp() throws Exception {
    tester = new ControllerTester(this.getClass());
    tester.setUp();
    ZipServiceTest.insertTestData();
  }

  @After
  public void tearDown() throws Exception {
    tester.tearDown();
  }


  @Test
  public void shouldReturnSuccessResponse() throws NullPointerException, IllegalArgumentException, IOException, ServletException {
    MockHttpServletRequest request = tester.request;
    request.setMethod("get");
    request.addParameter("zipcode", "1000001");
    tester.start("/zipcode");
    assertThat(tester.getController(), is(instanceOf(ZipcodeController.class)));
    assertThat(tester.response.getStatus(), is(equalTo(HttpServletResponse.SC_OK)));
    String output = tester.response.getOutputAsString();
    Map<String, Object> json = (Map<String, Object>)JSON.decode(output);
    assertThat((String)json.get("status"), is(equalTo("SUCCESS")));
    List<Map<String, String>> body = (List<Map<String, String>>)json.get("body");
    assertThat(body.size(), is(equalTo(1)));
    Map<String, String> res = body.get(0);
    assertThat(res.get("zipcode"), is(equalTo("1000001")));
    assertThat(res.get("pref"), is(equalTo("Tokyo")));
    assertThat(res.get("city"), is(equalTo("Chiyodaku")));
    assertThat(res.get("address"), is(equalTo("Chiyoda")));
    assertThat(res.get("pref_phonetic"), is(equalTo("tokyo")));
    assertThat(res.get("city_phonetic"), is(equalTo("chiyodaku")));
    assertThat(res.get("address_phonetic"), is(equalTo("chiyoda")));
  }

  @Test
  public void shouldReturnMissingRequiredParameter() throws NullPointerException, IllegalArgumentException, IOException, ServletException {
    MockHttpServletRequest request = tester.request;
    request.setMethod("get");
    request.addParameter("zipcode", "");
    tester.start("/zipcode");
    assertThat(tester.getController(), is(instanceOf(ZipcodeController.class)));
    assertThat(tester.response.getStatus(), is(equalTo(HttpServletResponse.SC_OK)));
    String output = tester.response.getOutputAsString();
    Map<String, String> json = (Map<String, String>)JSON.decode(output);
    assertThat(json.get("status"), is(equalTo("MISSING REQUIRED PARAMETER")));
  }

  @Test
  public void shouldReturnNotFoundResponse() throws NullPointerException, IllegalArgumentException, IOException, ServletException {
    MockHttpServletRequest request = tester.request;
    request.setMethod("get");
    request.addParameter("zipcode", "1000003");
    tester.start("/zipcode");
    assertThat(tester.response.getStatus(), is(equalTo(HttpServletResponse.SC_OK)));
    String output = tester.response.getOutputAsString();
    Map<String, String> json = (Map<String, String>)JSON.decode(output);
    assertThat(json.get("status"), is(equalTo("NOT FOUND")));
  }

}

