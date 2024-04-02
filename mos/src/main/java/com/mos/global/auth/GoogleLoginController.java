package com.mos.global.auth;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;
import java.util.Collections;

import static com.amazonaws.protocol.json.SdkStructuredPlainJsonFactory.JSON_FACTORY;

@RestController
@RequestMapping
public class GoogleLoginController {

  @GetMapping("/login")
  public void login() {
    RequestEntity.get("https://accounts.google.com/o/oauth2/v2/auth?");



  }

  /** Authorizes the installed application to access user's protected data. */
//  private static Credential authorize() throws Exception {
//    // load client secrets
//    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//        new InputStreamReader(CalendarSample.class.getResourceAsStream("/client_secrets.json")));
//    // set up authorization code flow
//    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//        httpTransport, JSON_FACTORY, clientSecrets,
//        Collections.singleton(CalendarScopes.CALENDAR)).setDataStoreFactory(dataStoreFactory)
//        .build();
//    // authorize
//    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
//  }
}
