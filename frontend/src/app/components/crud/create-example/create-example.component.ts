import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Expense } from 'src/app/shared/interfaces/expense';
import { ExpenseService } from 'src/app/shared/services/expense.service';

@Component({
  selector: 'app-create-example',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    CommonModule
  ],
  templateUrl: './create-example.component.html',
  styleUrl: './create-example.component.css'
})
export class CreateExampleComponent {
  expenseService = inject(ExpenseService);
  router = inject(Router);
  snackBar = inject(MatSnackBar);

  expenseTypes: string[] = ['Electricity', 'Water', 'Delivery', 'Rent', 'Super Market'];

  form = new FormGroup({
    expenseType: new FormControl('', Validators.required),
    description: new FormControl(''),
    amount: new FormControl('', Validators.required)
  });

  createExpenses(value: any) {
    // console.log(value);    
    const expense = {
      expenseType: this.form.value.expenseType,
      description: this.form.value.description,
      amount: parseFloat(this.form.value.amount),
      createdAt: new Date
    } as Expense;

    this.expenseService.createExpense(expense).subscribe({
      next: () => {
        this.form.reset();
        this.snackBar.open('Expense Created', 'Close', {duration: 3000});
        console.log('Expense created', expense.expenseType, expense.description, expense.amount);
      },
      error: (error) => {
        this.snackBar.open('There is an error', 'Close', {duration: 3000});
        console.error('There was an error!', error);
      },
    });
  }

  onClose() {
    this.form.reset();
    this.router.navigate(['crud-example-read']);
  }
}
