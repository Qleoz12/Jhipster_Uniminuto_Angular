import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITickets } from 'app/shared/model/tickets.model';
import { TicketsService } from './tickets.service';

@Component({
  selector: 'jhi-tickets-delete-dialog',
  templateUrl: './tickets-delete-dialog.component.html'
})
export class TicketsDeleteDialogComponent {
  tickets: ITickets;

  constructor(protected ticketsService: TicketsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ticketsService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ticketsListModification',
        content: 'Deleted an tickets'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tickets-delete-popup',
  template: ''
})
export class TicketsDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tickets }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TicketsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tickets = tickets;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tickets', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tickets', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
