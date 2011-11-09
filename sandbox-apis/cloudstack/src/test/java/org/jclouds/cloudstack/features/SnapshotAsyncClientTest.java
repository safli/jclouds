/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.cloudstack.features;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSet;
import com.google.inject.TypeLiteral;
import org.jclouds.cloudstack.domain.Snapshot;
import org.jclouds.cloudstack.domain.SnapshotPolicySchedule;
import org.jclouds.cloudstack.options.CreateSnapshotOptions;
import org.jclouds.cloudstack.options.ListSnapshotPoliciesOptions;
import org.jclouds.cloudstack.options.ListSnapshotsOptions;
import org.jclouds.cloudstack.util.SnapshotPolicySchedules;
import org.jclouds.functions.IdentityFunction;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.functions.ReleasePayloadAndReturn;
import org.jclouds.http.functions.UnwrapOnlyJsonValue;
import org.jclouds.rest.functions.MapHttp4xxCodesToExceptions;
import org.jclouds.rest.functions.ReturnEmptySetOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnNullOnNotFoundOr404;
import org.jclouds.rest.functions.ReturnVoidOnNotFoundOr404;
import org.jclouds.rest.internal.RestAnnotationProcessor;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * Tests the behaviour of SnapshotAsyncClient.
 * 
 * @see SnapshotAsyncClient
 * @author Richard Downer
 */
// NOTE:without testName, this will not call @Before* and fail w/NPE during
// surefire
@Test(groups = "unit", testName = "SnapshotAsyncClientTest")
public class SnapshotAsyncClientTest extends BaseCloudStackAsyncClientTest<SnapshotAsyncClient> {

   public void testCreateSnapshot() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("createSnapshot", long.class, CreateSnapshotOptions[].class);
      HttpRequest httpRequest = processor.createRequest(method, 5);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=createSnapshot&volumeid=5 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, UnwrapOnlyJsonValue.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, MapHttp4xxCodesToExceptions.class);

      checkFilters(httpRequest);
   }

   public void testCreateSnapshotOptions() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("createSnapshot", long.class, CreateSnapshotOptions[].class);
      HttpRequest httpRequest = processor.createRequest(method, 5, CreateSnapshotOptions.Builder.accountInDomain("acc", 7).policyId(9));

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=createSnapshot&volumeid=5&account=acc&domainid=7&policyid=9 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, UnwrapOnlyJsonValue.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, MapHttp4xxCodesToExceptions.class);

      checkFilters(httpRequest);
   }

   public void testListSnapshots() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("listSnapshots", ListSnapshotsOptions[].class);
      HttpRequest httpRequest = processor.createRequest(method);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=listSnapshots HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, UnwrapOnlyJsonValue.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnEmptySetOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   public void testGetSnapshot() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("getSnapshot", long.class);
      HttpRequest httpRequest = processor.createRequest(method, 5);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=listSnapshots&id=5 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest,
            Functions.compose(IdentityFunction.INSTANCE, IdentityFunction.INSTANCE).getClass());
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnNullOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   public void testListSnapshotsOptions() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("listSnapshots", ListSnapshotsOptions[].class);
      HttpRequest httpRequest = processor.createRequest(method, ListSnapshotsOptions.Builder.accountInDomain("acc", 7).id(5).interval(Snapshot.Interval.MONTHLY).isRecursive(true).keyword("fred").name("fred's snapshot").snapshotType(Snapshot.Type.RECURRING).volumeId(11));

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=listSnapshots&account=acc&domainid=7&id=5&intervaltype=MONTHLY&isrecursive=true&keyword=fred&name=fred%27s%20snapshot&snapshottype=RECURRING&volumeid=11 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, UnwrapOnlyJsonValue.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnEmptySetOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   public void testDeleteSnapshot() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("deleteSnapshot", long.class);
      HttpRequest httpRequest = processor.createRequest(method, 14);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=deleteSnapshot&id=14 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnVoidOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   public void testCreateSnapshotPolicy() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("createSnapshotPolicy", SnapshotPolicySchedule.class, long.class, String.class, long.class);
      HttpRequest httpRequest = processor.createRequest(method, SnapshotPolicySchedules.monthly(5, 6, 7), 10, "UTC", 12);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=createSnapshotPolicy&timezone=UTC&maxsnaps=10&volumeid=12&intervaltype=MONTHLY&schedule=07%3A06%3A05 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, UnwrapOnlyJsonValue.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, MapHttp4xxCodesToExceptions.class);

      checkFilters(httpRequest);
   }

   public void testDeleteSnapshotPolicy() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("deleteSnapshotPolicy", long.class);
      HttpRequest httpRequest = processor.createRequest(method, 7);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=deleteSnapshotPolicies&id=7 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnVoidOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   public void testDeleteSnapshotPolicies() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("deleteSnapshotPolicies", Iterable.class);
      Iterable<Long> ids = ImmutableSet.of(3L, 5L, 7L);
      HttpRequest httpRequest = processor.createRequest(method, ids);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=deleteSnapshotPolicies&ids=3%2C5%2C7 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, ReleasePayloadAndReturn.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnVoidOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   public void testListSnapshotPolicies() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("listSnapshotPolicies", long.class, ListSnapshotPoliciesOptions[].class);
      HttpRequest httpRequest = processor.createRequest(method, 10);

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=listSnapshotPolicies&volumeid=10 HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, UnwrapOnlyJsonValue.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnEmptySetOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   public void testListSnapshotPoliciesOptions() throws NoSuchMethodException {
      Method method = SnapshotAsyncClient.class.getMethod("listSnapshotPolicies", long.class, ListSnapshotPoliciesOptions[].class);
      HttpRequest httpRequest = processor.createRequest(method, 10, ListSnapshotPoliciesOptions.Builder.accountInDomain("fred", 4).keyword("bob"));

      assertRequestLineEquals(httpRequest,
            "GET http://localhost:8080/client/api?response=json&command=listSnapshotPolicies&volumeid=10&account=fred&domainid=4&keyword=bob HTTP/1.1");
      assertNonPayloadHeadersEqual(httpRequest, "Accept: application/json\n");
      assertPayloadEquals(httpRequest, null, null, false);

      assertResponseParserClassEquals(method, httpRequest, UnwrapOnlyJsonValue.class);
      assertSaxResponseParserClassEquals(method, null);
      assertExceptionParserClassEquals(method, ReturnEmptySetOnNotFoundOr404.class);

      checkFilters(httpRequest);
   }

   @Override
   protected TypeLiteral<RestAnnotationProcessor<SnapshotAsyncClient>> createTypeLiteral() {
      return new TypeLiteral<RestAnnotationProcessor<SnapshotAsyncClient>>() {
      };
   }
}
