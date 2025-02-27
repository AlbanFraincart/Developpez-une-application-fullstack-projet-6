export interface AuthCredentials {
    email: string;
    password: string;
}

export interface User {
    id: number;
    email: string;
    password: string;
    username: string;
}

export interface RegisterUser {
    email: string;
    password: string;
    username: string;
}
