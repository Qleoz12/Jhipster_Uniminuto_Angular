import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IClientes } from 'app/shared/model/clientes.model';

@Component({
  selector: 'jhi-clientes-detail',
  templateUrl: './clientes-detail.component.html'
})
export class ClientesDetailComponent implements OnInit {
  clientes: IClientes;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clientes }) => {
      this.clientes = clientes;
    });
  }

  previousState() {
    window.history.back();
  }
}
