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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.holonplatform.artisan.core.utils.Obj;
import com.holonplatform.artisan.vaadin.flow.components.InputFilter;
import com.holonplatform.artisan.vaadin.flow.components.InputFilterGroup;
import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterGroupBuilder;
import com.holonplatform.artisan.vaadin.flow.components.builders.InputFilterGroupConfigurator;
import com.holonplatform.artisan.vaadin.flow.components.internal.support.InputFilterPropertyConfiguration;
import com.holonplatform.artisan.vaadin.flow.components.internal.support.InputFilterPropertyConfigurationRegistry;
import com.holonplatform.artisan.vaadin.flow.components.internal.support.InputFilterPropertyRegistry;
import com.holonplatform.core.property.Property;
import com.holonplatform.core.property.PropertyRenderer;
import com.holonplatform.core.property.PropertyRendererRegistry;
import com.holonplatform.core.property.PropertyRendererRegistry.NoSuitableRendererAvailableException;
import com.holonplatform.core.property.PropertySet;
import com.holonplatform.core.query.QueryFilter;

/**
 * Default {@link InputFilterGroup} implementation.
 *
 * @since 1.0.0
 */
public class DefaultInputFilterGroup implements InputFilterGroup {

	private static final long serialVersionUID = -3609142192627581494L;

	/**
	 * Property set
	 */
	private final PropertySet<?> propertySet;

	/**
	 * Optional {@link PropertyRendererRegistry} to use
	 */
	private PropertyRendererRegistry propertyRendererRegistry;

	/**
	 * Post-processors
	 */
	private final List<BiConsumer<Property<?>, InputFilter<?>>> postProcessors = new LinkedList<>();

	/**
	 * Property components
	 */
	private final InputFilterPropertyRegistry components = InputFilterPropertyRegistry.create();

	/**
	 * Property configurations
	 */
	private final InputFilterPropertyConfigurationRegistry configuration = InputFilterPropertyConfigurationRegistry
			.create();

	/**
	 * Constructor.
	 * @param propertySet The property set (not null)
	 */
	public DefaultInputFilterGroup(PropertySet<?> propertySet) {
		super();
		Obj.argumentNotNull(propertySet, "PropertySet must be not null");
		this.propertySet = propertySet;
	}

	/**
	 * Get the property set.
	 * @return the property set
	 */
	protected PropertySet<?> getPropertySet() {
		return propertySet;
	}

	/**
	 * Get the {@link InputFilterPropertyConfiguration} bound to given property.
	 * @param <T> Property type
	 * @param property The property for which to obtain the configuration (not null)
	 * @return The property configuration
	 */
	protected <T> InputFilterPropertyConfiguration<T> getPropertyConfiguration(Property<T> property) {
		return configuration.get(property);
	}

	/**
	 * Get the specific {@link PropertyRendererRegistry} to use to render the components.
	 * @return Optional property renderer registry
	 */
	protected Optional<PropertyRendererRegistry> getPropertyRendererRegistry() {
		return Optional.ofNullable(propertyRendererRegistry);
	}

	/**
	 * Set the specific {@link PropertyRendererRegistry} to use to render the components.
	 * @param propertyRendererRegistry the property renderer registry to set
	 */
	protected void setPropertyRendererRegistry(PropertyRendererRegistry propertyRendererRegistry) {
		this.propertyRendererRegistry = propertyRendererRegistry;
	}

	/**
	 * Register a value component post-processor.
	 * @param postProcessor the post-processor to register (not null)
	 */
	protected void addPostProcessor(BiConsumer<Property<?>, InputFilter<?>> postProcessor) {
		Obj.argumentNotNull(postProcessor, "Post processor must be not null");
		postProcessors.add(postProcessor);
	}

	/**
	 * Get the value component post-processors.
	 * @return the post processors
	 */
	protected List<BiConsumer<Property<?>, InputFilter<?>>> getPostProcessors() {
		return postProcessors;
	}

	@Override
	public Collection<Property<?>> getProperties() {
		return getPropertySet().stream().map(p -> (Property<?>) p).collect(Collectors.toList());
	}

	@Override
	public Stream<Binding<Property<?>, InputFilter<?>>> getBindings() {
		return components.stream();
	}

	@Override
	public Optional<InputFilter<?>> getElement(Property<?> property) {
		return components.get(property).map(i -> i);
	}

	@Override
	public Stream<InputFilter<?>> getElements() {
		return components.stream().map(b -> b.getElement());
	}

	@Override
	public <T> Optional<InputFilter<T>> getInputFilter(Property<T> property) {
		return components.get(property);
	}

	@Override
	public Optional<QueryFilter> getFilter() {
		final List<QueryFilter> filters = components.stream().filter(b -> b.getElement() != null)
				.map(b -> b.getElement().getFilter()).flatMap(o -> o.isPresent() ? Stream.of(o.get()) : Stream.empty())
				.collect(Collectors.toList());
		return QueryFilter.allOf(filters);
	}

	@Override
	public void reset() {
		components.stream().filter(b -> b.getElement() != null).map(b -> b.getElement()).forEach(i -> i.reset());
	}

	// ------- building methods

	/**
	 * Build and bind {@link InputFilter}s to the properties of the property set.
	 */
	@SuppressWarnings("unchecked")
	protected void build() {
		components.clear();
		// render and bind components
		getPropertySet().stream().filter(property -> !configuration.get(property).isHidden())
				.forEach(property -> renderAndBind(property));
	}

	/**
	 * Render given property as a {@link InputFilter} and register the binding.
	 * @param <T> Property type
	 * @param property The property to render and bind
	 */
	protected <T> void renderAndBind(final Property<T> property) {
		// configuration
		final InputFilterPropertyConfiguration<T> propertyConfiguration = configuration.get(property);
		// render
		final InputFilter<T> component = render(propertyConfiguration)
				// configure input filter
				.map(input -> configureInputFilter(propertyConfiguration, input))
				// exception when InputFilter not available
				.orElseThrow(() -> new NoSuitableRendererAvailableException(
						"No renderer available to render the property [" + property + "] as an InputFilter"));
		// configure
		getPostProcessors().forEach(postProcessor -> postProcessor.accept(property, component));
		// register
		components.set(property, component);
	}

	/**
	 * Configure the {@link InputFilter} component using given configuration.
	 * @param <T> Property type
	 * @param configuration Property configuration (not null)
	 * @param input The {@link InputFilter} component to configure
	 * @return The configured {@link InputFilter}
	 */
	protected <T> InputFilter<T> configureInputFilter(final InputFilterPropertyConfiguration<T> configuration,
			final InputFilter<T> input) {
		return input;
	}

	/**
	 * Render given property configuration as a {@link InputFilter}.
	 * @param <T> Property type
	 * @param propertyConfiguration Property configuration
	 * @return Optional rendered component
	 */
	@SuppressWarnings("unchecked")
	protected <T> Optional<InputFilter<T>> render(InputFilterPropertyConfiguration<T> propertyConfiguration) {
		// check custom renderer
		Optional<InputFilter<T>> component = propertyConfiguration.getRenderer()
				.map(r -> r.render(propertyConfiguration.getProperty()));
		if (component.isPresent()) {
			return component;
		}
		// check specific registry
		if (getPropertyRendererRegistry().isPresent()) {
			return getPropertyRendererRegistry().get()
					.getRenderer(InputFilter.class, propertyConfiguration.getProperty())
					.map(r -> r.render(propertyConfiguration.getProperty()));
		} else {
			// use default
			return propertyConfiguration.getProperty().renderIfAvailable(InputFilter.class)
					.map(c -> (InputFilter<T>) c);
		}
	}

	// ------- builders

	/**
	 * {@link InputFilterGroupBuilder} builder.
	 */
	static class InternalBuilder extends AbstractBuilder<InternalBuilder> {

		public <P extends Property<?>> InternalBuilder(Iterable<P> properties) {
			super(properties);
		}

		@Override
		protected InternalBuilder builder() {
			return this;
		}

		public DefaultInputFilterGroup build() {
			instance.build();
			return instance;
		}

	}

	/**
	 * Default {@link InputFilterGroupBuilder} implementation.
	 */
	public static class DefaultBuilder extends AbstractBuilder<InputFilterGroupBuilder>
			implements InputFilterGroupBuilder {

		public <P extends Property<?>> DefaultBuilder(Iterable<P> properties) {
			super(properties);
		}

		@Override
		protected InputFilterGroupBuilder builder() {
			return this;
		}

		@Override
		public InputFilterGroup build() {
			instance.build();
			return instance;
		}

	}

	/**
	 * Abstract configurator.
	 * 
	 * @param <B> Concrete builder type
	 */
	public abstract static class AbstractBuilder<B extends InputFilterGroupConfigurator<B>>
			implements InputFilterGroupConfigurator<B> {

		protected final DefaultInputFilterGroup instance;

		public <P extends Property<?>> AbstractBuilder(Iterable<P> properties) {
			super();
			Obj.argumentNotNull(properties, "Properties must be not null");
			this.instance = new DefaultInputFilterGroup(
					(properties instanceof PropertySet<?>) ? (PropertySet<?>) properties : PropertySet.of(properties));
		}

		protected abstract B builder();

		@Override
		public <T> B hidden(Property<T> property) {
			Obj.argumentNotNull(property, "Property must be not null");
			instance.getPropertyConfiguration(property).setHidden(true);
			return builder();
		}

		@Override
		public <T> B bind(Property<T> property, PropertyRenderer<InputFilter<T>, T> renderer) {
			Obj.argumentNotNull(property, "Property must be not null");
			Obj.argumentNotNull(renderer, "Renderer must be not null");
			instance.getPropertyConfiguration(property).setRenderer(renderer);
			return builder();
		}

		@Override
		public B withPostProcessor(BiConsumer<Property<?>, InputFilter<?>> postProcessor) {
			Obj.argumentNotNull(postProcessor, "Post processor must be not null");
			instance.addPostProcessor(postProcessor);
			return builder();
		}

	}

}
