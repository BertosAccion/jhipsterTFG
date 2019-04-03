/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TfgTest1TestModule } from '../../../test.module';
import { TuitDetailComponent } from 'app/entities/tuit/tuit-detail.component';
import { Tuit } from 'app/shared/model/tuit.model';

describe('Component Tests', () => {
    describe('Tuit Management Detail Component', () => {
        let comp: TuitDetailComponent;
        let fixture: ComponentFixture<TuitDetailComponent>;
        const route = ({ data: of({ tuit: new Tuit(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TfgTest1TestModule],
                declarations: [TuitDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TuitDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TuitDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tuit).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
