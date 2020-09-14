import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IVuelos } from 'app/shared/model/vuelos.model';

@Component({
  selector: 'jhi-vuelos-detail',
  templateUrl: './vuelos-detail.component.html'
})
export class VuelosDetailComponent implements OnInit {
  vuelos: IVuelos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vuelos }) => {
      this.vuelos = vuelos;
    });
  }

  previousState() {
    window.history.back();
  }
}
