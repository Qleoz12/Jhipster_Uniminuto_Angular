import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvionModelos } from 'app/shared/model/avion-modelos.model';

@Component({
  selector: 'jhi-avion-modelos-detail',
  templateUrl: './avion-modelos-detail.component.html'
})
export class AvionModelosDetailComponent implements OnInit {
  avionModelos: IAvionModelos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ avionModelos }) => {
      this.avionModelos = avionModelos;
    });
  }

  previousState() {
    window.history.back();
  }
}
