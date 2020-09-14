import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { VuelosDeleteDialogComponent } from 'app/entities/vuelos/vuelos-delete-dialog.component';
import { VuelosService } from 'app/entities/vuelos/vuelos.service';

describe('Component Tests', () => {
  describe('Vuelos Management Delete Component', () => {
    let comp: VuelosDeleteDialogComponent;
    let fixture: ComponentFixture<VuelosDeleteDialogComponent>;
    let service: VuelosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [VuelosDeleteDialogComponent]
      })
        .overrideTemplate(VuelosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VuelosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VuelosService);
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
