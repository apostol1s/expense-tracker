import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ReadExampleComponent } from './components/crud/read-example/read-example.component';
import { authGuard } from './shared/guards/auth.guard';
import { CreateExampleComponent } from './components/crud/create-example/create-example.component';
import { UpdateExampleComponent } from './components/crud/update-example/update-example.component';
import { AdminComponent } from './components/admin/admin.component';

export const routes: Routes = [
    {
        path: 'login',
        component: LoginComponent,
    },
    {
        path: 'registration',
        component: RegistrationComponent,
    },
    {
        path: '',
        redirectTo: '/login', pathMatch: 'full'
    },
    {
      path: 'crud-example-read',
      component: ReadExampleComponent,
      canActivate: [authGuard],
      data: { role: 'User' }
    },
    {
        path: 'crud-example-create',
        component: CreateExampleComponent,
        canActivate: [authGuard],
        data: { role: 'User' }
    },
    {
        path: 'crud-example-update/:id',
        component: UpdateExampleComponent,
        canActivate: [authGuard],
        data: { role: 'User' }
    },  
    {
        path: 'admin',
        component: AdminComponent,
        canActivate: [authGuard],
        data: { role: 'Admin' }
    },   
];
