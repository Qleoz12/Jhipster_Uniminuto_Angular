import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICiudades } from 'app/shared/model/ciudades.model';
import { CiudadesService } from './ciudades.service';

@Component({
  selector: 'jhi-ciudades-delete-dialog',
  templateUrl: './ciudades-delete-dialog.component.html'
})
export class CiudadesDeleteDialogComponent {
  ciudades: ICiudades;

  constructor(protected ciudadesService: CiudadesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.ciudadesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'ciudadesListModification',
        content: 'Deleted an ciudades'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-ciudades-delete-popup',
  template: ''
})
export class CiudadesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ciudades }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CiudadesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.ciudades = ciudades;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/ciudades', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/ciudades', { outlets: { popup: null } }]);
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
