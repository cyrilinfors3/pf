import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import { Appointment } from '../entities/appointment/appointment.model';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-appointments',
    templateUrl: './appointments.component.html',
    styleUrls: [
        'appointments.scss'
    ]

})
export class AppointmentsComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    l = '';
    private apiurl = 'http://localhost:8080/api/myappointments/';
    data: any = {};

    constructor( private http: Http,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {

        this.getAppointment();
        this.getData();
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.l = account.login;
            this.getAppointment();
        });
        this.registerAuthenticationSuccess();
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', (message) => {
            this.principal.identity().then((account) => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
        this.getAppointment();
    }
    getData() {
    return this.http.get(this.apiurl + this.l ).map((res: Response) => res.json())
    }
    getAppointment() {
    this.getData().subscribe((data) => {
    this.data = data;
       })
    }
    }
