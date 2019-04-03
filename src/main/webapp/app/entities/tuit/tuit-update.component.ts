import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ITuit } from 'app/shared/model/tuit.model';
import { TuitService } from './tuit.service';

@Component({
    selector: 'jhi-tuit-update',
    templateUrl: './tuit-update.component.html'
})
export class TuitUpdateComponent implements OnInit {
    tuit: ITuit;
    isSaving: boolean;

    constructor(private tuitService: TuitService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tuit }) => {
            this.tuit = tuit;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tuit.id !== undefined) {
            this.subscribeToSaveResponse(this.tuitService.update(this.tuit));
        } else {
            this.subscribeToSaveResponse(this.tuitService.create(this.tuit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ITuit>>) {
        result.subscribe((res: HttpResponse<ITuit>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
