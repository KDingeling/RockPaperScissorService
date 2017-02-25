package com.xyz.assessment.objectset;

import java.util.LinkedList;
import java.util.List;

public class ObjectSetHolder {

	public static final String ROCK = "rock";
	public static final String PAPER = "paper";
	public static final String SCISSOR = "scissor";
	public static final String WELL = "well";

	@SuppressWarnings("serial")
	public static final List<String> objectSetA = new LinkedList<String>() {
		{
			add(ROCK);
			add(PAPER);
			add(SCISSOR);
		}
	};

	@SuppressWarnings("serial")
	public static final List<String> objectSetB = new LinkedList<String>() {
		{
			add(ROCK);
			add(PAPER);
			add(SCISSOR);
			add(WELL);
		}
	};
}
