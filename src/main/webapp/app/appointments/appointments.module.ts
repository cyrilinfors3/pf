import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Http, Response } from '@angular/http';
import { PfSharedModule } from '../shared';
import { PfProjectModule } from '../entities/project/project.module';

import { APPOINTMENTS_ROUTE, AppointmentsComponent } from './';

@NgModule({
    imports: [  PfProjectModule,
        PfSharedModule,
        RouterModule.forChild([ APPOINTMENTS_ROUTE ])
    ],
    declarations: [
        AppointmentsComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class PfAppointmentsModule {}
