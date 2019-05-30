package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Employee;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Enter full file path: ");
		String path = sc.nextLine();
		System.out.print("Enter salary: ");
		double salary = sc.nextDouble();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String line = br.readLine();
			
			List<Employee> list = new ArrayList();
			
			while (line != null) {
				String[] field = line.split(",");
				list.add(new Employee(field[0], field[1], Double.parseDouble(field[2])));
				line = br.readLine();
			}
			
			System.out.println();
			System.out.println("Email of people whose salary is more than " + salary + ":");
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> emailList = list.stream()
					.filter(emp -> emp.getSalary() > salary)
					.map(emp -> emp.getEmail()).sorted(comp)
					.collect(Collectors.toList());
			
			System.out.println();
			emailList.forEach(System.out::println);
			
			System.out.println();
			
			System.out.print("Enter the letter (upper case): ");
			char letter = sc.next().charAt(0);
			
			System.out.println();
			double sum = list.stream()
					.filter(emp -> emp.getName().charAt(0) == letter)
					.map(emp -> emp.getSalary()).reduce(0.0, (x, y) -> x+y);
			
			System.out.print("Sum of salary of people whose name starts with " + letter + ": " + String.format("%.2f", sum));
		}
		catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		catch (RuntimeException e) {
			System.out.println("Error" + e.getMessage());
		}
		finally {
			if (sc != null) {
				sc.close();
			}
		}
	}

}
