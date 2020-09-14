import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { ProgramasVuelosDetailComponent } from 'app/entities/programas-vuelos/programas-vuelos-detail.component';
import { ProgramasVuelos } from 'app/shared/model/programas-vuelos.model';

describe('Component Tests', () => {
  describe('ProgramasVuelos Management Detail Component', () => {
    let comp: ProgramasVuelosDetailComponent;
    let fixture: ComponentFixture<ProgramasVuelosDetailComponent>;
    const route = ({ data: of({ programasVuelos: new ProgramasVuelos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [ProgramasVuelosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProgramasVuelosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgramasVuelosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.programasVuelos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
