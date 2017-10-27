package com.connextra.pairing.exercise1;

import java.util.concurrent.atomic.AtomicInteger;

public class SlowSprocketFactory {
	private final AtomicInteger maxSerialNumber = new AtomicInteger();

	public Sprocket createSprocket() {
		// clang, click, whistle, pop and other expensive onomatopoeic operations
		int serialNumber = maxSerialNumber.incrementAndGet();
		return new Sprocket(serialNumber);
	}
	
	public int getMaxSerialNumber() {
		return maxSerialNumber.get();
	}
}