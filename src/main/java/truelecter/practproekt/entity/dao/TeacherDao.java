package truelecter.practproekt.entity.dao;

import org.springframework.data.repository.CrudRepository;

import truelecter.practproekt.entity.Teacher;

public interface TeacherDao extends CrudRepository<Teacher, Integer> {
}
