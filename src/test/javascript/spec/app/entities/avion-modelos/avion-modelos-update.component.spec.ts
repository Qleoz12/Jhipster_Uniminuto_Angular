import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { AvionModelosUpdateComponent } from 'app/entities/avion-modelos/avion-modelos-update.component';
import { AvionModelosService } from 'app/entities/avion-modelos/avion-modelos.service';
import { AvionModelos } from 'app/shared/model/avion-modelos.model';

describe('Component Tests', () => {
  describe('AvionModelos Management Update Component', () => {
    let comp: AvionModelosUpdateComponent;
    let fixture: ComponentFixture<AvionModelosUpdateComponent>;
    let service: AvionModelosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [AvionModelosUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AvionModelosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvionModelosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvionModelosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AvionModelos(123);
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
        const entity = new AvionModelos();
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
