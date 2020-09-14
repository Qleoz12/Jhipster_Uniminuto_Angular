import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { TicketsDetailComponent } from 'app/entities/tickets/tickets-detail.component';
import { Tickets } from 'app/shared/model/tickets.model';

describe('Component Tests', () => {
  describe('Tickets Management Detail Component', () => {
    let comp: TicketsDetailComponent;
    let fixture: ComponentFixture<TicketsDetailComponent>;
    const route = ({ data: of({ tickets: new Tickets(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [TicketsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TicketsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TicketsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tickets).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
