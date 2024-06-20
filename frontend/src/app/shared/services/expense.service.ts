import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Expense } from '../interfaces/expense';

const API_URL = `${environment.apiURL}/expense`;

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  http: HttpClient = inject(HttpClient);

  readAllExpenses() {
    return this.http.get<Expense[]>(`${API_URL}/overview`);
  }

  createExpense(expense: Expense) {        
    return this.http.post(`${API_URL}/create`, expense);
  }

  updateExpense(expenseId: string, expense:Expense) {
    return this.http.put<Expense>(`${API_URL}/update/${expenseId}`, expense);
  }

  deleteExpense(expenseId: string) {
    return this.http.delete(`${API_URL}/delete/${expenseId}`);
  }

}
