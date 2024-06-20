package gr.aueb.cf.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "module")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Module {
    @Id
    @UuidGenerator
    private UUID id;
    private String name;
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<ModuleAction> moduleActions = new ArrayList<>();

    public Module(String name) {
        this.name = name;
    }


    public void addModuleActions(ModuleAction moduleAction) {
        moduleActions.add(moduleAction);
        moduleAction.setModule(this);
    }
}
