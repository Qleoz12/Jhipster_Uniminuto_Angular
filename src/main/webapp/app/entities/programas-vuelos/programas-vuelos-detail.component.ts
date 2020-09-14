import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramasVuelos } from 'app/shared/model/programas-vuelos.model';

@Component({
  selector: 'jhi-programas-vuelos-detail',
  templateUrl: './programas-vuelos-detail.component.html'
})
export class ProgramasVuelosDetailComponent implements OnInit {
  programasVuelos: IProgramasVuelos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ programasVuelos }) => {
      this.programasVuelos = programasVuelos;
    });
  }

  previousState() {
    window.history.back();
  }
}
