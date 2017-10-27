package com.connextra.pairing.exercise1;

import java.util.HashMap;
import java.util.Map;

public class SprocketCache {

	private final SlowSprocketFactory sprocketFactory;
	private Map<Object, TimedCachedResult> sprocketCache = new HashMap<>();
	private long maxAge;

	public SprocketCache(SlowSprocketFactory sprocketFactory) {
		this.sprocketFactory = sprocketFactory;
	}

	public Sprocket get(Object key) {

		Sprocket result = null;

		synchronized (sprocketCache) {
			TimedCachedResult timedCachedResult = sprocketCache.computeIfAbsent(key,
					k -> new TimedCachedResult(this.sprocketFactory.createSprocket()));

			result =  sprocketCache.computeIfPresent(key, (k , v) -> {
				  if(v.isExpired(maxAge)) {
					  return new TimedCachedResult(sprocketFactory.createSprocket());
				  } else {
					  return v;
				  }
			  }).cachedObject;

		}

		return result;
	}

	public void setMaxAgeMs(long maxAgeMs) {
		this.maxAge = maxAgeMs;
	}

	class TimedCachedResult {

		private long timeInMilliSecond;

		public long getTimeInMilliSecond() {
			return timeInMilliSecond;
		}

		public void setTimeInMilliSecond(long timeInMilliSecond) {
			this.timeInMilliSecond = timeInMilliSecond;
		}

		public Sprocket getCachedObject() {
			return cachedObject;
		}

		public void setCachedObject(Sprocket cachedObject) {
			this.cachedObject = cachedObject;
		}

		private Sprocket cachedObject;

		TimedCachedResult(Sprocket result) {
			this.cachedObject = result;
			this.timeInMilliSecond = System.currentTimeMillis();
		}

		public boolean isExpired(long maxAgeMs) {
			boolean isExpired = false;
			long currentTime = System.currentTimeMillis();

			if ((currentTime - timeInMilliSecond) > maxAgeMs) {
				isExpired = true;
			}

			return isExpired;
		}

	}
}
