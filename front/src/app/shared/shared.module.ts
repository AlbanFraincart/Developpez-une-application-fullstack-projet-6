import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from './button/button.component';
import { MatButtonModule } from '@angular/material/button';
import { NavbarComponent } from './navbar/navbar.component';
import { MaterialModule } from '../material.module';
import { RouterModule } from '@angular/router';
import { CardComponent } from './card/card.component';

@NgModule({
    declarations: [ButtonComponent, NavbarComponent, CardComponent],
    imports: [CommonModule, MaterialModule, RouterModule],
    exports: [ButtonComponent, NavbarComponent, CardComponent],
})
export class SharedModule { }
