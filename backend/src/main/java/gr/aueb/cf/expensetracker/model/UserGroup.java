package gr.aueb.cf.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonMerge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_group")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGroup {
    @Id
    @UuidGenerator
    private UUID id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<User> users = new ArrayList<>();
    @OneToMany(mappedBy = "userGroup", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<AccessPermission> accessPermissions = new ArrayList<>();

    public UserGroup(Role role) {
        this.role = role;
    }

    public void addUser(User user) {
        users.add(user);
        user.setUserGroup(this);
    }

    public void addAccessPermission(AccessPermission accessPermission) {
        accessPermissions.add(accessPermission);
        accessPermission.setUserGroup(this);
    }
}
