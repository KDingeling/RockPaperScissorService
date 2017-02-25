package com.xyz.assessment.application;

import com.xyz.assessment.exception.UnrecognizedObjectException;
import com.xyz.assessment.objectset.ObjectSetHolder;
import org.junit.Assert;
import org.junit.Test;

public class GameRulesTest {

	@Test
	public void testRockResults() {
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.ROCK, ObjectSetHolder.SCISSOR, ObjectSetHolder.objectSetA));
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.ROCK, ObjectSetHolder.SCISSOR, ObjectSetHolder.objectSetB));

		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.ROCK, ObjectSetHolder.PAPER, ObjectSetHolder.objectSetA));
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.ROCK, ObjectSetHolder.PAPER, ObjectSetHolder.objectSetB));

		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.ROCK, ObjectSetHolder.WELL, ObjectSetHolder.objectSetB));
	}
	
	@Test
	public void testPaperResults() {
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.PAPER, ObjectSetHolder.ROCK, ObjectSetHolder.objectSetA));
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.PAPER, ObjectSetHolder.ROCK, ObjectSetHolder.objectSetA));
		
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.PAPER, ObjectSetHolder.WELL, ObjectSetHolder.objectSetB));
		
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.PAPER, ObjectSetHolder.SCISSOR, ObjectSetHolder.objectSetA));
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.PAPER, ObjectSetHolder.SCISSOR, ObjectSetHolder.objectSetB));
	}
	
	@Test
	public void testScissorResults() {
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.SCISSOR, ObjectSetHolder.PAPER, ObjectSetHolder.objectSetA));
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.SCISSOR, ObjectSetHolder.PAPER, ObjectSetHolder.objectSetB));
		
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.SCISSOR, ObjectSetHolder.ROCK, ObjectSetHolder.objectSetA));
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.SCISSOR, ObjectSetHolder.ROCK, ObjectSetHolder.objectSetB));

		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.SCISSOR, ObjectSetHolder.WELL, ObjectSetHolder.objectSetB));
	}
	
	@Test
	public void testWellResults() {
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.WELL, ObjectSetHolder.ROCK, ObjectSetHolder.objectSetB));
		
		Assert.assertTrue(GameRules.isWin(ObjectSetHolder.WELL, ObjectSetHolder.SCISSOR, ObjectSetHolder.objectSetB));
		
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.WELL, ObjectSetHolder.PAPER, ObjectSetHolder.objectSetB));
	}

	@Test(expected = UnrecognizedObjectException.class)
	public void testImpossibleTurns1() {
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.WELL, ObjectSetHolder.PAPER, ObjectSetHolder.objectSetA));
	}
	
	@Test(expected = UnrecognizedObjectException.class)
	public void testImpossibleTurns2() {
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.PAPER, ObjectSetHolder.WELL, ObjectSetHolder.objectSetA));
	}
	
	@Test(expected = UnrecognizedObjectException.class)
	public void testImpossibleTurns3() {
		Assert.assertFalse(GameRules.isWin(ObjectSetHolder.WELL, ObjectSetHolder.WELL, ObjectSetHolder.objectSetA));
	}
	
	@Test(expected = UnrecognizedObjectException.class)
	public void testUnspecifiedInput1() {
		GameRules.isWin("Hello World", ObjectSetHolder.ROCK, ObjectSetHolder.objectSetA);
	}
	
	@Test(expected = UnrecognizedObjectException.class)
	public void testUnspecifiedInput2() {
		GameRules.isWin(ObjectSetHolder.PAPER, "Hello World", ObjectSetHolder.objectSetA);
	}
	
	@Test(expected = UnrecognizedObjectException.class)
	public void testNullInput1() {
		GameRules.isWin(ObjectSetHolder.SCISSOR, null, ObjectSetHolder.objectSetA);
	}
	
	@Test(expected = UnrecognizedObjectException.class)
	public void testNullInpu2() {
		GameRules.isWin(null, ObjectSetHolder.WELL, ObjectSetHolder.objectSetB);
	}
}
