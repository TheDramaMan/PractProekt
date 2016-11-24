package truelecter.practproekt.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.*;
import truelecter.practproekt.entity.serializer.AnnotationSerializer;
import truelecter.practproekt.entity.serializer.NestedStudentSetSerializer;
import truelecter.practproekt.entity.serializer.NestedTeacherSerializer;

//As bean
@NoArgsConstructor
// For creating
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "disciplines")
public class Discipline {

    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "DISCIPLINE_ID")
    private int id;

    @NonNull
    @NotNull
    private String name;

    @NonNull
    @NotNull
    private int credits;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonSerialize(using = NestedTeacherSerializer.class)
    private Teacher teacher;

    @JsonSerialize(using = AnnotationSerializer.class, nullsUsing = AnnotationSerializer.class)
    private String annotation;

    private boolean recommended;

    // @ManyToMany(cascade = CascadeType.ALL)
    // @JoinTable(name = "discipline_students", joinColumns = @JoinColumn(name =
    // "DISCIPLINE_ID"), inverseJoinColumns = @JoinColumn(name = "STUDENT_ID"))
    @ManyToMany(mappedBy = "disciplines", fetch = FetchType.LAZY)
    @JsonSerialize(using = NestedStudentSetSerializer.class)
    private Set<Student> students = new HashSet<>();

}
