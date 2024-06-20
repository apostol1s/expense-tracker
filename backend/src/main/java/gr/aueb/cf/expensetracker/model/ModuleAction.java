package gr.aueb.cf.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "module_action")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleAction {
    @Id
    @UuidGenerator
    private UUID id;
    private String action;
    @ManyToOne
    @JoinColumn(name = "module_id", foreignKey = @ForeignKey(name = "module_action_fk"))
    private Module module;
    @JsonIgnore
    @OneToMany(mappedBy = "moduleAction", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AccessPermission> accessPermissions;

    public ModuleAction(String action) {
        this.action = action;
    }
}
