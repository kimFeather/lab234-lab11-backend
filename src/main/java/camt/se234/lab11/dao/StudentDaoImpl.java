package camt.se234.lab11.dao;

import camt.se234.lab11.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    List<Student> students;
    public StudentDaoImpl(){
        students = new ArrayList<>();
        students.add(new Student("123","A","temp",2.33));
        students.add(new Student("124","B","temp2",4.00));
        students.add(new Student("125","C","temp3",3.00));
        students.add(new Student("126","D","temp4",3.50));

    }

    @Override
    public List<Student> findAll() {
        return this.students;
    }
}
