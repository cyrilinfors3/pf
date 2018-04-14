import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Appointment } from './appointment.model';
import { AppointmentService } from './appointment.service';

@Component({
    selector: 'jhi-appointment-detail',
    templateUrl: './appointment-detail.component.html'
})
export class AppointmentDetailComponent implements OnInit, OnDestroy {

    appointment: Appointment;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private appointmentService: AppointmentService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAppointments();
    }

    load(id) {
        this.appointmentService.find(id).subscribe((appointment) => {
            this.appointment = appointment;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAppointments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'appointmentListModification',
            (response) => this.load(this.appointment.id)
        );
    }
}
