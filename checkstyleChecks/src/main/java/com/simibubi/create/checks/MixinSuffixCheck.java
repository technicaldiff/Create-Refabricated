package com.simibubi.create.checks;

import com.puppycrawl.tools.checkstyle.api.AbstractCheck;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class MixinSuffixCheck extends AbstractCheck {
	private String mixinAnnotation = "Mixin";
	private String suffix = mixinAnnotation;

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

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

		if (Utils.findMixin(mixinAnnotation, ast.findFirstToken(TokenTypes.MODIFIERS)) && !ast.findFirstToken(TokenTypes.IDENT).getText().endsWith("Mixin")) {
			log(ast.getLineNo(), "All @" + mixinAnnotation + "-annotated classes' names must end with " + suffix + "!");
		}
	}
}
