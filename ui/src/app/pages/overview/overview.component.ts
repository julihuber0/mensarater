import {Component, computed, inject, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {DishService} from "../../service/dish.service";
import {DishModel} from "../../dto/dish.model";
import {MatDialog} from "@angular/material/dialog";
import {RatingDialogComponent} from "../rating-dialog/rating-dialog.component";
import {MatIconModule} from "@angular/material/icon";
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {MatButtonModule} from "@angular/material/button";
import {DishCardComponent} from "../../components/dish-card/dish-card.component";
import {MatCardModule} from "@angular/material/card";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {FormsModule} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {SkeletonCardComponent} from "../../components/skeleton-card/skeleton-card.component";
import {MensaService} from "../../service/mensa.service";
import {lastValueFrom} from "rxjs";
import {SettingsDialogComponent} from "../settings-dialog/settings-dialog.component";
import {MensaModel} from "../../dto/mensa.model";
import {DatePipe} from "@angular/common";
import {MatMenuModule} from "@angular/material/menu";
import {MatChipsModule} from "@angular/material/chips";
import {MatBadgeModule} from "@angular/material/badge";
import {MatSelectModule} from "@angular/material/select";
import {MatDividerModule} from "@angular/material/divider";

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  imports: [MatIconModule, MatProgressBarModule, MatButtonModule, DishCardComponent, MatCardModule, MatButtonToggleModule, FormsModule, SkeletonCardComponent, DatePipe, MatMenuModule, MatChipsModule, MatBadgeModule, MatSelectModule, MatDividerModule],
  styleUrl: './overview.component.scss',
})
export class OverviewComponent implements OnInit {

  private readonly dishService: DishService = inject(DishService);
  private readonly mensaService: MensaService = inject(MensaService);
  private readonly dialog: MatDialog = inject(MatDialog);

  readonly authService: AuthService = inject(AuthService);

  menuData: WritableSignal<DishModel[]> = signal<DishModel[]>([]);
  filteredMenuData: Signal<DishModel[]> = computed(() => {
    const menu = this.menuData();
    const activeFilter = this.activeFilter();
    if (!activeFilter || activeFilter.length === 0) {
      return menu;
    }
    return menu.filter(d => activeFilter.includes(d.category));
  });
  selectedMensa: MensaModel | undefined;
  selectedDate: WritableSignal<Date> = signal<Date>(new Date());
  allCategories: Signal<string[]> = computed(() => {
    const menu = this.menuData();
    const activeFilter = this.activeFilter();
    const all = Array.from(new Set(menu.map(dish => dish.category)));
    return all.filter(c => !activeFilter.includes(c));
}
  )
  activeFilter: WritableSignal<string[]> = signal([]);

  private readonly FILTER_STORAGE_KEY = 'mensarater_filters_';

  loading: boolean = false;

  rateMode: 'avg' | 'user' = 'avg';

  async ngOnInit() {
    this.mensaService.getSelectedMensaSubject().subscribe(mensa => {
      if (mensa) {
        this.selectedMensa = mensa;
        this.loadFiltersFromLocalStorage();
        this.getTableData();
      } else {
        this.openSettingsDialog();
      }
    })
  }

  async openSettingsDialog() {
    const dialogRef = this.dialog.open(
      SettingsDialogComponent,
      {
        width: '500px',
        height: 'auto',
        disableClose: true,
        backdropClass: 'dialog-background',
      }
    );
    dialogRef.componentInstance.openMensaId = this.selectedMensa?.openMensaId;
    const updatedMensa = await lastValueFrom(dialogRef.afterClosed());
    if (updatedMensa) {
      await this.saveSelectedMensa(updatedMensa);
      this.loadFiltersFromLocalStorage();
    }
  }

  setToday() {
    this.selectedDate.set(new Date());
    this.getTableData();
  }

  prevDay() {
    const prevDate = new Date(this.selectedDate());
    prevDate.setDate(prevDate.getDate() - 1);
    this.selectedDate.set(prevDate);
    this.getTableData();
  }
  nextDay() {
    const nextDate = new Date(this.selectedDate());
    nextDate.setDate(nextDate.getDate() + 1);
    this.selectedDate.set(nextDate);
    this.getTableData();
  }

  onClickDish(dish: DishModel) {
    if (this.selectedMensa) {
      const dialogRef = this.dialog.open(
        RatingDialogComponent,
        {
          width: '500px',
          height: 'auto',
          disableClose: true,
          backdropClass: 'dialog-background',
        }
      );
      dialogRef.componentInstance.dish = dish;
    }
  }

  async getTableData() {
    if (this.selectedMensa) {
      this.loading = true;
      if (this.rateMode === 'avg') {
        this.dishService.getAverageDishRating(this.selectedMensa.openMensaId, this.selectedDate()).subscribe({
          next: dishes => {
            this.menuData.set(dishes);
          },
          complete: () => {
            this.loading = false;
          }
        });
      } else {
        this.dishService.getUserDishRating(this.selectedDate()).subscribe({
          next: dishes => {
            this.menuData.set(dishes)
          },
          complete: () => {
            this.loading = false;
          }
        })
      }
    }
  }

  private async saveSelectedMensa(mensa: MensaModel) {
    if (this.authService.loggedIn) {
      await lastValueFrom(this.mensaService.saveMensaOfUser(mensa));
    }
    localStorage.setItem('mensarater_mensa_id', String(mensa.openMensaId));
    this.mensaService.updateSelectedMensa(mensa);
  }

  // LocalStorage helpers
  private loadFiltersFromLocalStorage() {
    const raw = localStorage.getItem(this.FILTER_STORAGE_KEY + this.selectedMensa?.openMensaId || '');
    if (raw) {
      try {
        const parsed = JSON.parse(raw);
        if (Array.isArray(parsed)) {
          this.activeFilter.set(parsed);
        }
      } catch (e) {
        // ignore parse errors and keep default
        this.activeFilter.set([]);
      }
    } else {
      this.activeFilter.set([]);
    }
  }

  private saveFiltersToLocalStorage() {
    try {
      localStorage.setItem(this.FILTER_STORAGE_KEY + this.selectedMensa?.openMensaId || '', JSON.stringify(this.activeFilter() || []));
    } catch (e) {
      // ignore storage errors
    }
  }

  addFilter(category: string) {
    this.activeFilter.update(f => {
      if (!f.includes(category)) {
        return [...f, category];
      }
      return f;
    });
    this.saveFiltersToLocalStorage();
  }

  removeFilter(category: string) {
    this.activeFilter.update(f => f.filter(c => c !== category));
    this.saveFiltersToLocalStorage();
  }
}
