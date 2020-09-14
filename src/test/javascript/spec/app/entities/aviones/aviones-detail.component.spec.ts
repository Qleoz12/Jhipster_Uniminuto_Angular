import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { AvionesDetailComponent } from 'app/entities/aviones/aviones-detail.component';
import { Aviones } from 'app/shared/model/aviones.model';

describe('Component Tests', () => {
  describe('Aviones Management Detail Component', () => {
    let comp: AvionesDetailComponent;
    let fixture: ComponentFixture<AvionesDetailComponent>;
    const route = ({ data: of({ aviones: new Aviones(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [AvionesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AvionesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvionesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.aviones).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
