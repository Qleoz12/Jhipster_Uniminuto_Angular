import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAvionModelos, AvionModelos } from 'app/shared/model/avion-modelos.model';
import { AvionModelosService } from './avion-modelos.service';

@Component({
  selector: 'jhi-avion-modelos-update',
  templateUrl: './avion-modelos-update.component.html'
})
export class AvionModelosUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombre: [],
    capacidad: []
  });

  constructor(protected avionModelosService: AvionModelosService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ avionModelos }) => {
      this.updateForm(avionModelos);
    });
  }

  updateForm(avionModelos: IAvionModelos) {
    this.editForm.patchValue({
      id: avionModelos.id,
      nombre: avionModelos.nombre,
      capacidad: avionModelos.capacidad
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const avionModelos = this.createFromForm();
    if (avionModelos.id !== undefined) {
      this.subscribeToSaveResponse(this.avionModelosService.update(avionModelos));
    } else {
      this.subscribeToSaveResponse(this.avionModelosService.create(avionModelos));
    }
  }

  private createFromForm(): IAvionModelos {
    return {
      ...new AvionModelos(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      capacidad: this.editForm.get(['capacidad']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvionModelos>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
