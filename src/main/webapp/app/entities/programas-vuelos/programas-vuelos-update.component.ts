import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProgramasVuelos, ProgramasVuelos } from 'app/shared/model/programas-vuelos.model';
import { ProgramasVuelosService } from './programas-vuelos.service';
import { IAeropuertos } from 'app/shared/model/aeropuertos.model';
import { AeropuertosService } from 'app/entities/aeropuertos/aeropuertos.service';

@Component({
  selector: 'jhi-programas-vuelos-update',
  templateUrl: './programas-vuelos-update.component.html'
})
export class ProgramasVuelosUpdateComponent implements OnInit {
  isSaving: boolean;

  aeropuertos: IAeropuertos[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    totalDias: [],
    aeropuertoSalidaId: [],
    aeropuertoArriboId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected programasVuelosService: ProgramasVuelosService,
    protected aeropuertosService: AeropuertosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ programasVuelos }) => {
      this.updateForm(programasVuelos);
    });
    this.aeropuertosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAeropuertos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAeropuertos[]>) => response.body)
      )
      .subscribe((res: IAeropuertos[]) => (this.aeropuertos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(programasVuelos: IProgramasVuelos) {
    this.editForm.patchValue({
      id: programasVuelos.id,
      nombre: programasVuelos.nombre,
      totalDias: programasVuelos.totalDias,
      aeropuertoSalidaId: programasVuelos.aeropuertoSalidaId,
      aeropuertoArriboId: programasVuelos.aeropuertoArriboId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const programasVuelos = this.createFromForm();
    if (programasVuelos.id !== undefined) {
      this.subscribeToSaveResponse(this.programasVuelosService.update(programasVuelos));
    } else {
      this.subscribeToSaveResponse(this.programasVuelosService.create(programasVuelos));
    }
  }

  private createFromForm(): IProgramasVuelos {
    return {
      ...new ProgramasVuelos(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      totalDias: this.editForm.get(['totalDias']).value,
      aeropuertoSalidaId: this.editForm.get(['aeropuertoSalidaId']).value,
      aeropuertoArriboId: this.editForm.get(['aeropuertoArriboId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramasVuelos>>) {
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

  trackAeropuertosById(index: number, item: IAeropuertos) {
    return item.id;
  }
}
