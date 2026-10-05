import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SettingsDialogComponent } from './settings-dialog.component';
import {MensaService} from "../../service/mensa.service";
import {MatDialogRef} from "@angular/material/dialog";
import {of} from "rxjs";

describe('SettingsDialogComponent', () => {
  let component: SettingsDialogComponent;
  let fixture: ComponentFixture<SettingsDialogComponent>;

  let mockMensaService = {getAllMensas: vi.fn(() => of([]))};
  let mockDialogRef = {close: vi.fn()};

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SettingsDialogComponent],
      providers: [
        {provide: MensaService, useValue: mockMensaService},
        {provide: MatDialogRef, useValue: mockDialogRef}
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SettingsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
