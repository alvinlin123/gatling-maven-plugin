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

import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scala_maven_executions.MainHelper;

/**
 * 
 * 
 * @author Alvin Lin (alvinlin123@gmail.com)
 *
 */
public class GatlingMojoUtils {

	private static final Pattern FILE_CLASS_PTN = Pattern.compile("^.*file:([^!]*)$");
	private static final Pattern JAR_CLASS_PTN = Pattern.compile("^.*file:(.*)!.*$");
	
	/**
	 * Find class in either a JAR file or a directory.
	 * 
	 * This method is based on {@link MainHelper#locateJar(Class)}, which 
	 * only looks for class file inside a JAR.
	 * 
	 * @param clazz
	 * 	Class to look for.
	 * 
	 * @return
	 * 	Absolute path of enclosing directory or JAR file.
	 */
	public static String locateClass(Class<?> clazz) throws Exception {

		final URL location;
		final String classLocation = clazz.getName().replace('.', '/')
				+ ".class";
		final ClassLoader loader = clazz.getClassLoader();
		if (loader == null) {
			location = ClassLoader.getSystemResource(classLocation);
		} else {
			location = loader.getResource(classLocation);
		}

		if (location != null) {
			Matcher fsMatcher= FILE_CLASS_PTN.matcher(location.toString());
			Matcher jarMatcher = JAR_CLASS_PTN.matcher(location.toString());
			if (fsMatcher.find()) {
				String file = fsMatcher.group(1);
				
				/*take out the class name*/
				file = file.replace("/" + classLocation, "");
				return URLDecoder.decode(file, "UTF-8");
			} else if (jarMatcher.find()) {
				String jarFile = jarMatcher.group(1);
				return URLDecoder.decode(jarFile, "UTF-8");
			}
			throw new ClassNotFoundException("Cannot parse location of '"
					+ location);
		}
		throw new ClassNotFoundException("Cannot find class '" + clazz.getName()
				+ " using the classloader");
	}

}
