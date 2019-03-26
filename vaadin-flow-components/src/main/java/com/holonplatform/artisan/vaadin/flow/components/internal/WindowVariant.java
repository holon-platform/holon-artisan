package com.holonplatform.artisan.vaadin.flow.components.internal;

public enum WindowVariant {

	UNSET_WIDTH("window-unset-width"), UNSET_HEIGHT("window-unset-height"), FULL_WIDTH(
			"window-full-width"), FULL_HEIGHT("window-full-height"), SCROLLABLE("window-scrollable");

	private final String variant;

	WindowVariant(String variant) {
		this.variant = variant;
	}

	public String getVariantName() {
		return variant;
	}
}
