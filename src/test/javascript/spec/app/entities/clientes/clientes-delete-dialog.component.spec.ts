import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { ClientesDeleteDialogComponent } from 'app/entities/clientes/clientes-delete-dialog.component';
import { ClientesService } from 'app/entities/clientes/clientes.service';

describe('Component Tests', () => {
  describe('Clientes Management Delete Component', () => {
    let comp: ClientesDeleteDialogComponent;
    let fixture: ComponentFixture<ClientesDeleteDialogComponent>;
    let service: ClientesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [ClientesDeleteDialogComponent]
      })
        .overrideTemplate(ClientesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ClientesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClientesService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
