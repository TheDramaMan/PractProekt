package truelecter.practproekt.service;

import java.util.List;

import truelecter.practproekt.entity.Discipline;

public interface IDisciplineService {
    public List<Discipline> getAllDisciplines();
    public Discipline getDisciplineById(int id);
    public Discipline saveDiscipline(Discipline d);
}
