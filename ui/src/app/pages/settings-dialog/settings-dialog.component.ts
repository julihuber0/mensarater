import {Component, inject, OnInit} from '@angular/core';
import {MatDialogModule, MatDialogRef} from "@angular/material/dialog";
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
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
  styleUrl: './settings-dialog.component.scss'
})
export class SettingsDialogComponent implements OnInit{

  private readonly formBuilder: FormBuilder = inject(FormBuilder);
  private readonly mensaService: MensaService = inject(MensaService);

  dialogRef: MatDialogRef<SettingsDialogComponent> = inject(MatDialogRef<SettingsDialogComponent>);

  openMensaId?: number;

  availableMensas: MensaModel[] = [];

  settingsForm: FormGroup<SettingsForm> = new FormGroup<SettingsForm>({
    mensa: new FormControl<MensaModel | undefined>(undefined, { nonNullable: true, validators: [Validators.required] }),
  })

  async ngOnInit() {
    this.availableMensas = await lastValueFrom(this.mensaService.getAllMensas());
    let mensa = this.availableMensas.find(m => m.openMensaId === this.openMensaId);
    if (!mensa) {
      mensa = this.availableMensas[0];
    }
    this.settingsForm = this.formBuilder.group({
      mensa: new FormControl<MensaModel | undefined>(mensa, {nonNullable: true, validators: [Validators.required]}),
    });
  }

  onSave() {
    if (!this.settingsForm.invalid) {
      const inputs = this.settingsForm.value;
      this.dialogRef.close(inputs.mensa);
    }
  }

}
