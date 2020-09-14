import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAvionModelos } from 'app/shared/model/avion-modelos.model';
import { AvionModelosService } from './avion-modelos.service';

@Component({
  selector: 'jhi-avion-modelos-delete-dialog',
  templateUrl: './avion-modelos-delete-dialog.component.html'
})
export class AvionModelosDeleteDialogComponent {
  avionModelos: IAvionModelos;

  constructor(
    protected avionModelosService: AvionModelosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.avionModelosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'avionModelosListModification',
        content: 'Deleted an avionModelos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-avion-modelos-delete-popup',
  template: ''
})
export class AvionModelosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ avionModelos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AvionModelosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.avionModelos = avionModelos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/avion-modelos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/avion-modelos', { outlets: { popup: null } }]);
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
