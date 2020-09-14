import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { TicketsUpdateComponent } from 'app/entities/tickets/tickets-update.component';
import { TicketsService } from 'app/entities/tickets/tickets.service';
import { Tickets } from 'app/shared/model/tickets.model';

describe('Component Tests', () => {
  describe('Tickets Management Update Component', () => {
    let comp: TicketsUpdateComponent;
    let fixture: ComponentFixture<TicketsUpdateComponent>;
    let service: TicketsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [TicketsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TicketsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TicketsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TicketsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Tickets(123);
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
        const entity = new Tickets();
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
