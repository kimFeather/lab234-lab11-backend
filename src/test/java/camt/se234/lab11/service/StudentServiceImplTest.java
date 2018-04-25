package camt.se234.lab11.service;

import camt.se234.lab11.dao.StudentDao;
import camt.se234.lab11.dao.StudentDaoImpl;
import camt.se234.lab11.entity.Student;
import camt.se234.lab11.exception.NoDataException;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {
    StudentDao studentDao;
    StudentServiceImpl studentService;

    @Before
    public void setup() {
        studentDao = mock(StudentDao.class);
        studentService = new StudentServiceImpl();
        studentService.setStudentDao(studentDao);
    }

    @Test
    public void testFindById() {
        StudentDaoImpl studentDao = new StudentDaoImpl();
        StudentServiceImpl studentService = new StudentServiceImpl();
        studentService.setStudentDao(studentDao);
        assertThat(studentService.findStudentById("123"), is(new Student("123", "A", "temp", 2.33)));
        assertThat(studentService.findStudentById("124"), is(new Student("124", "B", "temp2", 4.00)));
        assertThat(studentService.findStudentById("125"), is(new Student("125", "C", "temp3", 3.00)));
        assertThat(studentService.findStudentById("126"), is(new Student("126", "D", "temp4", 3.50)));
    }

    @Test
    public void testGetAverageGpa() {
        StudentDaoImpl studentDao = new StudentDaoImpl();
        StudentServiceImpl studentService = new StudentServiceImpl();
        studentService.setStudentDao(studentDao);
        assertThat(studentService.getAverageGpa(), is(closeTo(3.21, 0.003)));
    }

    @Test
    public void testWithMock() {
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student("123", "A", "temp", 2.33));
        mockStudents.add(new Student("124", "B", "temp", 2.33));
        mockStudents.add(new Student("223", "C", "temp", 2.33));
        mockStudents.add(new Student("224", "D", "temp", 2.33));
        when(studentDao.findAll()).thenReturn(mockStudents);
        assertThat(studentService.findStudentById("123"),
                is(new Student("123", "A", "temp", 2.33)));
        assertThat(studentService.findStudentById("124"),
                is(new Student("124", "B", "temp", 2.33)));
        assertThat(studentService.findStudentById("223"),
                is(new Student("223", "C", "temp", 2.33)));
        assertThat(studentService.findStudentById("224"),
                is(new Student("224", "D", "temp", 2.33)));
    }

    @Test
    public void testGetAverageGpaWithMock() {
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student("123", "A", "temp", 2.33));
        mockStudents.add(new Student("124", "B", "temp", 2.33));
        mockStudents.add(new Student("223", "C", "temp", 2.33));
        mockStudents.add(new Student("224", "D", "temp", 2.33));
        when(studentDao.findAll()).thenReturn(mockStudents);
        assertThat(studentService.getAverageGpa(), is(closeTo(2.33, 0.01)));
        mockStudents.clear();
        mockStudents.add(new Student("234", "A", "temp", 2.33));
        mockStudents.add(new Student("235", "B", "temp2", 4.00));
        mockStudents.add(new Student("236", "C", "temp3", 3.00));
        mockStudents.add(new Student("237", "D", "temp4", 3.50));
        assertThat(studentService.getAverageGpa(), is(closeTo(3.21, 0.003)));
        mockStudents.clear();
        mockStudents.add(new Student("321", "A", "temp", 2.5));
        mockStudents.add(new Student("322", "B", "temp2", 4.00));
        mockStudents.add(new Student("323", "C", "temp3", 3.00));
        mockStudents.add(new Student("324", "D", "temp4", 3.50));
        assertThat(studentService.getAverageGpa(), is(closeTo(3.25, 0.003)));
    }

    @Test
    public void testFindByPartOfId() {
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student("123", "A", "temp", 2.33));
        mockStudents.add(new Student("124", "B", "temp", 2.33));
        mockStudents.add(new Student("223", "C", "temp", 2.33));
        mockStudents.add(new Student("224", "D", "temp", 2.33));
        when(studentDao.findAll()).thenReturn(mockStudents);
        assertThat(studentService.findStudentByPartOfId("22"),
                hasItem(new Student("223", "C", "temp", 2.33)));
        assertThat(studentService.findStudentByPartOfId("22"),
                hasItems(new Student("223", "C", "temp", 2.33),
                        new Student("224", "D", "temp", 2.33)));
        assertThat(studentService.findStudentByPartOfId("12"),
                hasItems(new Student("123", "A", "temp", 2.33),
                        new Student("124", "B", "temp", 2.33)));
    }

    @Test(expected = NoDataException.class)
    public void testNoDataException(){
        List<Student> mockStudents = new ArrayList<>();
        mockStudents.add(new Student("123", "A", "temp", 2.33));

        when(studentDao.findAll()).thenReturn(mockStudents);
        assertThat(studentService.findStudentById("55"), nullValue());
        assertThat(studentService.findStudentByPartOfId("55"), hasItem(nullValue()));
    }
    @Test(expected = ArithmeticException.class)
    public void testArithmeticException(){
        List<Student> mockStudents = new ArrayList<>();
        when(studentDao.findAll()).thenReturn(mockStudents);
        assertThat(studentService.getAverageGpa(), nullValue());
    }
}

