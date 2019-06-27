/*
 * Copyright 2016-2019 Axioma srl.
 * 
 * Licensed under the Commercial Holon Platform Module License Version 1 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * https://docs.holon-platform.com/license/chpml_v1.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.holonplatform.artisan.vaadin.flow.components.internal;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterComponent;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterGroup;
import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterComponentBuilder;
import com.holonplatform.core.i18n.Localizable;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyRenderer;
import com.holonplatform.core.query.QueryFilter;
import com.holonplatform.vaadin.flow.components.Composable;
import com.holonplatform.vaadin.flow.components.ValueHolder.ValueChangeListener;
import com.holonplatform.vaadin.flow.components.builders.ComponentConfigurator;
import com.holonplatform.vaadin.flow.components.events.GroupValueChangeEvent;
import com.holonplatform.vaadin.flow.i18n.LocalizationProvider;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.dom.DomEventListener;
import com.vaadin.flow.dom.Element;

/**
 * Default {@link InputFilterComponent} implementation.
 * 
 * @param <C> Content component type
 *
 * @since 1.0.0
 */
public class DefaultInputFilterComponent<C extends Component> implements InputFilterComponent {

	private static final long serialVersionUID = -38314217172619843L;

	/**
	 * Form content initializer
	 */
	private Consumer<C> initializer;

	/**
	 * Composer
	 */
	private Composer<? super C, InputFilter<?>, InputFilterGroup> composer;

	/**
	 * Compose on attach behaviour
	 */
	private boolean composeOnAttach = true;

	/**
	 * Whether content component was initialized
	 */
	private boolean contentInitialized = false;

	/**
	 * Composition state
	 */
	private boolean composed = false;

	/**
	 * Content component
	 */
	private final C content;

	/**
	 * Backing group
	 */
	private InputFilterGroup group;

	/**
	 * Custom property captions
	 */
	private final Map<Property<?>, Localizable> propertyCaptions = new HashMap<>(8);

	/**
	 * Constructor.
	 * @param content Component content (not null)
	 */
	public DefaultInputFilterComponent(C content) {
		super();
		Obj.argumentNotNull(content, "Content component must be not null");
		this.content = content;
		this.content.getElement().setAttribute("input-filter-group-content", "");
		// compose on attach
		content.addAttachListener(e -> {
			// check compose on attach if not already composed
			if (!isComposed() && isComposeOnAttach()) {
				compose();
			}
		});
	}

	/**
	 * Get the form content component.
	 * @return the form content component
	 */
	protected C getContent() {
		return content;
	}

	/**
	 * Gets whether the form was already composed.
	 * @return whether the form was already composed
	 */
	protected boolean isComposed() {
		return composed;
	}

	/**
	 * Get the form content initializer
	 * @return the form content initializer
	 */
	public Optional<Consumer<C>> getInitializer() {
		return Optional.ofNullable(initializer);
	}

	/**
	 * Set the form content initializer
	 * @param initializer the initializer to set
	 */
	public void setInitializer(Consumer<C> initializer) {
		this.initializer = initializer;
	}

	/**
	 * Get the composer, if available.
	 * @return Optional composer
	 */
	public Optional<Composer<? super C, InputFilter<?>, InputFilterGroup>> getComposer() {
		return Optional.ofNullable(composer);
	}

	/**
	 * Set the composer
	 * @param composer the composer to set
	 */
	public void setComposer(Composer<? super C, InputFilter<?>, InputFilterGroup> composer) {
		this.composer = composer;
	}

	/**
	 * Gets whether the form must be composed on content component <code>attach</code>, if not already composed invoking
	 * {@link #compose()}.
	 * @return <code>true</code> if the form must be composed on content component <code>attach</code>
	 */
	public boolean isComposeOnAttach() {
		return composeOnAttach;
	}

	/**
	 * Sets whether the form must be composed on content component <code>attach</code>, if not already composed invoking
	 * {@link #compose()}.
	 * @param composeOnAttach <code>true</code> to compose the form on content component <code>attach</code>
	 */
	public void setComposeOnAttach(boolean composeOnAttach) {
		this.composeOnAttach = composeOnAttach;
	}

	/**
	 * Get the {@link InputFilterGroup}.
	 * @return the {@link InputFilterGroup}
	 */
	protected InputFilterGroup getComponentGroup() {
		return group;
	}

	/**
	 * Set the backing group.
	 * @param group the group to set (not null)
	 */
	protected void setComponentGroup(InputFilterGroup group) {
		Obj.argumentNotNull(group, "Component group must be not null");
		this.group = group;
	}

	@Override
	public Component getComponent() {
		return getContent();
	}

	@Override
	public Collection<Property<?>> getProperties() {
		return getComponentGroup().getProperties();
	}

	@Override
	public <T> Optional<InputFilter<T>> getInputFilter(Property<T> property) {
		return getComponentGroup().getInputFilter(property);
	}

	@Override
	public Optional<QueryFilter> getFilter() {
		return getComponentGroup().getFilter();
	}

	@Override
	public void reset() {
		getComponentGroup().reset();
	}

	@Override
	public Stream<Binding<Property<?>, InputFilter<?>>> getBindings() {
		return getComponentGroup().getBindings();
	}

	@Override
	public Optional<InputFilter<?>> getElement(Property<?> property) {
		return getComponentGroup().getElement(property);
	}

	@Override
	public Stream<InputFilter<?>> getElements() {
		return getComponentGroup().getElements();
	}

	/**
	 * Set the caption for the component bound to given property
	 * @param property Property
	 * @param caption Localizable caption
	 */
	protected void setPropertyCaption(Property<?> property, Localizable caption) {
		if (property != null && caption != null) {
			propertyCaptions.put(property, caption);
		}
	}

	/**
	 * Configure given component using given property.
	 * @param property Property to which the component refers
	 * @param component Component to configure
	 */
	protected void configurePropertyComponent(Property<?> property, InputFilter<?> component) {
		if (component != null) {
			component.hasLabel().ifPresent(hasLabel -> {
				if (propertyCaptions.containsKey(property)) {
					LocalizationProvider.localize(propertyCaptions.get(property))
							.ifPresent(message -> hasLabel.setLabel(message));
				} else {
					if (hasLabel.getLabel() == null || hasLabel.getLabel().trim().equals("")) {
						// default behaviour
						hasLabel.setLabel(LocalizationProvider.localize(property).orElseGet(() -> property.getName()));
					}
				}
			});
		}
	}

	@Override
	public void compose() {
		// content initializer
		getInitializer().ifPresent(i -> {
			if (!contentInitialized) {
				i.accept(getContent());
				contentInitialized = true;
			}
		});

		// compose
		getComposer().orElseThrow(() -> new IllegalStateException("No composer available")).compose(getContent(),
				getComponentGroup());

		this.composed = true;
	}

	// Builder

	/**
	 * Default {@link InputFilterComponentBuilder} implementation.
	 * 
	 * @param <C> Content type
	 */
	public static class DefaultBuilder<C extends Component> implements InputFilterComponentBuilder<C> {

		private final C content;

		private final DefaultInputFilterComponent<C> instance;

		private final DefaultInputFilterGroup.InternalBuilder groupBuilder;

		private final BaseComponentConfigurator componentConfigurator;

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public <P extends Property<?>> DefaultBuilder(C content, Iterable<P> properties) {
			super();
			Obj.argumentNotNull(content, "Content must be not null");
			this.content = content;
			this.componentConfigurator = ComponentConfigurator.create(content);
			this.instance = new DefaultInputFilterComponent<>(content);
			this.groupBuilder = new DefaultInputFilterGroup.InternalBuilder(properties);
			// setup default composer
			if (content instanceof HasComponents) {
				instance.setComposer((Composer) Composable.componentContainerComposer());
			}
		}

		protected C getContent() {
			return content;
		}

		@Override
		public InputFilterComponentBuilder<C> id(String id) {
			componentConfigurator.id(id);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> visible(boolean visible) {
			componentConfigurator.visible(visible);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> elementConfiguration(Consumer<Element> element) {
			componentConfigurator.elementConfiguration(element);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> withAttachListener(ComponentEventListener<AttachEvent> listener) {
			componentConfigurator.withAttachListener(listener);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> withDetachListener(ComponentEventListener<DetachEvent> listener) {
			componentConfigurator.withDetachListener(listener);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> withThemeName(String themeName) {
			componentConfigurator.withThemeName(themeName);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> withEventListener(String eventType, DomEventListener listener) {
			componentConfigurator.withEventListener(eventType, listener);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> withEventListener(String eventType, DomEventListener listener,
				String filter) {
			componentConfigurator.withEventListener(eventType, listener, filter);
			return this;
		}

		@Override
		public <T> InputFilterComponentBuilder<C> hidden(Property<T> property) {
			groupBuilder.hidden(property);
			return this;
		}

		@Override
		public <T> InputFilterComponentBuilder<C> bind(Property<T> property,
				PropertyRenderer<InputFilter<T>, T> renderer) {
			groupBuilder.bind(property, renderer);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> withPostProcessor(BiConsumer<Property<?>, InputFilter<?>> postProcessor) {
			groupBuilder.withPostProcessor(postProcessor);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> initializer(Consumer<C> initializer) {
			Obj.argumentNotNull(initializer, "Form content initializer must be not null");
			instance.setInitializer(initializer);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> composer(Composer<? super C, InputFilter<?>, InputFilterGroup> composer) {
			Obj.argumentNotNull(composer, "Composer must be not null");
			instance.setComposer(composer);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> composeOnAttach(boolean composeOnAttach) {
			instance.setComposeOnAttach(composeOnAttach);
			return this;
		}

		@Override
		public InputFilterComponentBuilder<C> propertyCaption(Property<?> property, Localizable caption) {
			Obj.argumentNotNull(property, "Property must be not null");
			Obj.argumentNotNull(caption, "Caption must be not null");
			instance.setPropertyCaption(property, caption);
			return this;
		}

		@Override
		public <V> InputFilterComponentBuilder<C> withValueChangeListener(Property<V> property,
				ValueChangeListener<V, GroupValueChangeEvent<V, Property<?>, InputFilter<?>, InputFilterGroup>> listener) {
			groupBuilder.withValueChangeListener(property, listener);
			return this;
		}

		@Override
		public InputFilterComponent build() {
			instance.setComponentGroup(groupBuilder.withPostProcessor((property, component) -> {
				instance.configurePropertyComponent(property, component);
			}).build());
			return instance;
		}

	}

}
