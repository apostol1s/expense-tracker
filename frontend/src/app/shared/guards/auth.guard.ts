import { CanActivateFn, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = (route, state) => {
  const userService = inject(UserService);
  const router = inject(Router);


  // console.log('Auth Guard: Checking user role');
  // console.log('User:', userService.user());
  // console.log('Required role:', route.data['role']);

  if (userService.user() && userService.user().role === route.data['role']) {
    // console.log('Auth Guard: Role match, navigation allowed');
    return true;
  }

  // console.log('Auth Guard: Role mismatch or user not logged in, navigation denied');
  return router.navigate(['login']);
};
