import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { VuelosDetailComponent } from 'app/entities/vuelos/vuelos-detail.component';
import { Vuelos } from 'app/shared/model/vuelos.model';

describe('Component Tests', () => {
  describe('Vuelos Management Detail Component', () => {
    let comp: VuelosDetailComponent;
    let fixture: ComponentFixture<VuelosDetailComponent>;
    const route = ({ data: of({ vuelos: new Vuelos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [VuelosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(VuelosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(VuelosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.vuelos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
