/**
 * Copyright 2011-2013 eBusiness Information, Groupe Excilys (www.ebusinessinformation.fr)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gatling.mojo;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import scala_maven_executions.MainHelper;

public class GatlingMojoUtilsTest {

	@Test
	public void testFindClassPathInFileSystem() throws Exception {
		
		String path = GatlingMojoUtils.locateClass(GatlingMojoUtilsTest.class);
		
		assertEquals(new File("target"+File.separator+"test-classes").getCanonicalFile().getAbsolutePath(), path);
	}
	
	@Test
	public void testFindClassPathInJar() throws Exception {
	
		String path = GatlingMojoUtils.locateClass(String.class);
		
		assertEquals(MainHelper.locateJar(String.class), path);
	}
}
