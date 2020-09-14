import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { ClientesUpdateComponent } from 'app/entities/clientes/clientes-update.component';
import { ClientesService } from 'app/entities/clientes/clientes.service';
import { Clientes } from 'app/shared/model/clientes.model';

describe('Component Tests', () => {
  describe('Clientes Management Update Component', () => {
    let comp: ClientesUpdateComponent;
    let fixture: ComponentFixture<ClientesUpdateComponent>;
    let service: ClientesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [ClientesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ClientesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ClientesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ClientesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Clientes(123);
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
        const entity = new Clientes();
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
