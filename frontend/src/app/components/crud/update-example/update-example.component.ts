import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, Router } from '@angular/router';
import { Expense } from 'src/app/shared/interfaces/expense';
import { ExpenseService } from 'src/app/shared/services/expense.service';

@Component({
  selector: 'app-update-example',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule],
  templateUrl: './update-example.component.html',
  styleUrl: './update-example.component.css'
})
export class UpdateExampleComponent implements OnInit {
  expenseService = inject(ExpenseService);
  router = inject(Router);
  snackBar = inject(MatSnackBar);
  route = inject(ActivatedRoute);

  expenseId: string;
  expenseTypes: string[] = ['Electricity', 'Water', 'Delivery', 'Rent', 'Super Market'];  

  ngOnInit() {
    this.expenseId = this.route.snapshot.params['id'];
    // console.log('Expense ID:', this.expenseId);
  }
  
  form = new FormGroup({
    expenseType: new FormControl('', Validators.required),
    description: new FormControl(''),
    amount: new FormControl('', Validators.required)
  });  

  updateExpenses() {    
    const expense = {
      expenseType: this.form.value.expenseType,
      description: this.form.value.description,
      amount: parseFloat(this.form.value.amount),
      updatedAt: new Date
    } as Expense;
    this.expenseService.updateExpense(this.expenseId, expense).subscribe({
      next: () => {
        this.snackBar.open('Expense Updated', 'Close', {duration: 3000});
        console.log('Expense updated successfully', expense.expenseType, expense.description, expense.amount);
        this.form.reset();
      },
      error: (error) => {
        this.snackBar.open('There is an error', 'Close', {duration: 3000});
        console.error('Error updating expense', error);
      }
    });
  }

  onClose() {
    this.form.reset();
    this.router.navigate(['crud-example-read']);
  }        
}
