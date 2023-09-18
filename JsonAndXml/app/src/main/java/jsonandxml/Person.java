package jsonandxml;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "person")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person { 
	@XmlElement()
	private String name;
	@XmlElement()
	private int age;
	@XmlElement()
	private LocalDateTime birthDate;
	
	public Person() {
	}
	
	public Person(String name, int age, LocalDateTime birthDate) {
		this.name = name;
		this.age = age;
		this.birthDate = birthDate;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDateTime getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(LocalDateTime birthDate) {
		this.birthDate = birthDate;
	}
}
