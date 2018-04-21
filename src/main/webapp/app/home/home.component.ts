import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';
import { Project } from '../entities/project/project.model';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: [
        'home.scss'
    ]

})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    l = '';
    private apiurl = 'http://localhost:8080/api/myprojectsapi/';
    data: any = {};

    constructor( private http: Http,
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager
    ) {

        this.getProjects();
        this.getData();
    }

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.account = account;
            this.l = account.login;
            this.getProjects();
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
        this.getProjects();
    }
    getData() {
    return this.http.get(this.apiurl + this.l ).map((res: Response) => res.json())
    }

    getProjects() {
    this.getData().subscribe((data) => {
    this.data = data;
       })
    }
}
