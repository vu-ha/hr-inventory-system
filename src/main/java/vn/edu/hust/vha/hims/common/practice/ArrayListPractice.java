package vn.edu.hust.vha.hims.common.practice;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hust.vha.hims.common.entity.Employee;

public class ArrayListPractice {
	public static void main(String[] args) {
		Employee emp1 = Employee.builder()
                .firstName("Nguyen")
                .lastName("Van A")
                .email("vana@hust.edu.vn")
                .phoneNumber("0912345678")
                .build();
		Employee emp2 = Employee.builder()
                .firstName("Vu")
                .lastName("Van B")
                .email("vanb@hust.edu.vn")
                .phoneNumber("1234567890")
                .build();
		Employee emp3 = Employee.builder()
                .firstName("Dao")
                .lastName("Van C")
                .email("vanc@hust.edu.vn")
                .phoneNumber("1234567890")
                .build();
		List<Employee> employees = new ArrayList<>();
		employees.add(emp1);
		employees.add(emp2);
		employees.add(emp3);
		
		for(Employee e : employees) {
			if(e.getFirstName().equals("Dao"))
				System.out.println("1");
		}
		for(Employee e : employees) {
			if(e.getPhoneNumber().equals("1234567890"))
				System.out.println("tt :" + e.toString());
		}
		
		employees.set(0, emp1);
		System.out.println("ten :" + employees.get(0).getFirstName());

		System.out.println("ten :" + employees.get(1).getFirstName());
		System.out.println("size :" + employees.size());
		
	}
}
