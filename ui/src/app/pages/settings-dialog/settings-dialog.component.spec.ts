import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SettingsDialogComponent } from './settings-dialog.component';
import {MensaService} from "../../service/mensa.service";
import {MatDialogRef} from "@angular/material/dialog";

describe('SettingsDialogComponent', () => {
  let component: SettingsDialogComponent;
  let fixture: ComponentFixture<SettingsDialogComponent>;

  let mockMensaService: jasmine.SpyObj<MensaService> = jasmine.createSpyObj('MensaService', ['getMensaOfUser']);
  let mockDialogRef: jasmine.SpyObj<any> = jasmine.createSpyObj('MatDialogRef', ['close']);

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
