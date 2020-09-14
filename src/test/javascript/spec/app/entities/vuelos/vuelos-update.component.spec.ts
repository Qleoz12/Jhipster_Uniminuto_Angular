import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { VuelosUpdateComponent } from 'app/entities/vuelos/vuelos-update.component';
import { VuelosService } from 'app/entities/vuelos/vuelos.service';
import { Vuelos } from 'app/shared/model/vuelos.model';

describe('Component Tests', () => {
  describe('Vuelos Management Update Component', () => {
    let comp: VuelosUpdateComponent;
    let fixture: ComponentFixture<VuelosUpdateComponent>;
    let service: VuelosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [VuelosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(VuelosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VuelosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(VuelosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Vuelos(123);
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
        const entity = new Vuelos();
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
