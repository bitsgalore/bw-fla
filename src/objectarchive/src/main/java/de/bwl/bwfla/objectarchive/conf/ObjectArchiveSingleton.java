/*
 * This file is part of the Emulation-as-a-Service framework.
 *
 * The Emulation-as-a-Service framework is free software: you can
 * redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * The Emulation-as-a-Service framework is distributed in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PARTICULAR PURPOSE.  See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with the Emulation-as-a-Software framework.
 * If not, see <http://www.gnu.org/licenses/>.
 */

package de.bwl.bwfla.objectarchive.conf;

import java.util.logging.Logger;
import de.bwl.bwfla.common.utils.config.ConfigurationManager;



public class ObjectArchiveSingleton
{
	protected static final Logger				LOG	= Logger.getLogger(ObjectArchiveSingleton.class.getName());
	public static volatile boolean 				confValid = false;
	public static volatile ObjectArchiveConf	CONF;

	static
	{
		ObjectArchiveSingleton.loadConf();
	}

	public static boolean validate(ObjectArchiveConf conf)
	{
		// TODO: here perform validation
		return true;
	}

	synchronized public static void loadConf()
	{ 
		CONF = ConfigurationManager.load(ObjectArchiveConf.class);
		confValid = ObjectArchiveSingleton.validate(CONF); 
	}
}