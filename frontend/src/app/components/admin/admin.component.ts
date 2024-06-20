import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { User } from 'src/app/shared/interfaces/user';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent {
  // expenseService = inject(ExpenseService);  
  snackBar = inject(MatSnackBar);
  userService = inject(UserService)

  users: User[] = [];
  pageSize = 5;
  currentPage = 1;  

  ngOnInit() {
    this.readUsers();
  }

  readUsers() {
    this.userService.readAllUsers().subscribe({
      next: (users) => {
        // console.log('API response:', users);
        this.users = users;
        // console.log('All expenses:', users);
      },
      error: (err) => {
        console.error('Error fetching users data', err);
      }
    });
  }

  changePage(page: number): void {
    this.currentPage = page;
    this.readUsers();
  }

  getDisplayedUsers(): User[] {
    const sortedUsers = [...this.users].sort((a, b) => {
      return a.lastname.localeCompare(b.lastname);
    });

    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    return sortedUsers.slice(startIndex, endIndex);  
  }

  get totalPages(): number {
    return Math.ceil(this.users.length / this.pageSize);
  }

  get pages(): number[] {
    const pageCount = this.totalPages;
    return Array.from({ length: pageCount }, (_, index) => index + 1);
  }  

  deleteUsers(user:User) {
    this.userService.deleteUser(user.id).subscribe({
      next: () => {
        this.readUsers();
        this.snackBar.open('User Deleted', 'Close', {duration: 3000});
        console.log('User deleteded successfully', user.firstname, user.lastname, user.email);        
      },
      error: (error) => {
        this.snackBar.open('There is an error', 'Close', {duration: 3000});
        console.error('Error deleting user', error);
      }
    });
  }

  logout() {
    this.userService.logoutUser();
  }
}
