import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { Expense } from 'src/app/shared/interfaces/expense';
import { ExpenseService } from 'src/app/shared/services/expense.service';

import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { UserService } from 'src/app/shared/services/user.service';


@Component({
  selector: 'app-read-example',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    MatSnackBarModule
  ],
  templateUrl: './read-example.component.html',
  styleUrl: './read-example.component.css'
})
export class ReadExampleComponent implements OnInit {
  expenseService = inject(ExpenseService);  
  snackBar = inject(MatSnackBar);
  userService = inject(UserService)

  expenses: Expense[] = [];
  pageSize = 5;
  currentPage = 1;  

  ngOnInit() {
    this.readExpenses();
  }

  readExpenses() {
    this.expenseService.readAllExpenses().subscribe({
      next: (expenses) => {
        console.log('API response:', expenses);
        this.expenses = expenses;
        console.log('All expenses:', expenses);
      },
      error: (err) => {
        console.error('Error fetching expenses data', err);
      }
    });
  }

  changePage(page: number) {
    this.currentPage = page;
    this.readExpenses();
  }

  getDisplayedExpenses() {
    const sortedExpenses = [...this.expenses].sort((a, b) => {
      return new Date(b.createdAt).getTime() - new Date(a.createdAt).getTime();
    });

    const startIndex = (this.currentPage - 1) * this.pageSize;
    const endIndex = startIndex + this.pageSize;
    return sortedExpenses.slice(startIndex, endIndex);  
  }

  get totalPages()  {
    return Math.ceil(this.expenses.length / this.pageSize);
  }

  get pages() {
    const pageCount = this.totalPages;
    return Array.from({ length: pageCount }, (_, index) => index + 1);
  }  

  deleteExpenses(expense:Expense) {
    this.expenseService.deleteExpense(expense.id).subscribe({
      next: () => {
        this.readExpenses();
        this.snackBar.open('Expense Deleted', 'Close', {
          duration: 3000,
          panelClass: ['custom-snackbar'],          
        });
        console.log('Expense deleteded successfully', expense.expenseType, expense.description, expense.amount);        
      },
      error: (error) => {
        this.snackBar.open('There is an error', 'Close', {
          duration: 3000,
          panelClass: ['custom-snackbar']
        });
        console.error('Error deleting expense', error);
      }
    });
  }

  logout() {
    this.userService.logoutUser();
  }
}
