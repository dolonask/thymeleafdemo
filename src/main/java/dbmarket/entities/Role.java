package dbmarket.entities;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name =  "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int id;
    @Column(name = "role_name")
    private String name;
    private boolean is_active;
}
