package com.simibubi.create.checks;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public final class Utils {
	private Utils() {}

	public static boolean findMixin(final String mixinAnnotation, final DetailAST classModifiers) {
		if (classModifiers.getChildCount(TokenTypes.ANNOTATION) < 1) return false;

		for (DetailAST annotation = classModifiers.findFirstToken(TokenTypes.ANNOTATION);
			 annotation != null && annotation.getType() == TokenTypes.ANNOTATION; // Cursedest for loop to exist
			 annotation = annotation.getNextSibling()) {
			if (annotation.findFirstToken(TokenTypes.IDENT).getText().equals(mixinAnnotation)) return true;
		}
		return false;
	}
}
