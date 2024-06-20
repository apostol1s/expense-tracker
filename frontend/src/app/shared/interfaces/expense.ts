export interface Expense {
    id: string;
    expenseType: string;
    description: string;
    amount: number;
    createdAt: Date;
    updatedAt: Date;    
  }