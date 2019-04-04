import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TfgTest1SharedModule } from 'app/shared';
import {
    TuitComponent,
    TuitDetailComponent,
    TuitUpdateComponent,
    TuitDeletePopupComponent,
    TuitDeleteDialogComponent,
    tuitRoute,
    tuitPopupRoute
} from './';

const ENTITY_STATES = [...tuitRoute, ...tuitPopupRoute];

@NgModule({
    imports: [TfgTest1SharedModule, RouterModule.forChild(ENTITY_STATES)],
    exports: [TuitComponent],
    declarations: [TuitComponent, TuitDetailComponent, TuitUpdateComponent, TuitDeleteDialogComponent, TuitDeletePopupComponent],
    entryComponents: [TuitComponent, TuitUpdateComponent, TuitDeleteDialogComponent, TuitDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TfgTest1TuitModule {}
