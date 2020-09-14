import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IVuelos } from 'app/shared/model/vuelos.model';
import { VuelosService } from './vuelos.service';

@Component({
  selector: 'jhi-vuelos-delete-dialog',
  templateUrl: './vuelos-delete-dialog.component.html'
})
export class VuelosDeleteDialogComponent {
  vuelos: IVuelos;

  constructor(protected vuelosService: VuelosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.vuelosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'vuelosListModification',
        content: 'Deleted an vuelos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-vuelos-delete-popup',
  template: ''
})
export class VuelosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ vuelos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(VuelosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.vuelos = vuelos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/vuelos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/vuelos', { outlets: { popup: null } }]);
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
