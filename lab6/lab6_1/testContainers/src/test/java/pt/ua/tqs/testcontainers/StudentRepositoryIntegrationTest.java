package pt.ua.tqs.testcontainers;

import pt.ua.tqs.testcontainers.Student;
import pt.ua.tqs.testcontainers.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestContainersApplication.class)
@Testcontainers
@TestMethodOrder(OrderAnnotation.class)
public class StudentRepositoryIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine");

    @Autowired
    private StudentRepository studentRepository;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
    }

    @BeforeEach
    void cleanup() {
        studentRepository.deleteAll();
    }

    @Test
    @Order(1)
    void testInsertStudent() {
        // Given
        Student student = new Student("João Silva", 22, "joao@example.com");

        // When
        Student savedStudent = studentRepository.save(student);

        // Then
        assertNotNull(savedStudent.getId());
        assertEquals("João Silva", savedStudent.getName());
    }

    @Test
    @Order(2)
    void testRetrieveStudent() {
        // Given
        Student student = new Student("Maria Santos", 20, "maria@example.com");
        studentRepository.save(student);

        // When
        List<Student> students = studentRepository.findByName("Maria Santos");

        // Then
        assertEquals(1, students.size());
        assertEquals("Maria Santos", students.get(0).getName());
    }

    @Test
    @Order(3)
    void testUpdateAndRetrieveStudent() {
        // Given
        Student student = new Student("Carlos Oliveira", 25, "carlos@example.com");
        student = studentRepository.save(student);

        // When
        student.setAge(26);
        studentRepository.save(student);

        // Then
        Optional<Student> updated = studentRepository.findById(student.getId());
        assertTrue(updated.isPresent());
        assertEquals(26, updated.get().getAge());
    }

    @Test
    @Order(4)
    void testFindByAgeGreaterThan() {
        // Given
        studentRepository.saveAll(List.of(
                new Student("Student1", 18, "s1@example.com"),
                new Student("Student2", 20, "s2@example.com"),
                new Student("Student3", 22, "s3@example.com")
        ));

        // When
        List<Student> olderStudents = studentRepository.findByAgeGreaterThan(19);

        // Then
        assertEquals(2, olderStudents.size());
    }
}