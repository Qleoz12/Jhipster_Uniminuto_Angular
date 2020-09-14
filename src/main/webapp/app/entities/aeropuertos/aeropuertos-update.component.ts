import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAeropuertos, Aeropuertos } from 'app/shared/model/aeropuertos.model';
import { AeropuertosService } from './aeropuertos.service';
import { ICiudades } from 'app/shared/model/ciudades.model';
import { CiudadesService } from 'app/entities/ciudades/ciudades.service';

@Component({
  selector: 'jhi-aeropuertos-update',
  templateUrl: './aeropuertos-update.component.html'
})
export class AeropuertosUpdateComponent implements OnInit {
  isSaving: boolean;

  ciudades: ICiudades[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    ciudadesId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected aeropuertosService: AeropuertosService,
    protected ciudadesService: CiudadesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aeropuertos }) => {
      this.updateForm(aeropuertos);
    });
    this.ciudadesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICiudades[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICiudades[]>) => response.body)
      )
      .subscribe((res: ICiudades[]) => (this.ciudades = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(aeropuertos: IAeropuertos) {
    this.editForm.patchValue({
      id: aeropuertos.id,
      nombre: aeropuertos.nombre,
      ciudadesId: aeropuertos.ciudadesId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const aeropuertos = this.createFromForm();
    if (aeropuertos.id !== undefined) {
      this.subscribeToSaveResponse(this.aeropuertosService.update(aeropuertos));
    } else {
      this.subscribeToSaveResponse(this.aeropuertosService.create(aeropuertos));
    }
  }

  private createFromForm(): IAeropuertos {
    return {
      ...new Aeropuertos(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      ciudadesId: this.editForm.get(['ciudadesId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAeropuertos>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCiudadesById(index: number, item: ICiudades) {
    return item.id;
  }
}
