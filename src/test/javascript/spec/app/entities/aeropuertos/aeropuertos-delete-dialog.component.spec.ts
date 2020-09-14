import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { AeropuertosDeleteDialogComponent } from 'app/entities/aeropuertos/aeropuertos-delete-dialog.component';
import { AeropuertosService } from 'app/entities/aeropuertos/aeropuertos.service';

describe('Component Tests', () => {
  describe('Aeropuertos Management Delete Component', () => {
    let comp: AeropuertosDeleteDialogComponent;
    let fixture: ComponentFixture<AeropuertosDeleteDialogComponent>;
    let service: AeropuertosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [AeropuertosDeleteDialogComponent]
      })
        .overrideTemplate(AeropuertosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AeropuertosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AeropuertosService);
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
