/**
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.takeseem.demo.vertx;

import java.util.Properties;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4J2LoggerFactory;

/**
 * 
 * @author <a href="https://github.com/takeseem/">杨浩</a>
 */
public class UtilSys {
	private static final Properties props = System.getProperties();

	/**
	 * <ol>
	 * 	<li>set logger factory for: jul, jcl, vertx, netty</li>
	 * </ol>
	 */
	public static void init() {
		setPropIfMiss("java.awt.headless", true);
		setPropIfMiss("java.util.logging.manager", "org.apache.logging.log4j.jul.LogManager");
		setPropIfMiss("org.apache.commons.logging.log", "org.apache.logging.log4j.jcl.LogFactoryImpl");
		setPropIfMiss("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.Log4j2LogDelegateFactory");
		InternalLoggerFactory.setDefaultFactory(Log4J2LoggerFactory.INSTANCE);
	}
	
	/** 无-D{name}时，设置value */
	private static void setPropIfMiss(String name, Object value) {
		if (!props.containsKey(name) && value != null) {
			System.setProperty(name, value.toString());
		}
	}
}
