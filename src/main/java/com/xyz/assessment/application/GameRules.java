package com.xyz.assessment.application;

import java.util.List;

import com.xyz.assessment.exception.UnrecognizedObjectException;
import com.xyz.assessment.objectset.ObjectSetHolder;

public class GameRules {

	/**
	 * Checks if the given objects result in a win for the first object.
	 * 
	 * @param myObject
	 *            The object that is the context for win or loss.
	 * @param otherObject
	 *            The opponents object.
	 * @param objectSet
	 *            The objectSet.
	 * @return
	 * @throws UnrecognizedObjectException
	 */
	public static boolean isWin(String myObject, String otherObject, List<String> objectSet)
			throws UnrecognizedObjectException {
		requireObjectExistsInObjectSets(myObject, objectSet);
		requireObjectExistsInObjectSets(otherObject, objectSet);

		if (ObjectSetHolder.PAPER.equals(myObject)) {
			//@formatter:off
			return ObjectSetHolder.ROCK.equals(otherObject)
				|| ObjectSetHolder.WELL.equals(otherObject);
			//@formatter:on

		} else if (ObjectSetHolder.ROCK.equals(myObject)) {
			return ObjectSetHolder.SCISSOR.equals(otherObject);

		} else if (ObjectSetHolder.SCISSOR.equals(myObject)) {
			return ObjectSetHolder.PAPER.equals(otherObject);

		} else if (ObjectSetHolder.WELL.equals(myObject)) {
			//@formatter:off
			return ObjectSetHolder.ROCK.equals(otherObject)
				|| ObjectSetHolder.SCISSOR.equals(otherObject);
			//@formatter:on
		} else {
			// can't happen since we check for existence in
			// #requireObjectExistsInObjectSets
			return false;
		}
	}

	private static void requireObjectExistsInObjectSets(String object, List<String> objectSet) {
		if (!objectSet.contains(object)) {
			throw new UnrecognizedObjectException("Object '" + object + "' is not known in any objectSet");
		}
	}
}
