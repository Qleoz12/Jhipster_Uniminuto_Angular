import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { ProgramasVuelosUpdateComponent } from 'app/entities/programas-vuelos/programas-vuelos-update.component';
import { ProgramasVuelosService } from 'app/entities/programas-vuelos/programas-vuelos.service';
import { ProgramasVuelos } from 'app/shared/model/programas-vuelos.model';

describe('Component Tests', () => {
  describe('ProgramasVuelos Management Update Component', () => {
    let comp: ProgramasVuelosUpdateComponent;
    let fixture: ComponentFixture<ProgramasVuelosUpdateComponent>;
    let service: ProgramasVuelosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [ProgramasVuelosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProgramasVuelosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProgramasVuelosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProgramasVuelosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProgramasVuelos(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProgramasVuelos();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
