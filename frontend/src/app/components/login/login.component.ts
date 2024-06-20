import { Component, inject } from '@angular/core';
import {
  FormControl,
  ReactiveFormsModule,
  FormGroup,  
  Validators,
} from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { jwtDecode } from 'jwt-decode';
import { Credentials, LoggedInUser } from 'src/app/shared/interfaces/user';
import { UserService } from 'src/app/shared/services/user.service';


@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    RouterLink,
    RouterOutlet
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  userService = inject(UserService);
  router = inject(Router);
  snackBar = inject(MatSnackBar);

  invalidLogin = false;

  form = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', Validators.required),
  });

  onSubmit() {
    const credentials = this.form.value as Credentials;
    this.userService.loginUser(credentials).subscribe({
      next: (response) => {
        // console.log('Response from login:', response);
        const jwtToken = response.jwtToken;        
        localStorage.setItem('jwtToken', jwtToken);
        const role = response.role
        localStorage.setItem('role', role)
        const decodedTokenSubject = jwtDecode(jwtToken).sub;
        this.userService.user.set({          
          email: decodedTokenSubject,
          role: role
        });
        // console.log('Decoded Token:', decodedTokenSubject);
        
        if (role === 'Admin') {
          this.router.navigate(['admin']);
        } else if (role === 'User') {
          this.router.navigate(['crud-example-read']);
        }        
      },
      error: (response) => {
        console.error('Login Error', response);
        this.invalidLogin = true;
        this.snackBar.open('Invalid email or password', 'Close', {duration: 3000});
        this.form.reset();
      },
    });
  }
}
