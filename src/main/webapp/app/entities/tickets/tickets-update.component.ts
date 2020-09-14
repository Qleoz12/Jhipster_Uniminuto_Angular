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
import { ITickets, Tickets } from 'app/shared/model/tickets.model';
import { TicketsService } from './tickets.service';
import { IClientes } from 'app/shared/model/clientes.model';
import { ClientesService } from 'app/entities/clientes/clientes.service';
import { IProgramasVuelos } from 'app/shared/model/programas-vuelos.model';
import { ProgramasVuelosService } from 'app/entities/programas-vuelos/programas-vuelos.service';

@Component({
  selector: 'jhi-tickets-update',
  templateUrl: './tickets-update.component.html'
})
export class TicketsUpdateComponent implements OnInit {
  isSaving: boolean;

  clientes: IClientes[];

  programasvuelos: IProgramasVuelos[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    valor: [],
    descuento: [],
    estado: [],
    fecha: [],
    clientesId: [],
    programasVuelosId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected ticketsService: TicketsService,
    protected clientesService: ClientesService,
    protected programasVuelosService: ProgramasVuelosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tickets }) => {
      this.updateForm(tickets);
    });
    this.clientesService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IClientes[]>) => mayBeOk.ok),
        map((response: HttpResponse<IClientes[]>) => response.body)
      )
      .subscribe((res: IClientes[]) => (this.clientes = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.programasVuelosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProgramasVuelos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProgramasVuelos[]>) => response.body)
      )
      .subscribe((res: IProgramasVuelos[]) => (this.programasvuelos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(tickets: ITickets) {
    this.editForm.patchValue({
      id: tickets.id,
      nombre: tickets.nombre,
      valor: tickets.valor,
      descuento: tickets.descuento,
      estado: tickets.estado,
      fecha: tickets.fecha != null ? tickets.fecha.format(DATE_TIME_FORMAT) : null,
      clientesId: tickets.clientesId,
      programasVuelosId: tickets.programasVuelosId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tickets = this.createFromForm();
    if (tickets.id !== undefined) {
      this.subscribeToSaveResponse(this.ticketsService.update(tickets));
    } else {
      this.subscribeToSaveResponse(this.ticketsService.create(tickets));
    }
  }

  private createFromForm(): ITickets {
    return {
      ...new Tickets(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      valor: this.editForm.get(['valor']).value,
      descuento: this.editForm.get(['descuento']).value,
      estado: this.editForm.get(['estado']).value,
      fecha: this.editForm.get(['fecha']).value != null ? moment(this.editForm.get(['fecha']).value, DATE_TIME_FORMAT) : undefined,
      clientesId: this.editForm.get(['clientesId']).value,
      programasVuelosId: this.editForm.get(['programasVuelosId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITickets>>) {
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

  trackClientesById(index: number, item: IClientes) {
    return item.id;
  }

  trackProgramasVuelosById(index: number, item: IProgramasVuelos) {
    return item.id;
  }
}
