/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package jsonandxml;

import java.io.*;
import java.time.LocalDateTime;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

public class App {

	public static void main(String[] args) throws JAXBException, FileNotFoundException, ParseException {
		Person firstPerson = new Person("Vasia", 14, LocalDateTime.now());

		// XML

		// Write xml
		String xml = "";
		Writer fos = new StringWriter();

		JAXBContext context = org.eclipse.persistence.jaxb.JAXBContextFactory
				.createContext(new Class[] { Person.class }, null);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		marshaller.marshal(firstPerson, fos);

		xml = fos.toString();
		System.out.println(xml);

		// Read xml
		Reader fis = new StringReader(xml);

		Unmarshaller unmarshaller = context.createUnmarshaller();
		Person secondPerson = (Person) unmarshaller.unmarshal(fis);

		System.out.println("Person name=" + secondPerson.getName() + ", age=" + secondPerson.getAge() + ", birthDate="
				+ secondPerson.getBirthDate());

		// JSON
		
		// Write JSON
		String JSON = "";
		
		JSONObject person = new JSONObject();
		person.put("name", firstPerson.getName());
		person.put("age",firstPerson.getAge());
		person.put("birthDate",firstPerson.getBirthDate().toString());
		
		JSON = person.toJSONString();
		System.out.println(JSON);
		
		// Read JSON
		JSONParser jsonParser = new JSONParser();
		JSONObject JSONPerson = (JSONObject) jsonParser.parse(JSON);
		Person newPerson = new Person();
		newPerson.setName((String) JSONPerson.get("name"));
		newPerson.setAge((Integer.parseInt(JSONPerson.get("age").toString())));
		newPerson.setBirthDate(LocalDateTime.parse(JSONPerson.get("birthDate").toString()));
		
		System.out.println("Person name=" + newPerson.getName() + ", age=" + newPerson.getAge() + ", birthDate="
				+ newPerson.getBirthDate());
	}
}
