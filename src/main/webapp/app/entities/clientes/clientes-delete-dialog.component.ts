import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IClientes } from 'app/shared/model/clientes.model';
import { ClientesService } from './clientes.service';

@Component({
  selector: 'jhi-clientes-delete-dialog',
  templateUrl: './clientes-delete-dialog.component.html'
})
export class ClientesDeleteDialogComponent {
  clientes: IClientes;

  constructor(protected clientesService: ClientesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.clientesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'clientesListModification',
        content: 'Deleted an clientes'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-clientes-delete-popup',
  template: ''
})
export class ClientesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ clientes }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ClientesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.clientes = clientes;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/clientes', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/clientes', { outlets: { popup: null } }]);
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
