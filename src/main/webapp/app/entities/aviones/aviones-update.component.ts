import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAviones, Aviones } from 'app/shared/model/aviones.model';
import { AvionesService } from './aviones.service';
import { IAvionModelos } from 'app/shared/model/avion-modelos.model';
import { AvionModelosService } from 'app/entities/avion-modelos/avion-modelos.service';

@Component({
  selector: 'jhi-aviones-update',
  templateUrl: './aviones-update.component.html'
})
export class AvionesUpdateComponent implements OnInit {
  isSaving: boolean;

  avionmodelos: IAvionModelos[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    avionModelosId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected avionesService: AvionesService,
    protected avionModelosService: AvionModelosService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ aviones }) => {
      this.updateForm(aviones);
    });
    this.avionModelosService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAvionModelos[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAvionModelos[]>) => response.body)
      )
      .subscribe((res: IAvionModelos[]) => (this.avionmodelos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(aviones: IAviones) {
    this.editForm.patchValue({
      id: aviones.id,
      nombre: aviones.nombre,
      avionModelosId: aviones.avionModelosId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const aviones = this.createFromForm();
    if (aviones.id !== undefined) {
      this.subscribeToSaveResponse(this.avionesService.update(aviones));
    } else {
      this.subscribeToSaveResponse(this.avionesService.create(aviones));
    }
  }

  private createFromForm(): IAviones {
    return {
      ...new Aviones(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      avionModelosId: this.editForm.get(['avionModelosId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAviones>>) {
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

  trackAvionModelosById(index: number, item: IAvionModelos) {
    return item.id;
  }
}
