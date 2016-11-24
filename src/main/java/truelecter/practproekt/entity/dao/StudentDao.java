package truelecter.practproekt.entity.dao;

import org.springframework.data.repository.CrudRepository;

import truelecter.practproekt.entity.Student;

public interface StudentDao extends CrudRepository<Student, String> {

}
