import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TfgTest1TuitModule } from './tuit/tuit.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        TfgTest1TuitModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TfgTest1EntityModule {}
