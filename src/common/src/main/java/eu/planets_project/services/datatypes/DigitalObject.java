/******************************************************************************
 * Copyright (c) 2007, 2010 The Planets Project Partners
 
 * All rights reserved. This program and the accompanying
 * materials are made available under the terms of the
 * Apache License, Version 2.0 which accompanies
 * this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.
 
 *******************************************************************************

/**
 * A representation of a digital object. Instances are created using a builder
 * to allow optional named constructor parameters and ensure consistent state
 * during creation. E.g. to create a digital object with only the required
 * argument, you'd use:
 * <p/>
 * {@code DigitalObject o = new DigitalObject.Builder(content).build();}
 * <p/>
 * You can cascade additional calls for optional arguments:
 * <p/>
 * {@code DigitalObject o = new
 * DigitalObject.Builder(content).manifestationOf(abstraction
 * ).title(title).build();}
 * <p/>
 * DigitalObject instances can be serialized to XML. Given such an XML
 * representation, a digital object can be instantiated using the builder:
 * <p/>
 * {@code DigitalObject o = new DigitalObject.Builder(xml).build();}
 * <p/>
 * For usage examples, see the tests in {@link DigitalObjectTests} and web
 * service sample usage in pserv/IF/simple.
 * @author Fabian Steeg
 */

package eu.planets_project.services.datatypes;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlJavaTypeAdapter(DigitalObject.Adapter.class)
@XmlRootElement(name = "digitalObject")
public interface DigitalObject {

  /** @return The title of this digital object. */
  String getTitle();

  /** @return The type of this digital object. */
  URI getFormat();

  /** @return The unique identifier. */
  URI getPermanentUri();

  /** @return The URI that this digital object is a manifestation of. */
  URI getManifestationOf();

  /**
   * @return Additional repository-specific metadata. Returns a defensive copy, changes to the obtained list won't affect this digital
   *         object.
   */
  List<Metadata> getMetadata();

  /**
   * @return The actual content reference. Required. Returns a defensive copy, changes to the obtained instance won't affect this digital
   *         object.
   */
  DigitalObjectContent getContent();

  /**
   * @return The 0..n events that happened to this digital object. Returns a defensive copy, changes to the obtained list won't affect this
   *         digital object.
   */
  List<Event> getEvents();

  /**
   * @return The 0..n fragment IDs this digital object consists of. Returns a defensive copy, changes to the obtained list won't affect this
   *         digital object. If required, a future version of the framework might use a complex type to represent a fragment.
   */
  List<String> getFragments();

  /**
   * @return An XML representation of this digital object (can be used to instantiate an object using the builder constructor)
   */
  String toXml();

  /* Same approach as above, but for the DigitalObject itself. */
  /** Adapter for serialization of DigitalObject interface instances. */
  @SuppressWarnings("hiding")
static class Adapter<ImmutableDigitalObject, DigitalObject> extends XmlAdapter<ImmutableDigitalObject, DigitalObject> {
	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#unmarshal(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public DigitalObject unmarshal(final ImmutableDigitalObject o) {
	  return (DigitalObject) o;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see javax.xml.bind.annotation.adapters.XmlAdapter#marshal(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public ImmutableDigitalObject marshal(final DigitalObject o) {
	  // FIXME: This causes Marshal Exception because the ImmutableDigitalObject is unknown
		return (ImmutableDigitalObject) o;
	  }

	
  }

  /**
   * Builder for DigitalObject instances. Using a builder ensures consistent object state during creation and models optional named
   * constructor parameters.
   * 
   * @see eu.planets_project.services.datatypes.DigitalObjectTests
   */
  public static final class Builder {
	/* Required parameter: */
	private DigitalObjectContent content;
	/* Optional parameters, initialized to default values: */
	private URI permanentUri = null;
	private List<Event> events = new ArrayList<Event>();
	private List<String> fragments = new ArrayList<String>();
	private URI manifestationOf = null;
	private List<Metadata> metadata = new ArrayList<Metadata>();
	private URI format = null;
	private String title = null;

	/** @return The instance created using this builder. */
	public DigitalObject build() {
	  return new ImmutableDigitalObject(this);
	}

	/**
	 * Constructs an anonymous (permanentUri == null) digital object. To set further attributes, call the desired methods on the resulting
	 * builder.
	 * 
	 * @param content
	 *          The content of the digital object, see static factory methods in {@link Content} for different ways of content creation,
	 *          e.g. {@code Content.byReference(file)}.
	 */
	public Builder(final DigitalObjectContent content) {
	  this.content = content;
	}

	/**
	 * @param digitalObject
	 *          An existing digital object to copy into an new anonymous (permanentUri == null) digital object.
	 */
	public Builder(final DigitalObject digitalObject) {
	  content = digitalObject.getContent();
	  events = digitalObject.getEvents();
	  fragments = digitalObject.getFragments();
	  manifestationOf = digitalObject.getManifestationOf();
	  title = digitalObject.getTitle();
	  metadata = digitalObject.getMetadata();
	  format = digitalObject.getFormat();
	}

	/**
	 * Creates a builder that will build a digital object identical to the given object, including the permanent URI.
	 * 
	 * @param digitalObjectXml
	 *          An XML representation of a digital object.
	 */
	public Builder(final String digitalObjectXml) {
	  if (digitalObjectXml == null) {
		throw new IllegalArgumentException("Cannot create digital object for null string");
	  }
	  /*
	   * Besides the adapter, this is the second place where we mention the implementation class, but as before, this is behind the
	   * interface.
	   */
	  ImmutableDigitalObject digitalObject = ImmutableDigitalObject.of(digitalObjectXml);
	  permanentUri = digitalObject.getPermanentUri();
	  content = digitalObject.getContent();
	  events = digitalObject.getEvents();
	  fragments = digitalObject.getFragments();
	  manifestationOf = digitalObject.getManifestationOf();
	  title = digitalObject.getTitle();
	  metadata = digitalObject.getMetadata();
	  format = digitalObject.getFormat();
	}

	/** No-arg constructor for JAXB. */
	@SuppressWarnings("unused")
	private Builder() {
	}

	/**
	 * @param content
	 *          The new content for the digital object to be created, see static factory methods in {@link Content} for different ways of
	 *          content creation, e.g. {@code Content.byReference(file)}.
	 * @return The builder, for cascaded calls
	 */
	public Builder content(final DigitalObjectContent content) {
	  this.content = content;
	  return this;
	}

	/**
	 * @param permanentUri
	 *          The globally unique identifier for this digital object.
	 * @return The builder, for cascaded calls
	 */
	public Builder permanentUri(final URI permanentUri) {
	  this.permanentUri = permanentUri;
	  return this;
	}

	/**
	 * @param events
	 *          The events of the digital object
	 * @return The builder, for cascaded calls
	 */
	public Builder events(final Event... events) {
	  this.events = new ArrayList<Event>(Arrays.asList(events));
	  return this;
	}

	/**
	 * @param fragments
	 *          The fragments the digital object is made of
	 * @return The builder, for cascaded calls
	 */
	public Builder fragments(final String... fragments) {
	  this.fragments = new ArrayList<String>(Arrays.asList(fragments));
	  return this;
	}

	/**
	 * @param manifestationOf
	 *          What the digital object is a manifestation of
	 * @return The builder, for cascaded calls
	 */
	public Builder manifestationOf(final URI manifestationOf) {
	  this.manifestationOf = manifestationOf;
	  return this;
	}

	/**
	 * @param title
	 *          The title of the digital object
	 * @return The builder, for cascaded calls
	 */
	public Builder title(final String title) {
	  this.title = title;
	  return this;
	}

	/**
	 * @param metadata
	 *          Additional metadata for the digital object
	 * @return The builder, for cascaded calls
	 */
	public Builder metadata(final Metadata... metadata) {
	  this.metadata = new ArrayList<Metadata>(Arrays.asList(metadata));
	  return this;
	}

	/**
	 * @param format
	 *          The type of the digital object
	 * @return The builder, for cascaded calls
	 */
	public Builder format(final URI format) {
	  this.format = format;
	  return this;
	}

	/**
	 * @return The content
	 * @see DigitalObject#getContent()
	 */
	public DigitalObjectContent getContent() {
	  return content;
	}

	/**
	 * @return The permanent URI
	 * @see DigitalObject#getPermanentUri()
	 */
	public URI getPermanentUri() {
	  return permanentUri;
	}

	/**
	 * @return The events
	 * @see DigitalObject#getEvents()
	 */
	public List<Event> getEvents() {
	  return events;
	}

	/**
	 * @return The fragments
	 * @see DigitalObject#getFragments()
	 */
	public List<String> getFragments() {
	  return fragments;
	}

	/**
	 * @return The abstraction this object is a manifestation of
	 * @see DigitalObject#getManifestationOf()
	 */
	public URI getManifestationOf() {
	  return manifestationOf;
	}

	/**
	 * @return The metadata
	 * @see DigitalObject#getMetadata()
	 */
	public List<Metadata> getMetadata() {
	  return metadata;
	}

	/**
	 * @return The format
	 * @see DigitalObject#getFormat()
	 */
	public URI getFormat() {
	  return format;
	}

	/**
	 * @return The title
	 * @see DigitalObject#getTitle()
	 */
	public String getTitle() {
	  return title;
	}
  }
}
