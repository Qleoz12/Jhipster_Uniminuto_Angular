import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAeropuertos } from 'app/shared/model/aeropuertos.model';

@Component({
  selector: 'jhi-aeropuertos-detail',
  templateUrl: './aeropuertos-detail.component.html'
})
export class AeropuertosDetailComponent implements OnInit {
  aeropuertos: IAeropuertos;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aeropuertos }) => {
      this.aeropuertos = aeropuertos;
    });
  }

  previousState() {
    window.history.back();
  }
}
