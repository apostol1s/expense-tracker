package gr.aueb.cf.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "access_permission")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessPermission {
    @Id
    @UuidGenerator
    private UUID id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_group_id", foreignKey = @ForeignKey(name = "access_group_fk"))
    private UserGroup userGroup;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "module_action_id", foreignKey = @ForeignKey(name = "access_permission_module_action_id"))
    private ModuleAction moduleAction;

    public AccessPermission(UserGroup userGroup, ModuleAction moduleAction) {
        this.userGroup = userGroup;
        this.moduleAction = moduleAction;
    }
}
