import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Http, Response } from '@angular/http';
import { Project } from '../entities/project/project.model';

import { Account, LoginModalService, Principal } from '../shared';

@Component({
    selector: 'jhi-services',
    templateUrl: './services.component.html',
    styleUrls: [
        'services.css'
    ]

})
export class ServicesComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
  private apiurl= 'http://localhost:8080/api/projects';
    data: any= {};

    constructor(private http: Http,
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
    }

    getData() {
    return this.http.get(this.apiurl).map((res: Response) => res.json())
    }

    getProjects() {
    this.getData().subscribe((data) => {
    this.data = data;
       })
    }
}
