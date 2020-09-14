import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { CiudadesUpdateComponent } from 'app/entities/ciudades/ciudades-update.component';
import { CiudadesService } from 'app/entities/ciudades/ciudades.service';
import { Ciudades } from 'app/shared/model/ciudades.model';

describe('Component Tests', () => {
  describe('Ciudades Management Update Component', () => {
    let comp: CiudadesUpdateComponent;
    let fixture: ComponentFixture<CiudadesUpdateComponent>;
    let service: CiudadesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [CiudadesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CiudadesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CiudadesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CiudadesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ciudades(123);
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
        const entity = new Ciudades();
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
