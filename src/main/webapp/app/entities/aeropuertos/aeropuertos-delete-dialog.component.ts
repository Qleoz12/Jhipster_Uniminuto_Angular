import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAeropuertos } from 'app/shared/model/aeropuertos.model';
import { AeropuertosService } from './aeropuertos.service';

@Component({
  selector: 'jhi-aeropuertos-delete-dialog',
  templateUrl: './aeropuertos-delete-dialog.component.html'
})
export class AeropuertosDeleteDialogComponent {
  aeropuertos: IAeropuertos;

  constructor(
    protected aeropuertosService: AeropuertosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.aeropuertosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'aeropuertosListModification',
        content: 'Deleted an aeropuertos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-aeropuertos-delete-popup',
  template: ''
})
export class AeropuertosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aeropuertos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AeropuertosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.aeropuertos = aeropuertos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/aeropuertos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/aeropuertos', { outlets: { popup: null } }]);
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
