package serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serialization {

	public static void main(String[] args) {
		// Serialization
		Person serPerson = new Person("Petr", 14, true);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream("person.dat");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(serPerson);
			System.out.println("Object to write: " + serPerson.toString());
			// Change instance
			serPerson.setAge(15);
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Reading a serialized object from a file
		Person readedPerson;
		FileInputStream fis;
		try {
			fis = new FileInputStream("person.dat");
			ObjectInputStream ois = new ObjectInputStream(fis);
			readedPerson = (Person) ois.readObject();
			System.out.println("Result object: " + readedPerson.toString());
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	// Data model
	public static class Person implements Serializable {
		private String name;
		private int age;
		private boolean isMale;

		public Person(String name, int age, boolean isMale) {
			this.name = name;
			this.age = age;
			this.isMale = isMale;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isMale() {
			return isMale;
		}

		public void setMale(boolean isMale) {
			this.isMale = isMale;
		}

		public String toString() {
			String res = "";

			if (this.name == null)
				res += "Person: name=_";
			else
				res += "Person: name=" + this.name;

			res += " , age=" + age;
			res += " , isMale=" + isMale;
			
			return res;
		}

	}
}
