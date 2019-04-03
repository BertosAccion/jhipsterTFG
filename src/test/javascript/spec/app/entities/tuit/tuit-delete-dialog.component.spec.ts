/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { TfgTest1TestModule } from '../../../test.module';
import { TuitDeleteDialogComponent } from 'app/entities/tuit/tuit-delete-dialog.component';
import { TuitService } from 'app/entities/tuit/tuit.service';

describe('Component Tests', () => {
    describe('Tuit Management Delete Component', () => {
        let comp: TuitDeleteDialogComponent;
        let fixture: ComponentFixture<TuitDeleteDialogComponent>;
        let service: TuitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [TfgTest1TestModule],
                declarations: [TuitDeleteDialogComponent]
            })
                .overrideTemplate(TuitDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TuitDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TuitService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
