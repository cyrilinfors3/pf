import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Project } from './project.model';
import { ProjectService } from './project.service';
import { Http, Response } from '@angular/http';
import 'rxjs/add/operator/map';

@Component({
    selector: 'jhi-project-detail',
    templateUrl: './project-detail.component.html'
})
export class ProjectDetailComponent implements OnInit, OnDestroy {
    private apiurl = 'http://localhost:8080/api/sendappointment/';
    prcreationdate = '';
    message = '';
    projectid = '';
    projectower = '';
    coauchid = '';
    project: Project;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private http: Http,
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private projectService: ProjectService,
        private route: ActivatedRoute
    ) {
    }
    sendappointment() {
    return this.http.get(this.apiurl + this.message + '/' + this.projectid + '/' + this.projectower + '/' + this.coauchid  ).map((res: Response) => res.json())
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProjects();
    }

    load(id) {
        this.projectService.find(id).subscribe((project) => {
            this.project = project;
            this.projectid = project.id + '';
            this.projectower = 1 + '';
            this.coauchid = 1 + '';
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProjects() {
        this.eventSubscriber = this.eventManager.subscribe(
            'projectListModification',
            (response) => this.load(this.project.id)
        );
    }
}
