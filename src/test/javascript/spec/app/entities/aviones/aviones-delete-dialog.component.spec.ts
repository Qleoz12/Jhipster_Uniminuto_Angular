import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { AvionesDeleteDialogComponent } from 'app/entities/aviones/aviones-delete-dialog.component';
import { AvionesService } from 'app/entities/aviones/aviones.service';

describe('Component Tests', () => {
  describe('Aviones Management Delete Component', () => {
    let comp: AvionesDeleteDialogComponent;
    let fixture: ComponentFixture<AvionesDeleteDialogComponent>;
    let service: AvionesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [AvionesDeleteDialogComponent]
      })
        .overrideTemplate(AvionesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvionesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvionesService);
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
