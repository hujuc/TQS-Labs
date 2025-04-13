package pt.ua.tqs.testcontainers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
public class StudentRepositoryFlywayTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private StudentRepository studentRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testCanFindStudentsByName() {
        // When
        List<Student> students = studentRepository.findByName("Ana Silva");

        // Then
        assertEquals(1, students.size());
        assertEquals("Ana Silva", students.get(0).getName());
        assertEquals(21, students.get(0).getAge());
    }

    @Test
    void testCanFindStudentsOlderThan() {
        // When
        List<Student> olderStudents = studentRepository.findByAgeGreaterThan(23);

        // Then
        assertEquals(2, olderStudents.size());
        assertTrue(olderStudents.stream().anyMatch(s -> s.getName().equals("Carlos Santos")));
        assertTrue(olderStudents.stream().anyMatch(s -> s.getName().equals("Sofia Martins")));
    }

    @Test
    void testCanUpdateStudent() {
        // Given
        Student student = studentRepository.findByName("Mariana Oliveira").get(0);

        // When
        student.setAge(20);
        studentRepository.save(student);

        // Then
        Student updatedStudent = studentRepository.findByName("Mariana Oliveira").get(0);
        assertEquals(20, updatedStudent.getAge());
    }
}