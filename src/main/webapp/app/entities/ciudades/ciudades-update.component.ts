import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICiudades, Ciudades } from 'app/shared/model/ciudades.model';
import { CiudadesService } from './ciudades.service';

@Component({
  selector: 'jhi-ciudades-update',
  templateUrl: './ciudades-update.component.html'
})
export class CiudadesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    ciudad: [],
    pais: []
  });

  constructor(protected ciudadesService: CiudadesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ciudades }) => {
      this.updateForm(ciudades);
    });
  }

  updateForm(ciudades: ICiudades) {
    this.editForm.patchValue({
      id: ciudades.id,
      ciudad: ciudades.ciudad,
      pais: ciudades.pais
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const ciudades = this.createFromForm();
    if (ciudades.id !== undefined) {
      this.subscribeToSaveResponse(this.ciudadesService.update(ciudades));
    } else {
      this.subscribeToSaveResponse(this.ciudadesService.create(ciudades));
    }
  }

  private createFromForm(): ICiudades {
    return {
      ...new Ciudades(),
      id: this.editForm.get(['id']).value,
      ciudad: this.editForm.get(['ciudad']).value,
      pais: this.editForm.get(['pais']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICiudades>>) {
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
