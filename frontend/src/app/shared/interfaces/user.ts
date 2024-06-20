export interface User {
    id: string;
    firstname: string;
    lastname: string;
    email: string;
    password: string;
  }
  
  export interface Credentials {
    email: string;
    password: string;
  }
  
  export interface LoggedInUser {    
    email: string;
    role: string;
  }