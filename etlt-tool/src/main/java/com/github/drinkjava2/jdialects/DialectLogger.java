/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package com.github.drinkjava2.jdialects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * For logger output, to avoid logger jar version conflict, default use JDK log,
 * if found commons log, use it, if found Log4j use it..., by this way this
 * project has no dependency to any logger jar.
 * 
 * @author Yong Zhu
 * @since 1.0.1
 */
public class DialectLogger {

	private Log logger;
	private static boolean firstRun = true;
	private static boolean enableLog = true;
	public static DialectLogger INSTANCE = null;// NOSONAR

	static {
		INSTANCE = new DialectLogger(DialectLogger.class);
		firstRun = false;
	}

	public DialectLogger(Class<?> targetClass) {
		if (targetClass == null)
			throw new AssertionError("DbProLogger error: targetClass can not be null.");

		if (firstRun) {
			System.err.println("DialectLogger failed to load org.apache.commons.logging.LogFactory. Use JDK logger.");// NOSONAR
			logger = LogFactory.getLog(targetClass.getName());// use JDK log
			firstRun = false;
		}
	}

	/**
	 * Build a DbProLogger instance by given targetClass
	 * 
	 * @param targetClass
	 * @return A DbProLogger instance
	 */
	public static DialectLogger getLog(Class<?> targetClass) {
		return new DialectLogger(targetClass);
	}

	public static void setEnableLog(boolean enablelog) {
		enableLog = enablelog;
	}

	public void info(String msg) {
		if (!enableLog)
			return;
		if (logger != null) {
			logger.info(msg);
		}
	}

	public void warn(String msg) {
		if (!enableLog)
			return;
		if (logger != null) {
			logger.warn(msg);
		}
	}

	public void error(String msg) {
		if (!enableLog)
			return;
		if (logger != null) {
			logger.error(msg);
		}
	}

}
