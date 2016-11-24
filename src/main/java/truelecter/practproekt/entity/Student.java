package truelecter.practproekt.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import truelecter.practproekt.entity.serializer.StudentSerializer;

//As bean
@NoArgsConstructor
// For creating
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "students")
@JsonSerialize(using = StudentSerializer.class)
public class Student {
    @NonNull
    @NotNull
    @Id
    // Should be same as username
    @Column(name = "STUDENT_ID")
    private String id;

    @NonNull
    private String name;

    // @JsonBackReference
    // @ManyToMany(mappedBy = "students")
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "students_disciplines", inverseJoinColumns = @JoinColumn(name = "DISCIPLINE_ID"), joinColumns = @JoinColumn(name = "STUDENT_ID"))
    // @JsonSerialize(using = NestedDisciplineSetSerializer.class)
    private Set<Discipline> disciplines = new HashSet<>();

    public int getTotalCredits() {
        int r = 0;
        for (Discipline d : disciplines) {
            r += d.getCredits();
        }
        return r;
    }
}
