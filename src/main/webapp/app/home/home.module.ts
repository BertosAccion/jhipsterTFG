import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TfgTest1SharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';
import { TfgTest1EntityModule } from 'app/entities/entity.module';

@NgModule({
    imports: [TfgTest1SharedModule, TfgTest1EntityModule, RouterModule.forChild([HOME_ROUTE])],
    declarations: [HomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TfgTest1HomeModule {}
