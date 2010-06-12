package jp_zipcode_search.controller;

import org.slim3.controller.router.RouterImpl;

public class AppRouter extends RouterImpl {

  public AppRouter() {
    addRouting("/address/{pref}/{city}/{address}", "/address?pref={pref}&city={city}&address={address}");
    addRouting("/address/{pref}/{city}", "/address?pref={pref}&city={city}");
    addRouting("/address/{pref}", "/address?pref={pref}");
    addRouting("/zipcode/{zipcode}", "/zipcode?zipcode={zipcode}");
  }

}
