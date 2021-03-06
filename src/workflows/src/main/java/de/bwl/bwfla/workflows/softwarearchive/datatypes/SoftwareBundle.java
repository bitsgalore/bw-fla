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

package de.bwl.bwfla.workflows.softwarearchive.datatypes;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.transform.stream.StreamSource;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "softwareBundle", namespace="miep")
public class SoftwareBundle {
	@XmlElement(name="file", namespace="miep")
	public List<BundledFile> files = new ArrayList<BundledFile>();
	
	public SoftwareBundle() {
		
	}

    public static SoftwareBundle fromValue(String data) throws JAXBException {
    	JAXBContext jc = JAXBContext.newInstance(SoftwareBundle.class);
    	Unmarshaller unmarshaller = jc.createUnmarshaller();
    	return (SoftwareBundle)unmarshaller.unmarshal(new StreamSource(new StringReader(data)));
    }
    
    public String value() throws JAXBException {
    	JAXBContext jc = JAXBContext.newInstance(this.getClass());
    	Marshaller marshaller = jc.createMarshaller();
    	StringWriter w = new StringWriter();
    	marshaller.marshal(this, w);
    	return w.toString();
    }
}
