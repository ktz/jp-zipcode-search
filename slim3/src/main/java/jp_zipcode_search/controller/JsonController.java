package jp_zipcode_search.controller;

import java.util.logging.Logger;
import java.util.Map;
import java.util.HashMap;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import net.arnx.jsonic.JSON;

import com.google.appengine.api.datastore.DatastoreFailureException;
import com.google.appengine.api.datastore.DatastoreTimeoutException;
import com.google.apphosting.api.DeadlineExceededException;
import com.google.apphosting.api.ApiProxy.CapabilityDisabledException;

public abstract class JsonController extends Controller {

  static final Logger logger = Logger.getLogger(JsonController.class.getName());

  final Map<String, Object> json = new HashMap<String, Object>();

  @Override
  protected Navigation handleError(Throwable error) throws Throwable {
    String code;
    String message;
    if (error instanceof CapabilityDisabledException) {
      code = "APPENGINE READONLY";
      message = "Datastore functionality is not available.";
    } else if (error instanceof DatastoreTimeoutException) {
      code = "APPENGINE TIMEOUT";
      message = "Datastore operation exceeds the maximum amount of time allowed for datastore operatoins.";
    } else if (error instanceof DatastoreFailureException) {
      code = "APPENGINE FRAILURE";
      message = "Unknown error occurs while communicating with the datastore.";
    } else if (error instanceof DeadlineExceededException) {
      code = "APPENGINE DREADLINE";
      message = "Request has exceeded the 30 second request deadline.";
    } else {
      code = "UNKOWN ERROR";
      message = error.toString();
    }
    json.put("status", code);
    json.put("body", message);
    jsonResponse(json);
    return null;
  }

  protected void jsonResponse(Object data) throws Exception {
    logger.info(param("callback"));
    response.setContentType("application/json; charset=utf-8");
    if (param("callback") != null) {
      response.getWriter().write(param("callback") + "(" + JSON.encode(data) + ")");
    } else {
      response.getWriter().write(JSON.encode(data));
    }
    response.flushBuffer();
  }

  protected boolean isBlank(String arg) {
    if (arg == null) {
      return true;
    } else if (arg.equals("")) {
      return true;
    } else {
      return false;
    }
  }
  
}
