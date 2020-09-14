import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IClientes, Clientes } from 'app/shared/model/clientes.model';
import { ClientesService } from './clientes.service';

@Component({
  selector: 'jhi-clientes-update',
  templateUrl: './clientes-update.component.html'
})
export class ClientesUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nombres: [],
    appelidos: [],
    documentoTipo: [],
    documentoNumero: []
  });

  constructor(protected clientesService: ClientesService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ clientes }) => {
      this.updateForm(clientes);
    });
  }

  updateForm(clientes: IClientes) {
    this.editForm.patchValue({
      id: clientes.id,
      nombres: clientes.nombres,
      appelidos: clientes.appelidos,
      documentoTipo: clientes.documentoTipo,
      documentoNumero: clientes.documentoNumero
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const clientes = this.createFromForm();
    if (clientes.id !== undefined) {
      this.subscribeToSaveResponse(this.clientesService.update(clientes));
    } else {
      this.subscribeToSaveResponse(this.clientesService.create(clientes));
    }
  }

  private createFromForm(): IClientes {
    return {
      ...new Clientes(),
      id: this.editForm.get(['id']).value,
      nombres: this.editForm.get(['nombres']).value,
      appelidos: this.editForm.get(['appelidos']).value,
      documentoTipo: this.editForm.get(['documentoTipo']).value,
      documentoNumero: this.editForm.get(['documentoNumero']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IClientes>>) {
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
