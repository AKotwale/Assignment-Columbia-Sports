Time to complete the test: 45 minutes.

A simple test of core Java skills and test driven development

In this exercise you will be implementing a small read through cache that wraps the factory class `SlowSprocketFactory`.
Imagine that `SlowSprocketFactory`’s create method is an expensive method that we want to cache the results.

The focus is on core Java skills, clean code design and test driven development.

1. Make `testCacheReturnsASprocket()` pass by implementing the `get(...)` method in `SprocketCache`.

2. Make `testCacheReturnsSameObjectForSameKey()` pass by having `SprocketCache` cache the result for subsequent calls.

3. Implement the method `testCacheReturnsDifferentObjectsForDifferentKeys()` by caching sprockets with different keys.

4. Make `testCacheReturnsDifferentObjectsForDifferentKeys()` pass.

5. Implement the method `testCacheTimeout()` such that it tests that the cache will only return cached sprockets of less than a given age.

6. Make `testCacheTimeout()` pass.

7. Implement the method `testThreadSafe()` which will test if same object is returned when accessing cache from two concurrent threads.

8. Finish the `SprocketCache` class such that it passes all tests.