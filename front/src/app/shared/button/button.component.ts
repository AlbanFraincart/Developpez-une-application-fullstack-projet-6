import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-button',
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss'],
})
export class ButtonComponent {
  @Input() label: string = ''; // Texte du bouton
  @Input() disabled: boolean = false; // Désactiver le bouton

  @Output() clickEvent = new EventEmitter<void>(); // Événement clic

  onClick() {
    if (!this.disabled) {
      this.clickEvent.emit();
    }
  }
}
