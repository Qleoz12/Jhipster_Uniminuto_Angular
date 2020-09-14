import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProgramasVuelos } from 'app/shared/model/programas-vuelos.model';
import { ProgramasVuelosService } from './programas-vuelos.service';

@Component({
  selector: 'jhi-programas-vuelos-delete-dialog',
  templateUrl: './programas-vuelos-delete-dialog.component.html'
})
export class ProgramasVuelosDeleteDialogComponent {
  programasVuelos: IProgramasVuelos;

  constructor(
    protected programasVuelosService: ProgramasVuelosService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.programasVuelosService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'programasVuelosListModification',
        content: 'Deleted an programasVuelos'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-programas-vuelos-delete-popup',
  template: ''
})
export class ProgramasVuelosDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ programasVuelos }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProgramasVuelosDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.programasVuelos = programasVuelos;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/programas-vuelos', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/programas-vuelos', { outlets: { popup: null } }]);
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
