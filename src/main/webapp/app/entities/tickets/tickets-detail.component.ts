import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITickets } from 'app/shared/model/tickets.model';

@Component({
  selector: 'jhi-tickets-detail',
  templateUrl: './tickets-detail.component.html'
})
export class TicketsDetailComponent implements OnInit {
  tickets: ITickets;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tickets }) => {
      this.tickets = tickets;
    });
  }

  previousState() {
    window.history.back();
  }
}
