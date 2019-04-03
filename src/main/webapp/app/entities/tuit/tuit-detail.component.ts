import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITuit } from 'app/shared/model/tuit.model';

@Component({
    selector: 'jhi-tuit-detail',
    templateUrl: './tuit-detail.component.html'
})
export class TuitDetailComponent implements OnInit {
    tuit: ITuit;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tuit }) => {
            this.tuit = tuit;
        });
    }

    previousState() {
        window.history.back();
    }
}
