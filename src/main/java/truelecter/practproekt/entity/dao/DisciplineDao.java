package truelecter.practproekt.entity.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import truelecter.practproekt.entity.Discipline;

@Transactional
public interface DisciplineDao  extends CrudRepository<Discipline, Integer> {}
