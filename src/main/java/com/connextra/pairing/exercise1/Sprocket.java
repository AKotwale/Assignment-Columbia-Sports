package com.connextra.pairing.exercise1;

/**
 * Connextra's world famous Sprocket. 
 */
public class Sprocket {
	private int serialNumber;
	
	public Sprocket(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	@Override
	public String toString() {
		return "sprocket number " + serialNumber;
	}
}
