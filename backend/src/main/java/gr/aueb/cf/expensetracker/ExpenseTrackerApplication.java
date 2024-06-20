package gr.aueb.cf.expensetracker;

import gr.aueb.cf.expensetracker.authentication.SecurityConfig;
import gr.aueb.cf.expensetracker.model.*;
import gr.aueb.cf.expensetracker.model.Module;
import gr.aueb.cf.expensetracker.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@SpringBootApplication
@EnableJpaAuditing
public class ExpenseTrackerApplication {

    private final PasswordEncoder passwordEncoder;

    public ExpenseTrackerApplication(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ExpenseTrackerApplication.class, args);
    }

    /**
     * Configures initial setup for modules, actions, and user groups upon application startup.
     *  *
     *
     * @param moduleRepository      repository for managing modules.
     * @param userGroupRepository   repository for managing user groups.
     * @return CommandLineRunner implementation that initializes
     *          predefined modules, actions, and user groups.
     */
    @Bean
    CommandLineRunner commandLineRunner(ModuleRepository moduleRepository,
                                        UserGroupRepository userGroupRepository) {
        return args -> {
            // Define and save 'Users' module with actions
            Module moduleUsers = new Module("Users");
            ModuleAction getAllUsers = new ModuleAction("GetAllUsers");
            ModuleAction deleteUserById = new ModuleAction("DeleteUserById");

            moduleUsers.addModuleActions(getAllUsers);
            moduleUsers.addModuleActions(deleteUserById);

            // Create 'Admin' user group and add an admin user
            UserGroup admin = new UserGroup(Role.Admin);
            admin.addUser(new User("Admin", "Adminakos", "admin@admin.com", passwordEncoder.encode("Admin123$"), false, true));

            // Assign access permissions to 'Admin' user group for module actions
            admin.addAccessPermission(new AccessPermission(admin, deleteUserById));
            admin.addAccessPermission(new AccessPermission(admin, getAllUsers));

            // Save 'Users' module and 'Admin' user group
            moduleRepository.save(moduleUsers);
            userGroupRepository.save(admin);

            // Define and save 'Expenses' module with actions
            Module moduleExpenses = new Module("Expenses");
            ModuleAction getAllExpenses = new ModuleAction("GetAllExpenses");
            ModuleAction deleteExpenseById = new ModuleAction("DeleteExpenseById");
            ModuleAction updateExpenseById = new ModuleAction("UpdateExpenseById");
            ModuleAction createExpenseById = new ModuleAction("CreateExpense");

            moduleExpenses.addModuleActions(getAllExpenses);
            moduleExpenses.addModuleActions(deleteExpenseById);
            moduleExpenses.addModuleActions(updateExpenseById);
            moduleExpenses.addModuleActions(createExpenseById);

            // Create 'User' user group and assign access permissions for 'Expenses' module actions
            UserGroup user = new UserGroup(Role.User);

            user.addAccessPermission(new AccessPermission(user, getAllExpenses));
            user.addAccessPermission(new AccessPermission(user, deleteExpenseById));
            user.addAccessPermission(new AccessPermission(user, updateExpenseById));
            user.addAccessPermission(new AccessPermission(user, createExpenseById));

            // Save 'Expenses' module and 'User' user group
            moduleRepository.save(moduleExpenses);
            userGroupRepository.save(user);
        };
    }
}
