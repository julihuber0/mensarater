import {Component, inject, DOCUMENT} from '@angular/core';
import {AuthService} from "../../service/auth.service";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatMenuModule} from "@angular/material/menu";
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";

import {FormsModule} from "@angular/forms";
import {MatChipsModule} from "@angular/material/chips";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  imports: [MatToolbarModule, MatMenuModule, MatIconModule, MatButtonModule, FormsModule, MatChipsModule],
  styleUrl: './header.component.scss',
})
export class HeaderComponent {

  public readonly authService: AuthService = inject(AuthService);
  private readonly router: Router = inject(Router);
  private document = inject(DOCUMENT);

  currentMode: 'light' | 'dark';

  darkModeToggled: boolean;

  constructor() {
    const storedMode = localStorage.getItem('mensaRater_mode');
    if (storedMode) {
      if (storedMode === 'dark') {
        this.currentMode = 'dark';
        this.darkModeToggled = true;
        this.document.body.classList.toggle('dark');
      } else {
        this.currentMode = 'light';
        this.darkModeToggled = false;
      }
    } else {
      this.currentMode = 'light';
      this.darkModeToggled = false;
    }
  }

  goToOverview() {
    this.router.navigate(['/']);
  }

  onToggleDarkMode() {
    if (this.currentMode === 'dark') {
      this.currentMode = 'light';
      localStorage.setItem('mensaRater_mode', 'light');
      this.darkModeToggled = false;
    } else {
      this.currentMode = 'dark';
      localStorage.setItem('mensaRater_mode', 'dark');
      this.darkModeToggled = true;
    }
    this.document.body.classList.toggle('dark');
  }
}
