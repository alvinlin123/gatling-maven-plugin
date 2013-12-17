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

import java.io.File;
import java.util.ArrayList;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.apache.maven.project.MavenProject;

import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * Tests the GatlingMojo class using maven-plugin-test-harness to make sure 
 * the GatlingMojo can properly invokes Gatling App.
 * 
 * Note that this is a JUnit 3 style class because its parent class is JUnit 3 
 * style test class.
 */
public class GatlingMojoMavenRuntimeTest extends AbstractMojoTestCase {

	private final static File TEST_POM = getTestFile( "src/test/resources/mojotest/testpom.xml");
	
	public void testPluginCanStartProperly() throws Exception {
		
		lookupMojo("execute", TEST_POM);
	}
	
	public void testExecuteWithoutFork() throws Exception {
		
		GatlingMojo mojo = (GatlingMojo) lookupMojo("execute", TEST_POM);
		
		setVariableValueToObject(mojo, "mavenProject", mockMavenProject());
		setVariableValueToObject(mojo, "fork", false);
		setVariableValueToObject(mojo, "simulationClass", "dummy.DummySimulation");
		
		mojo.execute();
	}
	
	public void testExecutWithFork() throws Exception {
		
		GatlingMojo mojo = (GatlingMojo) lookupMojo("execute", TEST_POM);
		
		setVariableValueToObject(mojo, "mavenProject", mockMavenProject());
		setVariableValueToObject(mojo, "fork", true);
		setVariableValueToObject(mojo, "simulationClass", "dummy.DummySimulation");
		
		mojo.execute();
	}
	
	public void testExceptionPropagatedInNoForkMode() throws Exception {
		
		GatlingMojo mojo = (GatlingMojo) lookupMojo("execute", TEST_POM);
		
		setVariableValueToObject(mojo, "mavenProject", mockMavenProject());
		setVariableValueToObject(mojo, "fork", false);
		setVariableValueToObject(mojo, "simulationClass", "not exist");
		
		try {
			mojo.execute();
			fail("Should throw MojoExecutionException");
		} catch (MojoExecutionException e) {
			
		}
	}
	
	public void testExceptionPropagatedInForkedMode() throws Exception {
		
		GatlingMojo mojo = (GatlingMojo) lookupMojo("execute", TEST_POM);
		
		setVariableValueToObject(mojo, "mavenProject", mockMavenProject());
		setVariableValueToObject(mojo, "fork", true);
		setVariableValueToObject(mojo, "simulationClass", "not exist");
		
		try {
			mojo.execute();
			fail("Should throw MojoExecutionException");
		} catch (MojoExecutionException e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	private MavenProject mockMavenProject() {
		
		MavenProjectStub mvnPro = new MavenProjectStub();
		
		String classpaths = System.getProperty("java.class.path");
		String sep = File.pathSeparator;
		
		String[] paths = classpaths.split(sep);
		ArrayList<String> pathsArrayList = new ArrayList<String>();
		pathsArrayList.addAll(Arrays.asList(paths));
		
		mvnPro.setTestClasspathElements(pathsArrayList);
		
		
		return mvnPro;
	}
}
