/*
 * This program is part of the OpenLMIS logistics management information system platform software.
 * Copyright © 2017 VillageReach
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU Affero General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *  
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Affero General Public License for more details. You should have received a copy of
 * the GNU Affero General Public License along with this program. If not, see
 * http://www.gnu.org/licenses.  For additional information contact info@OpenLMIS.org. 
 */

package org.openlmis.referencedata.service;

import static org.openlmis.referencedata.service.RequestHelper.createEntity;
import static org.openlmis.referencedata.service.RequestHelper.createUri;
import static org.openlmis.referencedata.util.messagekeys.ServiceAccountMessageKeys.ERROR_API_KEY_REQUIRED;

import org.apache.commons.codec.binary.Base64;
import org.openlmis.referencedata.exception.ExternalApiException;
import org.openlmis.referencedata.util.UuidUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {
  private static final String ACCESS_TOKEN = "access_token";

  @Value("${auth.server.clientId}")
  private String clientId;

  @Value("${auth.server.clientSecret}")
  private String clientSecret;

  @Value("${auth.server.authorizationUrl}")
  private String authorizationUrl;

  @Value("${auth.url}")
  private String authUrl;

  private RestOperations restTemplate = new RestTemplate();

  /**
   * Creates API key. This method will call the auth service.
   *
   * @return created API key.
   */
  public UUID createApiKey() {
    String url = getApiKeyUrl();
    HttpEntity entity = createEntity(obtainAccessToken());

    ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

    String body = response.getBody();
    return UuidUtil
        .fromString(body)
        .orElseThrow(() -> new ExternalApiException(ERROR_API_KEY_REQUIRED));
  }

  /**
   * Removes API key. This method will call the auth service.
   */
  public void removeApiKey(UUID key) {
    HttpEntity body = createEntity(obtainAccessToken());
    String url = getApiKeyUrl() + "/" + key;

    restTemplate.exchange(url, HttpMethod.DELETE, body, Object.class);
  }

  private String obtainAccessToken() {
    String plainCreds = clientId + ":" + clientSecret;
    byte[] plainCredsBytes = plainCreds.getBytes();
    byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
    String base64Creds = new String(base64CredsBytes);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + base64Creds);

    HttpEntity<String> request = new HttpEntity<>(headers);

    RequestParameters params = RequestParameters
        .init()
        .set("grant_type", "client_credentials");

    ResponseEntity<Map> response = restTemplate.exchange(
        createUri(authorizationUrl, params), HttpMethod.POST, request, Map.class
    );

    return String.valueOf(response.getBody().get(ACCESS_TOKEN));
  }

  private String getApiKeyUrl() {
    return authUrl + "/api/apiKeys";
  }

}
