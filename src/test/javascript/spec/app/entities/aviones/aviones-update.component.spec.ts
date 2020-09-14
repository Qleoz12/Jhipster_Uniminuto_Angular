import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { AvionesUpdateComponent } from 'app/entities/aviones/aviones-update.component';
import { AvionesService } from 'app/entities/aviones/aviones.service';
import { Aviones } from 'app/shared/model/aviones.model';

describe('Component Tests', () => {
  describe('Aviones Management Update Component', () => {
    let comp: AvionesUpdateComponent;
    let fixture: ComponentFixture<AvionesUpdateComponent>;
    let service: AvionesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [AvionesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AvionesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvionesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvionesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Aviones(123);
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
        const entity = new Aviones();
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
