import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IVuelos, Vuelos } from 'app/shared/model/vuelos.model';
import { VuelosService } from './vuelos.service';
import { IAeropuertos } from 'app/shared/model/aeropuertos.model';
import { AeropuertosService } from 'app/entities/aeropuertos/aeropuertos.service';
import { IAviones } from 'app/shared/model/aviones.model';
import { AvionesService } from 'app/entities/aviones/aviones.service';
import { IProgramasVuelos } from 'app/shared/model/programas-vuelos.model';
import { ProgramasVuelosService } from 'app/entities/programas-vuelos/programas-vuelos.service';

@Component({
  selector: 'jhi-vuelos-update',
  templateUrl: './vuelos-update.component.html'
})
export class VuelosUpdateComponent implements OnInit {
  isSaving: boolean;

  aeropuertos: IAeropuertos[];

  aviones: IAviones[];

  programasvuelos: IProgramasVuelos[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    diasSemana: [],
    fechaSalida: [],
    fechaArribo9: [],
    plazasVacias: [],
    orden: [],
    estadoVuelo: [],
    esEscala: [],
    aeropuertoSalidaId: [],
    aeropuertoArriboId: [],
    idAvionId: [],
    idProgramaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected vuelosService: VuelosService,
    protected aeropuertosService: AeropuertosService,
    protected avionesService: AvionesService,
    protected programasVuelosService: ProgramasVuelosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ vuelos }) => {
      this.updateForm(vuelos);
    });
    this.aeropuertosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAeropuertos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAeropuertos[]>) => response.body)
      )
      .subscribe((res: IAeropuertos[]) => (this.aeropuertos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.avionesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAviones[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAviones[]>) => response.body)
      )
      .subscribe((res: IAviones[]) => (this.aviones = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.programasVuelosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProgramasVuelos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProgramasVuelos[]>) => response.body)
      )
      .subscribe((res: IProgramasVuelos[]) => (this.programasvuelos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(vuelos: IVuelos) {
    this.editForm.patchValue({
      id: vuelos.id,
      nombre: vuelos.nombre,
      diasSemana: vuelos.diasSemana,
      fechaSalida: vuelos.fechaSalida != null ? vuelos.fechaSalida.format(DATE_TIME_FORMAT) : null,
      fechaArribo9: vuelos.fechaArribo9 != null ? vuelos.fechaArribo9.format(DATE_TIME_FORMAT) : null,
      plazasVacias: vuelos.plazasVacias,
      orden: vuelos.orden,
      estadoVuelo: vuelos.estadoVuelo,
      esEscala: vuelos.esEscala,
      aeropuertoSalidaId: vuelos.aeropuertoSalidaId,
      aeropuertoArriboId: vuelos.aeropuertoArriboId,
      idAvionId: vuelos.idAvionId,
      idProgramaId: vuelos.idProgramaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const vuelos = this.createFromForm();
    if (vuelos.id !== undefined) {
      this.subscribeToSaveResponse(this.vuelosService.update(vuelos));
    } else {
      this.subscribeToSaveResponse(this.vuelosService.create(vuelos));
    }
  }

  private createFromForm(): IVuelos {
    return {
      ...new Vuelos(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      diasSemana: this.editForm.get(['diasSemana']).value,
      fechaSalida:
        this.editForm.get(['fechaSalida']).value != null ? moment(this.editForm.get(['fechaSalida']).value, DATE_TIME_FORMAT) : undefined,
      fechaArribo9:
        this.editForm.get(['fechaArribo9']).value != null ? moment(this.editForm.get(['fechaArribo9']).value, DATE_TIME_FORMAT) : undefined,
      plazasVacias: this.editForm.get(['plazasVacias']).value,
      orden: this.editForm.get(['orden']).value,
      estadoVuelo: this.editForm.get(['estadoVuelo']).value,
      esEscala: this.editForm.get(['esEscala']).value,
      aeropuertoSalidaId: this.editForm.get(['aeropuertoSalidaId']).value,
      aeropuertoArriboId: this.editForm.get(['aeropuertoArriboId']).value,
      idAvionId: this.editForm.get(['idAvionId']).value,
      idProgramaId: this.editForm.get(['idProgramaId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVuelos>>) {
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

  trackAvionesById(index: number, item: IAviones) {
    return item.id;
  }

  trackProgramasVuelosById(index: number, item: IProgramasVuelos) {
    return item.id;
  }
}
