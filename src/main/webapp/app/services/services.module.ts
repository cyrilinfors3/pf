import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { PfSharedModule } from '../shared';

import { SERVICES_ROUTE, ServicesComponent } from './';

@NgModule({
    imports: [
        PfSharedModule,
        RouterModule.forChild([ SERVICES_ROUTE ])
    ],
    declarations: [
        ServicesComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfServicesModule {}
