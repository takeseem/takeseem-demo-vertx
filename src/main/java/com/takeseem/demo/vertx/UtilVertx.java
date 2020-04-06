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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;

/**
 * 
 * @author <a href="https://github.com/takeseem/">杨浩</a>
 */
public class UtilVertx {

	/** handle {@linkplain Future} 并 {@linkplain CompletableFuture#get()} */
	public static <T> T get(Future<T> future) throws InterruptedException, ExecutionException {
		return handleGet(future::onComplete);
	}
	/** {@linkplain #handle(Consumer)} 并 {@linkplain CompletableFuture#get()} */
	public static <T> T handleGet(Consumer<Handler<AsyncResult<T>>> action) throws InterruptedException, ExecutionException {
		return handle(action).get();
	}
	/** handle异步，转换为{@linkplain CompletableFuture} */
	public static <T> CompletableFuture<T> handle(Consumer<Handler<AsyncResult<T>>> action) {
		var ret = new CompletableFuture<T>();
		action.accept(v -> {
			if (v.succeeded()) {
				ret.complete(v.result());
			} else {
				ret.completeExceptionally(v.cause());
			}
		});
		return ret;
	}
}
