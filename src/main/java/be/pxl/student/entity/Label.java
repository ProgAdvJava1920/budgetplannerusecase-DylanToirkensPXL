package be.pxl.student.entity;

import javax.persistence.*;

@Entity
public class Label {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Lob
    private String description;

    public Label() {
        // JPA only
    }
}
