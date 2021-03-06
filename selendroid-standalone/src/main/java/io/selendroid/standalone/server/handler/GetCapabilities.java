/*
 * Copyright 2012-2014 eBay Software Foundation and selendroid committers.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.selendroid.standalone.server.handler;

import org.json.JSONException;
import org.json.JSONObject;

import io.selendroid.common.SelendroidCapabilities;
import io.selendroid.server.common.Response;
import io.selendroid.server.common.SelendroidResponse;
import io.selendroid.server.common.StatusCode;
import io.selendroid.server.common.exceptions.SelendroidException;
import io.selendroid.server.common.http.HttpRequest;
import io.selendroid.standalone.server.BaseSelendroidServerHandler;

import java.util.logging.Logger;

public class GetCapabilities extends BaseSelendroidServerHandler {
  private static final Logger log = Logger.getLogger(GetCapabilities.class.getName());

  public GetCapabilities(String mappedUri) {
    super(mappedUri);
  }

  @Override
  public Response handle(HttpRequest request) throws JSONException {
    log.info("get capabilities command");
    String sessionId = getSessionId(request);

    SelendroidCapabilities caps = getSelendroidDriver(request).getSessionCapabilities(sessionId);
    if (caps == null) {
      return new SelendroidResponse(sessionId, StatusCode.UNKNOWN_ERROR, new SelendroidException("Session was not found"));
    }
    return new SelendroidResponse(sessionId, new JSONObject(caps.asMap()));
  }
}
