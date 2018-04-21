import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Appointmentmessages } from './appointmentmessages.model';
import { AppointmentmessagesService } from './appointmentmessages.service';

@Component({
    selector: 'jhi-appointmentmessages-detail',
    templateUrl: './appointmentmessages-detail.component.html'
})
export class AppointmentmessagesDetailComponent implements OnInit, OnDestroy {

    appointmentmessages: Appointmentmessages;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private appointmentmessagesService: AppointmentmessagesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAppointmentmessages();
    }

    load(id) {
        this.appointmentmessagesService.find(id).subscribe((appointmentmessages) => {
            this.appointmentmessages = appointmentmessages;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAppointmentmessages() {
        this.eventSubscriber = this.eventManager.subscribe(
            'appointmentmessagesListModification',
            (response) => this.load(this.appointmentmessages.id)
        );
    }
}
