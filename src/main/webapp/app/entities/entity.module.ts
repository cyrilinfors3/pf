import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { PfUserextraModule } from './userextra/userextra.module';
import { PfProjectModule } from './project/project.module';
import { PfProjectevolutionModule } from './projectevolution/projectevolution.module';
import { PfVoteModule } from './vote/vote.module';
import { PfAppointmentModule } from './appointment/appointment.module';
import { PfProjectmessagesModule } from './projectmessages/projectmessages.module';
import { PfAppointmentmessagesModule } from './appointmentmessages/appointmentmessages.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        PfUserextraModule,
        PfProjectModule,
        PfProjectevolutionModule,
        PfVoteModule,
        PfAppointmentModule,
        PfProjectmessagesModule,
        PfAppointmentmessagesModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    exports: [
        PfProjectModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PfEntityModule {}
