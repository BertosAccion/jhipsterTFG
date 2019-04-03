/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { TfgTest1TestModule } from '../../../test.module';
import { TuitComponent } from 'app/entities/tuit/tuit.component';
import { TuitService } from 'app/entities/tuit/tuit.service';
import { Tuit } from 'app/shared/model/tuit.model';

describe('Component Tests', () => {
    describe('Tuit Management Component', () => {
        let comp: TuitComponent;
        let fixture: ComponentFixture<TuitComponent>;
        let service: TuitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TfgTest1TestModule],
                declarations: [TuitComponent],
                providers: []
            })
                .overrideTemplate(TuitComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TuitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TuitService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Tuit(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tuits[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
