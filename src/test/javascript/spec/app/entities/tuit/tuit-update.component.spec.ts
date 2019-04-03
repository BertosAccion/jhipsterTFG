/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { TfgTest1TestModule } from '../../../test.module';
import { TuitUpdateComponent } from 'app/entities/tuit/tuit-update.component';
import { TuitService } from 'app/entities/tuit/tuit.service';
import { Tuit } from 'app/shared/model/tuit.model';

describe('Component Tests', () => {
    describe('Tuit Management Update Component', () => {
        let comp: TuitUpdateComponent;
        let fixture: ComponentFixture<TuitUpdateComponent>;
        let service: TuitService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TfgTest1TestModule],
                declarations: [TuitUpdateComponent]
            })
                .overrideTemplate(TuitUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TuitUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TuitService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Tuit(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tuit = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Tuit();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.tuit = entity;
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
