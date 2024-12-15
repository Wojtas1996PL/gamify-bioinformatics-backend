package project.bioinformatics.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE exercises SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
@NoArgsConstructor
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String topic;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private int steps;
    @Column(nullable = false)
    private int time;
    @Column(nullable = false, name = "is_deleted")
    private boolean isDeleted = false;
}
