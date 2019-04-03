import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITuit } from 'app/shared/model/tuit.model';
import { Principal } from 'app/core';
import { TuitService } from './tuit.service';

@Component({
    selector: 'jhi-tuit',
    templateUrl: './tuit.component.html'
})
export class TuitComponent implements OnInit, OnDestroy {
    tuits: ITuit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tuitService: TuitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.tuitService.query().subscribe(
            (res: HttpResponse<ITuit[]>) => {
                this.tuits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTuits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITuit) {
        return item.id;
    }

    registerChangeInTuits() {
        this.eventSubscriber = this.eventManager.subscribe('tuitListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
