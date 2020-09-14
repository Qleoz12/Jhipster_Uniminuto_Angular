import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAviones } from 'app/shared/model/aviones.model';

@Component({
  selector: 'jhi-aviones-detail',
  templateUrl: './aviones-detail.component.html'
})
export class AvionesDetailComponent implements OnInit {
  aviones: IAviones;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aviones }) => {
      this.aviones = aviones;
    });
  }

  previousState() {
    window.history.back();
  }
}
