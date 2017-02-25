package com.xyz.assessment.application;

import java.util.List;
import java.util.Random;

public class GameBot {

	public static String getRandomObject(List<String> objectSet) {
		return objectSet.get(getRandomNumber(objectSet.size()));
	}

	private static int getRandomNumber(int exclusiveRange) {
		return new Random().nextInt(exclusiveRange);
	}
}
