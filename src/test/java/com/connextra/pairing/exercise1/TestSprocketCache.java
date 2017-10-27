package com.connextra.pairing.exercise1;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class TestSprocketCache {

	private SlowSprocketFactory sprocketFactory = new SlowSprocketFactory();

	@Test
	public void testCacheReturnsASprocket() {
		SprocketCache cache = new SprocketCache(sprocketFactory);

		Sprocket sprocket = cache.get("key");
		assertNotNull(sprocket);
	}

	@Test

	public void testCacheReturnsSameObjectForSameKey() {
		SprocketCache cache = new SprocketCache(sprocketFactory);

		Sprocket sprocket1 = cache.get("key");
		Sprocket sprocket2 = cache.get("key");

		assertEquals("cache should return the same object for the same key", sprocket1, sprocket2);
		assertEquals("factory's create method should be called once only", 1, sprocketFactory.getMaxSerialNumber());
	}

	@Test
	public void testCacheReturnsDifferentObjectsForDifferentKeys() {
		SprocketCache cache = new SprocketCache(sprocketFactory);

		Sprocket sprocket1 = cache.get("key1");
		Sprocket sprocket2 = cache.get("key2");

		assertNotEquals(sprocket1, sprocket2);
	}

	@Test
	public void testCacheTimeout() throws InterruptedException {
		SprocketCache cache = new SprocketCache(sprocketFactory);
		cache.setMaxAgeMs(200);
		Sprocket oldObject = cache.get("key1");
		Thread.sleep(300);

		Sprocket newObject = cache.get("key1");
		assertNotEquals(oldObject, newObject);

	}

	@Test
	public void testThreadSafe() throws InterruptedException, ExecutionException {

		SprocketCache cache = new SprocketCache(sprocketFactory);
		GetCachedResult cachedResult1 = new GetCachedResult(cache, "key1");
		GetCachedResult cachedResult2 = new GetCachedResult(cache, "key1");
		GetCachedResult cachedResult3 = new GetCachedResult(cache, "key1");
		GetCachedResult cachedResult4 = new GetCachedResult(cache, "key1");
		GetCachedResult cachedResult5 = new GetCachedResult(cache, "key1");
		GetCachedResult cachedResult6 = new GetCachedResult(cache, "key1");

		List<GetCachedResult> taskList = new ArrayList<>();
		taskList.add(cachedResult1);
		taskList.add(cachedResult2);
		taskList.add(cachedResult3);
		taskList.add(cachedResult4);
		taskList.add(cachedResult5);
		taskList.add(cachedResult6);
		ExecutorService service = Executors.newFixedThreadPool(6);
		List<Future<Sprocket>> results = service.invokeAll(taskList);

		Sprocket result = results.get(0).get();

		assertEquals(result, results.get(1).get());
		assertEquals(result, results.get(2).get());
		assertEquals(result, results.get(3).get());
		assertEquals(result, results.get(4).get());
		assertEquals(result, results.get(5).get());

	}

	class GetCachedResult implements Callable<Sprocket> {

		private SprocketCache cache;
		private String key;

		GetCachedResult(SprocketCache cache, String key) {
			this.cache = cache;
			this.key = key;
		}

		@Override
		public Sprocket call() throws Exception {
			Sprocket result = this.cache.get(key);
			return result;
		}

	}
}
