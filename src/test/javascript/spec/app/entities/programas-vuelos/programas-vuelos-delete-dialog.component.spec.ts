import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { ProgramasVuelosDeleteDialogComponent } from 'app/entities/programas-vuelos/programas-vuelos-delete-dialog.component';
import { ProgramasVuelosService } from 'app/entities/programas-vuelos/programas-vuelos.service';

describe('Component Tests', () => {
  describe('ProgramasVuelos Management Delete Component', () => {
    let comp: ProgramasVuelosDeleteDialogComponent;
    let fixture: ComponentFixture<ProgramasVuelosDeleteDialogComponent>;
    let service: ProgramasVuelosService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [ProgramasVuelosDeleteDialogComponent]
      })
        .overrideTemplate(ProgramasVuelosDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgramasVuelosDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProgramasVuelosService);
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
