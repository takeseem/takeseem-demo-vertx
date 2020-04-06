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
package com.takeseem.demo.vertx.issues;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.takeseem.demo.vertx.UtilSys;
import com.takeseem.demo.vertx.UtilVertx;

import io.vertx.core.Vertx;

/**
 * 
 * @author <a href="https://github.com/takeseem/">杨浩</a>
 */
public class VertxIssues522 {
	protected static final Logger LOGGER = LogManager.getLogger(VertxIssues522.class);

	/**
	 * <a href="https://github.com/vert-x3/issues/issues/522">
	 * 	BlockedThreadChecker.log not work in 4.0.0-SNAPSHOT, but 3 is work ok.
	 * </a>
	 */
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		UtilSys.init();
		Vertx vertx = Vertx.vertx();
		
		CountDownLatch cdLatch = new CountDownLatch(1);
		long sleepTime = 3500;
		vertx.runOnContext(v -> {
			LOGGER.info("sleep {}ms in eventloop", sleepTime);
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException ex) {
				LOGGER.warn("ignore exception", ex);
			} finally {
				cdLatch.countDown();
			}
		});
		cdLatch.await();
		
		long t0 = System.currentTimeMillis();
		UtilVertx.<Void>handleGet(vertx::close);
		LOGGER.info("close vertx : {}, use : {}ms", vertx, System.currentTimeMillis() - t0);
	}

}
