import { Component } from '@angular/core';
import { LoginComponent } from './components/login/login.component';
import { RouterLink, RouterOutlet } from '@angular/router';
import { RegistrationComponent } from './components/registration/registration.component';
import { ReadExampleComponent } from './components/crud/read-example/read-example.component';
import { CreateExampleComponent } from './components/crud/create-example/create-example.component';
import { UpdateExampleComponent } from './components/crud/update-example/update-example.component';
import { AdminComponent } from './components/admin/admin.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [ 
    RouterLink,
    RouterOutlet,
    LoginComponent,
    RegistrationComponent,
    ReadExampleComponent,
    CreateExampleComponent,
    UpdateExampleComponent,
    AdminComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {}