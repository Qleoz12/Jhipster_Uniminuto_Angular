import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { AeropuertosUpdateComponent } from 'app/entities/aeropuertos/aeropuertos-update.component';
import { AeropuertosService } from 'app/entities/aeropuertos/aeropuertos.service';
import { Aeropuertos } from 'app/shared/model/aeropuertos.model';

describe('Component Tests', () => {
  describe('Aeropuertos Management Update Component', () => {
    let comp: AeropuertosUpdateComponent;
    let fixture: ComponentFixture<AeropuertosUpdateComponent>;
    let service: AeropuertosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [AeropuertosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AeropuertosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AeropuertosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AeropuertosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Aeropuertos(123);
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
        const entity = new Aeropuertos();
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
