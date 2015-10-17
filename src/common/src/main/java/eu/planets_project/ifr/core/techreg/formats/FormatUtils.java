/*******************************************************************************
 * Copyright (c) 2007, 2010 The Planets Project Partners.
 *
 * All rights reserved. This program and the accompanying 
 * materials are made available under the terms of the 
 * Apache License, Version 2.0 which accompanies 
 * this distribution, and is available at 
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *******************************************************************************/
package eu.planets_project.ifr.core.techreg.formats;

import java.net.URI;
import java.util.Set;

/**
 * @author <a href="mailto:Andrew.Jackson@bl.uk">Andy Jackson</a>
 * @author <a href="mailto:fabian.steeg@uni-koeln.de">Fabian Steeg</a>
 */
final class FormatUtils {
	/** Util classes providing static methods should not be instantiated. */
	private FormatUtils() {/* Enforce non-instantiability */
	}

	/** The string representing "any format". */
	static final String ANY_FORMAT = "planets:fmt/any";
	/** The string representing a "folder". */
	static final String FOLDER_TYPE = "planets:fmt/folder";
	/** The string representing "unknown format". */
	static final String UNKNOWN_FORMAT = "planets:fmt/unknown";
	/** The prefix for MIME format URIs. */
	static final String MIME_URI_PREFIX = "planets:fmt/mime/";
	/** The prefix for extension format URIs. */
	static final String EXT_URI_PREFIX = "planets:fmt/ext/";
	/** The prefix for modification Action-URIs. */
	static final String ACTION_URI_PREFIX = "planets:mod/";
	/** The prefix for PRONOM format URIs. */
	static final String PRONOM_URI_PREFIX = "info:pronom/";

	/**
	 * @param uri
	 *          The pronom URI
	 * @return The first corresponding extension (e.g. 'txt')
	 */
	static String getFirstExtension(final URI uri) {
		if (uri == null) {
			return null;
		}
		FormatRegistry fr = FormatRegistryFactory.getFormatRegistry();
		Format frURI = fr.getFormatForUri(uri);
		Set<String> set = frURI.getExtensions();

		if (set != null) {
			return set.iterator().next();
		}
		return null;
	}

	/**
	 * Static helper to construct appropriate URIs for file-extensions format
	 * specifiers.
	 * 
	 * @param ext
	 *          The extension (e.g. 'txt')
	 * @return the extension as a format URI
	 */
	static URI createExtensionUri(final String ext) {
		return URI.create(EXT_URI_PREFIX + ext.toLowerCase());
	}

	/**
	 * Static helper to construct appropriate URIs for mime-type format
	 * specifiers.
	 * 
	 * @param mime
	 *          The mime type (e.g. 'image/tiff')
	 * @return the MIME type as a format URI
	 */
	static URI createMimeUri(final String mime) {
		return URI.create(MIME_URI_PREFIX + mime.toLowerCase());
	}

	/**
	 * Static helper to construct appropriate URIs for Planets modification
	 * actions. (e.g. for a Modify-rotate service: "planets:mod/rotate")
	 * 
	 * @param action
	 *          the action the service can perform
	 * @return the Action-URI
	 */
	static URI createActionUri(final String action) {
		return URI.create(ACTION_URI_PREFIX + action.toLowerCase());
	}

	/**
	 * Static helper to convert a short-form PRONOM ID into a PRONOM URI.
	 * 
	 * @param pronomID
	 *          Short-form PUID, e.g. 'fmt/95'
	 * @return A full PRONOM Format URI, e.g. 'info:pronom/fmt/95'
	 */
	static URI createPronomUri(final String pronomID) {
		return URI.create(PRONOM_URI_PREFIX + pronomID.toLowerCase());
	}

	/**
	 * @param uri
	 *          The URI to check
	 * @return true if a MIME URI
	 */
	static boolean isMimeUri(final URI uri) {
		return uri.toString().startsWith(MIME_URI_PREFIX);
	}

	/**
	 * @param uri
	 *          The URI to check
	 * @return true if an extension URI
	 */
	static boolean isExtensionUri(final URI uri) {
		return uri.toString().startsWith(EXT_URI_PREFIX);
	}

	/**
	 * @param uri
	 *          The URI to check
	 * @return true if a PRONOM URI
	 */
	static boolean isPronomUri(final URI uri) {
		return uri.toString().startsWith(PRONOM_URI_PREFIX);
	}
}
