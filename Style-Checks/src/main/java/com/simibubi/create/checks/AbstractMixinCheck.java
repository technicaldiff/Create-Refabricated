package com.simibubi.create.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AbstractMixinCheck extends AbstractCheck {
	private String mixinAnnotation = "Mixin";

	public String getMixinAnnotation() {
		return mixinAnnotation;
	}

	public void setMixinAnnotation(String mixinAnnotation) {
		this.mixinAnnotation = mixinAnnotation;
	}

	@Override
	public int[] getDefaultTokens() {
		return new int[]{TokenTypes.CLASS_DEF};
	}

	@Override
	public int[] getAcceptableTokens() {
		return getDefaultTokens();
	}

	@Override
	public int[] getRequiredTokens() {
		return getDefaultTokens();
	}

	@Override
	public void visitToken(DetailAST ast) {
		if (ast.getChildCount(TokenTypes.MODIFIERS) < 1) return;
		DetailAST modifiers = ast.findFirstToken(TokenTypes.MODIFIERS);

		if (Utils.findMixin(mixinAnnotation, modifiers) && modifiers.getChildCount(TokenTypes.ABSTRACT) < 1) {
			log(ast.getLineNo(), "All @" + mixinAnnotation + "-annotated classes must be abstract!");
		}
	}
}
