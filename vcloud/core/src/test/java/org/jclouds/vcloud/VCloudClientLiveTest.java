/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
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
 * ====================================================================
 */
package org.jclouds.vcloud;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.net.URI;

import org.jclouds.logging.log4j.config.Log4JLoggingModule;
import org.jclouds.vcloud.domain.Catalog;
import org.jclouds.vcloud.domain.NamedResource;
import org.jclouds.vcloud.domain.Organization;
import org.jclouds.vcloud.domain.Task;
import org.jclouds.vcloud.domain.VApp;
import org.jclouds.vcloud.domain.VDC;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

/**
 * Tests behavior of {@code VCloudClient}
 * 
 * @author Adrian Cole
 */
@Test(groups = "live", sequential = true, testName = "vcloud.VCloudClientLiveTest")
public class VCloudClientLiveTest {

   protected VCloudClient connection;
   protected String account;

   @Test
   public void testOrganization() throws Exception {
      Organization response = connection.getOrganization();
      assertNotNull(response);
      assertNotNull(response.getId());
      assertNotNull(account);
      assertNotNull(response.getCatalog());
      assertEquals(response.getTasksLists().size(), 1);
      assertEquals(response.getVDCs().size(), 1);
   }

   @Test
   public void testCatalog() throws Exception {
      Catalog response = connection.getCatalog();
      assertNotNull(response);
      assertNotNull(response.getId());
      assertNotNull(response.getName());
      assertNotNull(response.getLocation());
      assert response.size() > 0;
   }

   @Test
   public void testDefaultVDC() throws Exception {
      VDC response = connection.getDefaultVDC();
      assertNotNull(response);
      assertNotNull(response.getId());
      assertNotNull(response.getName());
      assertNotNull(response.getLocation());
      assertNotNull(response.getResourceEntities());
      assertNotNull(response.getAvailableNetworks());
      assertEquals(connection.getVDC(response.getId()), response);
   }

   @Test
   public void testDefaultTasksList() throws Exception {
      org.jclouds.vcloud.domain.TasksList response = connection.getDefaultTasksList();
      assertNotNull(response);
      assertNotNull(response.getId());
      assertNotNull(response.getLocation());
      assertNotNull(response.getTasks());
      assertEquals(connection.getTasksList(response.getId()), response);
   }

   @Test
   public void testGetTask() throws Exception {
      org.jclouds.vcloud.domain.TasksList response = connection.getDefaultTasksList();
      assertNotNull(response);
      assertNotNull(response.getLocation());
      assertNotNull(response.getTasks());
      for (Task t : response.getTasks()) {
         assertEquals(connection.getTask(t.getId()).getLocation(), t.getLocation());
      }
   }

   @Test(enabled = true)
   public void testGetVApp() throws Exception {
      VDC response = connection.getDefaultVDC();
      for (NamedResource item : response.getResourceEntities().values()) {
         if (item.getType().equals(VCloudMediaType.VAPP_XML)) {
            VApp app = connection.getVApp(item.getId());
            assertNotNull(app);
         }
      }
   }

   @BeforeGroups(groups = { "live" })
   public void setupClient() {
      String endpoint = checkNotNull(System.getProperty("jclouds.test.endpoint"),
               "jclouds.test.endpoint");
      account = checkNotNull(System.getProperty("jclouds.test.user"), "jclouds.test.user");
      String key = checkNotNull(System.getProperty("jclouds.test.key"), "jclouds.test.key");
      connection = new VCloudContextBuilder(new VCloudPropertiesBuilder(URI.create(endpoint),
               account, key).build()).withModules(new Log4JLoggingModule()).buildContext().getApi();
   }

}
