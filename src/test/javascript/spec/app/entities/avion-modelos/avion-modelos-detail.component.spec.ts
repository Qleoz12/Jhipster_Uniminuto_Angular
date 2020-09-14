import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PruebaJhipsterTestModule } from '../../../test.module';
import { AvionModelosDetailComponent } from 'app/entities/avion-modelos/avion-modelos-detail.component';
import { AvionModelos } from 'app/shared/model/avion-modelos.model';

describe('Component Tests', () => {
  describe('AvionModelos Management Detail Component', () => {
    let comp: AvionModelosDetailComponent;
    let fixture: ComponentFixture<AvionModelosDetailComponent>;
    const route = ({ data: of({ avionModelos: new AvionModelos(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PruebaJhipsterTestModule],
        declarations: [AvionModelosDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AvionModelosDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvionModelosDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.avionModelos).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
