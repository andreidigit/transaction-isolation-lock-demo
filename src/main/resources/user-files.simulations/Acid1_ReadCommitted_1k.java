/*
 * Copyright 2011-2022 GatlingCorp (https://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;

public class Acid1_ReadCommitted_1k extends Simulation {

  HttpProtocolBuilder httpProtocol =
      http
          // Here is the root for all relative URLs
          .baseUrl("http://localhost:8084/api")
          // Here are the common headers
          .acceptHeader("*/*")
          .acceptEncodingHeader("gzip, deflate, br")
          .userAgentHeader(
              "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0");

  // A scenario is a chain of requests and pauses
  ScenarioBuilder scn =
      scenario("Scenario Name")
          .exec(
              http("request_1")
                  // Here's an example of a POST request
                  .post("/add-read-committed")
                  .header("content-type", "application/x-www-form-urlencoded")
                  // Note the triple double quotes: used in Scala for protecting a whole chain of
                  // characters (no need for backslash)
                  .queryParam("name", "Ivan")
                  .queryParam("like", "1"))
            .exec(
              http("request_2")
                  // Here's an example of a POST request
                  .post("/add-read-committed")
                  .header("content-type", "application/x-www-form-urlencoded")
                  // Note the triple double quotes: used in Scala for protecting a whole chain of
                  // characters (no need for backslash)
                  .queryParam("name", "Ivan")
                  .queryParam("like", "1"));

  {
    setUp(scn.injectOpen(constantUsersPerSec(250).during(2)).protocols(httpProtocol));
  }
}
