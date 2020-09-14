import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAviones } from 'app/shared/model/aviones.model';
import { AvionesService } from './aviones.service';

@Component({
  selector: 'jhi-aviones-delete-dialog',
  templateUrl: './aviones-delete-dialog.component.html'
})
export class AvionesDeleteDialogComponent {
  aviones: IAviones;

  constructor(protected avionesService: AvionesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.avionesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'avionesListModification',
        content: 'Deleted an aviones'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-aviones-delete-popup',
  template: ''
})
export class AvionesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ aviones }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AvionesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.aviones = aviones;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/aviones', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/aviones', { outlets: { popup: null } }]);
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
