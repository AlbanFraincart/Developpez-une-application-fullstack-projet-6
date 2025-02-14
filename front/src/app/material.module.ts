import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { MatInputModule } from '@angular/material/input';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatDialogModule } from '@angular/material/dialog';
import { MatTableModule } from '@angular/material/table';

@NgModule({
    exports: [
        MatButtonModule,
        // MatToolbarModule,
        // MatIconModule,
        // MatSidenavModule,
        // MatListModule,
        // MatInputModule,
        // MatCardModule,
        // MatSnackBarModule,
        // MatDialogModule,
        // MatTableModule,
    ]
})
export class MaterialModule { }
