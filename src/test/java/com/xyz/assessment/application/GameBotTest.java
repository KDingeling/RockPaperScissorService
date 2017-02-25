package com.xyz.assessment.application;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameBotTest {
	
	private final Logger logger = LoggerFactory.getLogger(GameBotTest.class);
	
	private List<String> someRandomStrings = new LinkedList<String>();
	
	@Before
	public void setup() {
		someRandomStrings.add("1");
		someRandomStrings.add("2");
		someRandomStrings.add("3");
		someRandomStrings.add("4");
		someRandomStrings.add("5");
	}
	
	@Test
	public void testGetRandomObject() {
		// there is no simple way to test randomness, has to be done visually
		for (int i = 0; i < 20; i++) {
			logger.debug("getting random object [{}]: {}", i, GameBot.getRandomObject(someRandomStrings));
		}
	}
}
