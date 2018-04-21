import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { Http, Response } from '@angular/http';
import { PfSharedModule } from '../shared';
import { PfProjectModule } from '../entities/project/project.module';

import { MYPROJECTS_ROUTE, MyprojectsComponent } from './';

@NgModule({
    imports: [  PfProjectModule,
        PfSharedModule,
        RouterModule.forChild([ MYPROJECTS_ROUTE ])
    ],
    declarations: [
        MyprojectsComponent,
    ],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfMyprojectsModule {}
