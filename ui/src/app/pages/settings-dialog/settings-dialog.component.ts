import {Component, inject, OnInit, signal, WritableSignal, ChangeDetectionStrategy} from '@angular/core';
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MensaService} from "../../service/mensa.service";
import {MatButton} from "@angular/material/button";
import {MensaModel} from "../../dto/mensa.model";
import {lastValueFrom} from "rxjs";

interface SettingsForm {
  mensa: FormControl<MensaModel | undefined>
}

@Component({
  selector: 'app-settings-dialog',
  imports: [MatDialogModule, ReactiveFormsModule, MatFormFieldModule, MatSelectModule, MatButton],
  templateUrl: './settings-dialog.component.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './settings-dialog.component.scss'
})
export class SettingsDialogComponent implements OnInit{

  private readonly mensaService: MensaService = inject(MensaService);

  dialogRef: MatDialogRef<SettingsDialogComponent> = inject(MatDialogRef<SettingsDialogComponent>);

  openMensaId?: number;

  availableMensas: WritableSignal<MensaModel[]> = signal<MensaModel[]>([]);

  settingsForm: FormGroup<SettingsForm> = new FormGroup<SettingsForm>({
    mensa: new FormControl<MensaModel | undefined>(undefined, { nonNullable: true, validators: [Validators.required] }),
  })

  async ngOnInit() {
    const availableMensas = await lastValueFrom(this.mensaService.getAllMensas());
    let mensa = availableMensas.find(m => m.openMensaId === this.openMensaId);
    if (!mensa) {
      mensa = availableMensas[0];
    }
    this.availableMensas.set(availableMensas);
    this.settingsForm.controls.mensa.setValue(mensa);
  }

  onSave() {
    if (!this.settingsForm.invalid) {
      const inputs = this.settingsForm.value;
      this.dialogRef.close(inputs.mensa);
    }
  }

}
