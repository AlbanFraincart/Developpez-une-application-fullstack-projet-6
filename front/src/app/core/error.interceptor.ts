import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                let errorMessage = 'Une erreur est survenue.';

                if (error.error && typeof error.error === 'object') {
                    errorMessage = error.error.message || errorMessage;
                } else if (typeof error.error === 'string') {
                    errorMessage = error.error;
                }

                return throwError(() => new Error(errorMessage));
            })
        );
    }
}
